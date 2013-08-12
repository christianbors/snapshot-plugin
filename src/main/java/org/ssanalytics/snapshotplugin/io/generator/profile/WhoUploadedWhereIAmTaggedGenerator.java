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
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;

/**
 *
 * @author christian
 */
@ServiceProviders(value={
@ServiceProvider(service = Generator.class),
@ServiceProvider(service = ProfileGenerator.class)})
public class WhoUploadedWhereIAmTaggedGenerator extends AbstractPhotoInfoGenerator implements ProfileGenerator{

    public static String NAME = "Who uploaded where I am tagged Graph";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected void addAttributeColumns() {
        this.model.getNodeTable().addColumn("tagged me", AttributeType.INT);
    }

    @Override
    protected void addGraphElements(ContainerLoader container, Map<IProfile, List<IPhoto>> photoMap) {
        Map<String, PhotoUploadNode> tempNodes = new HashMap<>();

        for (Map.Entry<IProfile, List<IPhoto>> photoEntry : photoMap.entrySet()) {
            for (IPhoto photo : photoEntry.getValue()) {
                PhotoUploadNode uploadNode;
                if (!tempNodes.containsKey(photo.getFrom().getId())) {
                    uploadNode = new PhotoUploadNode(container.factory().newNodeDraft(), 1);
                    uploadNode.node.setId(photo.getFrom().getId());
                    uploadNode.node.setLabel(photo.getFrom().getName());
                    tempNodes.put(photo.getFrom().getId(), uploadNode);
                } else {
                    uploadNode = tempNodes.get(photo.getFrom().getId());
                    uploadNode.count++;
                }
            }

            for (PhotoUploadNode tempNode : tempNodes.values()) {
                tempNode.node.addAttributeValue(model.getNodeTable().getColumn("tagged me"), tempNode.count);
                tempNode.node.setSize(tempNode.count);
                container.addNode(tempNode.node);
            }
        }
    }

    private class PhotoUploadNode {

        NodeDraft node;
        int count;

        public PhotoUploadNode(NodeDraft node, int count) {
            this.node = node;
            this.count = count;
        }
    }
}
