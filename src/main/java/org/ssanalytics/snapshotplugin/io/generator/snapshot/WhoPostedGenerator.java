/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.generator.snapshot;

import java.util.Map;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.io.generator.spi.Generator;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.NodeDraft;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.ui.helper.layout.AbstractSnapshotLayoutController;
import org.ssanalytics.snapshotplugin.ui.helper.ranking.AbstractSnapshotRankingController;

/**
 *
 * @author christian
 */
@ServiceProviders(value={
@ServiceProvider(service = Generator.class),
@ServiceProvider(service = SnapshotGenerator.class)})
public class WhoPostedGenerator extends AbstractSnapshotGenerator implements SnapshotGenerator{

    public static String NAME = "Who Posted Graph";
    protected AttributeModel model;

    @Override
    public void generate(ContainerLoader container) {
        model = container.getAttributeModel();
        model.getNodeTable().addColumn("likes", AttributeType.INT);
        model.getNodeTable().addColumn("posts", AttributeType.INT);

        if (hasSnapshot()) {
            try {
                NodeDraft namedItemDraft;
                Map<? extends NamedItem, Integer> whoPostedMap = DaoFactory.getFeedDAO().getWhoPostedOnWallForRootAccountOfSnapshotLatestVersion(snapshot.getValue());
                
                long startTime = System.currentTimeMillis();
                for (Map.Entry<? extends NamedItem, Integer> entry : whoPostedMap.entrySet()) {
                    namedItemDraft = container.factory().newNodeDraft();
                    namedItemDraft.setId(entry.getKey().getId());
                    namedItemDraft.setLabel(entry.getKey().getName());
                    namedItemDraft.setSize(entry.getValue());
                    namedItemDraft.addAttributeValue(model.getNodeTable().getColumn("posts"), entry.getValue());
                    container.addNode(namedItemDraft);
                }
                System.out.println(System.currentTimeMillis() - startTime);
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }

        } else {
            cancel();
        }
    }

    @Override
    public AbstractSnapshotRankingController getGeneratorRanking() {
        return null;
    }

    @Override
    public AbstractSnapshotLayoutController getGeneratorLayout() {
        return null;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
