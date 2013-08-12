/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.generator.profile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.io.generator.spi.Generator;
import org.gephi.io.importer.api.ContainerLoader;
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
public class WhoIsTaggedWhereIAmTaggedGenerator extends AbstractPhotoInfoGenerator implements ProfileGenerator{

    public static String NAME = "Who is tagged where I am Graph";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected void addAttributeColumns() {
        this.model.getNodeTable().addColumn("# tagged", AttributeType.INT);
    }

    @Override
    protected void addGraphElements(ContainerLoader container, Map<IProfile, List<IPhoto>> photoMap) {
        Map<String, PhotoTagNode> tempNodes = new HashMap<>();

        for (Map.Entry<IProfile, List<IPhoto>> photoEntry : photoMap.entrySet()) {
            for (IPhoto photoInfo : photoEntry.getValue()) {
                for (IPhotoTagData tagData : photoInfo.getPhotoTags().getTagDataList()) {
                    PhotoTagNode tagNode;
                    if (!tempNodes.containsKey(tagData.getId())) {
                        tagNode = new PhotoTagNode(container.factory().newNodeDraft(), 1);
                        tagNode.node.setId(tagData.getId());
                        tagNode.node.setLabel(tagData.getName());
                        tempNodes.put(tagData.getId(), tagNode);
                    } else {
                        tagNode = tempNodes.get(tagData.getId());
                        tagNode.count++;
                    }
                }
                for (PhotoTagNode photoTagNode : tempNodes.values()) {
                    photoTagNode.node.addAttributeValue(model.getNodeTable().getColumn("# tagged"), photoTagNode.count);
                    photoTagNode.node.setSize(photoTagNode.count);
                    container.addNode(photoTagNode.node);
                }
            }
        }
    }

    private class PhotoTagNode {

        NodeDraft node;
        int count;

        PhotoTagNode(NodeDraft node, int count) {
            this.node = node;
            this.count = count;
        }
    }
}
