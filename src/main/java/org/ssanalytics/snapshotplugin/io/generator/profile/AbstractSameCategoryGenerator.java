/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.generator.profile;

import java.util.List;
import java.util.Map;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.EdgeDraft;
import org.gephi.io.importer.api.NodeDraft;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ICategorizedDomain;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.ui.helper.layout.AbstractSnapshotLayoutController;
import org.ssanalytics.snapshotplugin.ui.helper.ranking.AbstractSnapshotRankingController;

/**
 *
 * @author christian
 */
public abstract class AbstractSameCategoryGenerator extends AbstractProfileGenerator {

    protected AttributeModel model;

    @Override
    public void generate(ContainerLoader container) {

        model = container.getAttributeModel();
        model.getNodeTable().addColumn("category", AttributeType.STRING);
        Map<ICategorizedDomain, List<NamedItem>> categories = getCategoryMap();

        long startTime = System.currentTimeMillis();
        for (Map.Entry<ICategorizedDomain, List<NamedItem>> entry : categories.entrySet()) {
            NodeDraft categorizedNode = container.factory().newNodeDraft();
            categorizedNode.setId(entry.getKey().getId());
            categorizedNode.setLabel(entry.getKey().getName());
            categorizedNode.addAttributeValue(model.getNodeTable().getColumn("category"), entry.getKey().getCategory());
            container.addNode(categorizedNode);

            for (NamedItem liker : entry.getValue()) {
                NodeDraft namedNode;
                if (!container.nodeExists(liker.getId())) {
                    namedNode = container.factory().newNodeDraft();
                    namedNode.setId(liker.getId());
                    namedNode.setLabel(liker.getName());
                    container.addNode(namedNode);
                } else {
                    namedNode = container.getNode(liker.getId());
                }

                EdgeDraft likesEdge = container.factory().newEdgeDraft();
                likesEdge.setSource(namedNode);
                likesEdge.setTarget(categorizedNode);
                container.addEdge(likesEdge);
            }
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }

    @Override
    public AbstractSnapshotRankingController getGeneratorRanking() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AbstractSnapshotLayoutController getGeneratorLayout() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected abstract Map<ICategorizedDomain, List<NamedItem>> getCategoryMap();
}
