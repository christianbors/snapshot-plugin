/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.generator.profile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.io.generator.spi.Generator;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.EdgeDraft;
import org.gephi.io.importer.api.NodeDraft;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.LocationFetcher;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.ui.helper.layout.AbstractSnapshotLayoutController;
import org.ssanalytics.snapshotplugin.ui.helper.ranking.AbstractSnapshotRankingController;

/**
 *
 * @author christian
 */
@ServiceProviders(value={
@ServiceProvider(service = Generator.class),
@ServiceProvider(service = ProfileGenerator.class)})
public class WhoHasBeenWhereIHaveBeenGenerator extends AbstractProfileGenerator implements ProfileGenerator{

    public static String NAME = "Who has been Where I have Been";

    @Override
    public void generate(ContainerLoader container) {
        try {
            Map<ISharedPlace, List<NamedItem>> locations = new HashMap<>();
            for (IProfile profile : this.profiles) {
                locations.putAll(LocationFetcher.getInstance().getWhoHasBeenWhereIHaveBeenSnapshotLatestVersion(snapshot.getValue(), profile.getId()));
            }

            AttributeModel model = container.getAttributeModel();
            model.getNodeTable().addColumn("city", AttributeType.STRING);
            model.getNodeTable().addColumn("Latitude", AttributeType.STRING);
            model.getNodeTable().addColumn("Longitude", AttributeType.STRING);
            model.getNodeTable().addColumn("State", AttributeType.STRING);
            model.getNodeTable().addColumn("Street", AttributeType.STRING);
            model.getNodeTable().addColumn("Zip", AttributeType.STRING);

            long startTime = System.currentTimeMillis();
            for (Map.Entry<ISharedPlace, List<NamedItem>> entry : locations.entrySet()) {
                NodeDraft placeNode = container.factory().newNodeDraft();
                placeNode.setId(entry.getKey().getId());
                placeNode.setLabel(entry.getKey().getName());
                container.addNode(placeNode);

                placeNode.addAttributeValue(model.getNodeTable().getColumn("city"), entry.getKey().getLocation().getCity());
                placeNode.addAttributeValue(model.getNodeTable().getColumn("Latitude"), entry.getKey().getLocation().getLatitude());
                placeNode.addAttributeValue(model.getNodeTable().getColumn("Longitude"), entry.getKey().getLocation().getLongitude());
                placeNode.addAttributeValue(model.getNodeTable().getColumn("State"), entry.getKey().getLocation().getState());
                placeNode.addAttributeValue(model.getNodeTable().getColumn("Street"), entry.getKey().getLocation().getStreet());
                placeNode.addAttributeValue(model.getNodeTable().getColumn("Zip"), entry.getKey().getLocation().getZip());

                for (NamedItem tagged : entry.getValue()) {
                    NodeDraft profileNode = container.factory().newNodeDraft();
                    profileNode.setId(tagged.getId());
                    profileNode.setLabel(tagged.getName());
                    container.addNode(profileNode);

                    EdgeDraft beenTo = container.factory().newEdgeDraft();
                    beenTo.setSource(profileNode);
                    beenTo.setTarget(placeNode);
                }
            }
            System.out.println(System.currentTimeMillis() - startTime);
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public AbstractSnapshotRankingController getGeneratorRanking() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AbstractSnapshotLayoutController getGeneratorLayout() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
