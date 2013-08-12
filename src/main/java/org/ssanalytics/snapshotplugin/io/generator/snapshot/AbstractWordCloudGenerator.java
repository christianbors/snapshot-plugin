/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.generator.snapshot;

import java.util.Map;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.NodeDraft;
import org.ssanalytics.snapshotplugin.ui.helper.layout.AbstractSnapshotLayoutController;
import org.ssanalytics.snapshotplugin.ui.helper.ranking.AbstractSnapshotRankingController;

/**
 *
 * @author christian
 */
public abstract class AbstractWordCloudGenerator extends AbstractSnapshotGenerator {

    @Override
    public void generate(ContainerLoader container) {
        AttributeModel model = container.getAttributeModel();
        model.getNodeTable().addColumn("count", AttributeType.INT);

        if (hasSnapshot()) {
            Map<String, Integer> wordMap = getWordMap();
            
            long startTime = System.currentTimeMillis();
            for (Map.Entry<String, Integer> wordEntry : wordMap.entrySet()) {
                NodeDraft wordNode = container.factory().newNodeDraft();
                wordNode.setId(wordEntry.getKey());
//                wordNode.setLabelSize(wordEntry.getValue());
                wordNode.setSize(wordEntry.getValue());
                wordNode.setVisible(false);
                wordNode.setLabelVisible(true);
                wordNode.addAttributeValue(model.getNodeTable().getColumn("count"), wordEntry.getValue());
                container.addNode(wordNode);
            }
            System.out.println(System.currentTimeMillis() - startTime);
        }
    }
    
    protected abstract Map<String, Integer> getWordMap();
    
    @Override
    public AbstractSnapshotRankingController getGeneratorRanking() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AbstractSnapshotLayoutController getGeneratorLayout() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
