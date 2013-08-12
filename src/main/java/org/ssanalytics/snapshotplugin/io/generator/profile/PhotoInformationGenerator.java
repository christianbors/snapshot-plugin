/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.generator.profile;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.io.generator.spi.Generator;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.EdgeDraft;
import org.gephi.io.importer.api.NodeDraft;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhoto;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoTagData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedCommentData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLikeData;
import org.ssanalytics.snapshotplugin.ui.helper.layout.AbstractSnapshotLayoutController;
import org.ssanalytics.snapshotplugin.ui.helper.ranking.AbstractSnapshotRankingController;

/**
 *
 * @author christian
 */
@ServiceProviders(value={
@ServiceProvider(service = Generator.class),
@ServiceProvider(service = ProfileGenerator.class)})
public class PhotoInformationGenerator extends AbstractPhotoInfoGenerator implements ProfileGenerator{

    public static String NAME = "Photo Information Graph";

    @Override
    protected void addAttributeColumns() {
        model.getNodeTable().addColumn("source", AttributeType.STRING);
        model.getNodeTable().addColumn("link", AttributeType.STRING);
        model.getNodeTable().addColumn("updated_time", AttributeType.STRING);
        model.getNodeTable().addColumn("likes", AttributeType.INT);
        model.getNodeTable().addColumn("comments", AttributeType.INT);
        model.getNodeTable().addColumn("tags", AttributeType.INT);
    }

    @Override
    protected void addGraphElements(ContainerLoader container, Map<IProfile, List<IPhoto>> photoMap) {

        for (Map.Entry<IProfile, List<IPhoto>> photoEntry : photoMap.entrySet()) {
            if (photoEntry.getValue().size() > 0) {
                NodeDraft nodeRoot = null;
                if (!container.nodeExists(photoEntry.getKey().getId())) {
                    nodeRoot = container.factory().newNodeDraft();
                    nodeRoot.setId(photoEntry.getKey().getId());
                    nodeRoot.setLabel(photoEntry.getKey().getName());
                    nodeRoot.setSize(photoEntry.getValue().size());
                    container.addNode(nodeRoot);
                } else {
                    nodeRoot = container.getNode(photoEntry.getKey().getId());
                }

                for (IPhoto photoInfo : photoEntry.getValue()) {
                    if (friendsMap.containsKey(photoInfo.getFrom().getId())) {
                        NodeDraft nodePhoto = container.factory().newNodeDraft();
                        nodePhoto.setId(photoInfo.getId());
                        if (photoInfo.getName() != null) {
                            nodePhoto.setLabel(photoInfo.getName());
                        } else {
                            nodePhoto.setLabel(new Date(photoInfo.getUpdated_time()).toString());
                        }
                        nodePhoto.addAttributeValue(model.getNodeTable().getColumn("source"), photoInfo.getSource());
                        nodePhoto.addAttributeValue(model.getNodeTable().getColumn("link"), photoInfo.getLink());
                        nodePhoto.addAttributeValue(model.getNodeTable().getColumn("updated_time"), new Date(photoInfo.getUpdated_time()).toString());
                        container.addNode(nodePhoto);

                        // from info
                        if (!container.nodeExists(photoInfo.getFrom().getId())) {
                            NodeDraft nodeFrom = container.factory().newNodeDraft();
                            nodeFrom.setId(photoInfo.getFrom().getId());
                            nodeFrom.setLabel(photoInfo.getFrom().getName());
                            container.addNode(nodeFrom);
                        }

                        EdgeDraft edgeFrom = container.factory().newEdgeDraft();
                        edgeFrom.setSource(container.getNode(photoInfo.getFrom().getId()));
                        edgeFrom.setTarget(nodePhoto);
                        edgeFrom.setLabel("uploaded");
                        edgeFrom.setColor(Color.YELLOW);
                        container.addEdge(edgeFrom);

                        EdgeDraft edgeRoot = container.factory().newEdgeDraft();
                        edgeRoot.setSource(nodePhoto);
                        edgeRoot.setTarget(nodeRoot);
                        container.addEdge(edgeRoot);

                        // likes info
                        if (photoInfo.getLikes() != null && photoInfo.getLikes().getDataList().size() > 0) {
                            nodePhoto.addAttributeValue(model.getNodeTable().getColumn("likes"), photoInfo.getLikes().getDataList().size());
                            nodePhoto.setSize(photoInfo.getLikes().getDataList().size());
                            for (ISharedLikeData like : photoInfo.getLikes().getDataList()) {
                                if (friendsMap.containsKey(like.getId())) {
                                    if (!container.nodeExists(like.getId())) {
                                        NodeDraft nodeLike = container.factory().newNodeDraft();
                                        nodeLike.setId(like.getId());
                                        nodeLike.setLabel(like.getName());
                                        container.addNode(nodeLike);
                                    }

                                    EdgeDraft edgeLike = container.factory().newEdgeDraft();
                                    edgeLike.setSource(container.getNode(like.getId()));
                                    edgeLike.setTarget(nodePhoto);
                                    edgeLike.setLabel("liked");
                                    edgeLike.setColor(Color.GREEN);
                                    container.addEdge(edgeLike);
                                }
                            }
                        } else {
                            nodePhoto.addAttributeValue(model.getNodeTable().getColumn("likes"), 0);
                        }

                        // comments info
                        if (photoInfo.getComments() != null && photoInfo.getComments().getDataList().size() > 0) {
                            nodePhoto.addAttributeValue(model.getNodeTable().getColumn("comments"), photoInfo.getComments().getDataList().size());
                            for (ISharedCommentData comment : photoInfo.getComments().getDataList()) {
                                if (friendsMap.containsKey(comment.getFrom().getId())) {
                                    if (!container.nodeExists(comment.getFrom().getId())) {
                                        NodeDraft nodeCommentFrom = container.factory().newNodeDraft();
                                        nodeCommentFrom.setId(comment.getFrom().getId());
                                        nodeCommentFrom.setLabel(comment.getFrom().getName());
                                        container.addNode(nodeCommentFrom);
                                    }

                                    EdgeDraft edgeComment = container.factory().newEdgeDraft();
                                    edgeComment.setSource(container.getNode(comment.getFrom().getId()));
                                    edgeComment.setTarget(nodePhoto);
                                    if (comment.getLikes() != null) {
                                        edgeComment.setWeight(comment.getLikes());
                                    }
                                    if (comment.getMessage() != null) {
                                        edgeComment.setLabel(comment.getMessage());
                                    } else {
                                        edgeComment.setLabel(new Date(comment.getCreatedTime()).toString());
                                    }
                                    edgeComment.setColor(Color.CYAN);
                                    container.addEdge(edgeComment);
                                }
                            }
                        } else {
                            nodePhoto.addAttributeValue(model.getNodeTable().getColumn("comments"), 0);
                        }

                        // tags info
                        if (photoInfo.getPhotoTags() != null && photoInfo.getPhotoTags().getTagDataList().size() > 0) {
                            nodePhoto.addAttributeValue(model.getNodeTable().getColumn("tags"), photoInfo.getPhotoTags().getTagDataList().size());
                            for (IPhotoTagData tag : photoInfo.getPhotoTags().getTagDataList()) {
                                if (friendsMap.containsKey(tag.getId())) {
                                    if (!container.nodeExists(tag.getId())) {
                                        NodeDraft nodeTag = container.factory().newNodeDraft();
                                        nodeTag.setId(tag.getId());
                                        nodeTag.setLabel(tag.getName());
                                        container.addNode(nodeTag);
                                    }
                                    EdgeDraft edgeTag = container.factory().newEdgeDraft();
                                    edgeTag.setSource(container.getNode(tag.getId()));
                                    edgeTag.setTarget(nodePhoto);
                                    edgeTag.setLabel("tagged on");
                                    edgeTag.setColor(Color.ORANGE);
                                    container.addEdge(edgeTag);
                                }
                            }
                        } else {
                            nodePhoto.addAttributeValue(model.getNodeTable().getColumn("tags"), 0);
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public AbstractSnapshotRankingController getGeneratorRanking() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AbstractSnapshotLayoutController getGeneratorLayout() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
