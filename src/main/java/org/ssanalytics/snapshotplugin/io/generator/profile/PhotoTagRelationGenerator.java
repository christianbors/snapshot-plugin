/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.generator.profile;

import java.awt.Color;
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

/**
 *
 * @author christian
 */
@ServiceProviders(value={
@ServiceProvider(service = Generator.class),
@ServiceProvider(service = ProfileGenerator.class)})
public class PhotoTagRelationGenerator extends AbstractPhotoInfoGenerator implements ProfileGenerator{

    public static String NAME = "Photo Tag Relations";

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
                NodeDraft nodeRoot;
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
                        
                        if (!container.nodeExists(photoInfo.getFrom().getId())) {
                            NodeDraft nodeFrom = container.factory().newNodeDraft();
                            nodeFrom.setId(photoInfo.getFrom().getId());
                            nodeFrom.setLabel(photoInfo.getFrom().getName());
                            container.addNode(nodeFrom);
                        }
                        
                        EdgeDraft edgeFrom = container.factory().newEdgeDraft();
                        edgeFrom.setSource(container.getNode(photoInfo.getFrom().getId()));
                        edgeFrom.setTarget(nodeRoot);
                        edgeFrom.setLabel("uploaded");
                        edgeFrom.setColor(Color.GREEN);
                        container.addEdge(edgeFrom);
                        
                        if (photoInfo.getPhotoTags() != null && photoInfo.getPhotoTags().getTagDataList().size() > 0) {

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
                                    edgeTag.setTarget(nodeRoot);
                                    edgeTag.setLabel("tagged");
                                    edgeTag.setColor(Color.darkGray);
                                    container.addEdge(edgeTag);
                                }
                            }
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
}
