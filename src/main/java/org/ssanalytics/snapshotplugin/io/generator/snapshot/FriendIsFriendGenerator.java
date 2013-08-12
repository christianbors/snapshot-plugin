/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.generator.snapshot;

import java.util.List;
import java.util.Map;
import org.gephi.io.generator.spi.Generator;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.EdgeDraft;
import org.gephi.io.importer.api.NodeDraft;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;
import org.ssanalytics.snapshotplugin.ui.helper.layout.AbstractSnapshotLayoutController;
import org.ssanalytics.snapshotplugin.ui.helper.ranking.AbstractSnapshotRankingController;
import org.ssanalytics.snapshotplugin.ui.helper.ranking.FriendIsFriendGraphRankingController;
import org.ssanalytics.snapshotplugin.ui.helper.layout.FriendIsFriendLayoutController;

/**
 *
 * @author christian
 */
@ServiceProviders(value={
@ServiceProvider(service = Generator.class),
@ServiceProvider(service = SnapshotGenerator.class)})
public class FriendIsFriendGenerator extends AbstractSnapshotGenerator implements SnapshotGenerator{

    public static String NAME = "Friend Is Friend";
    
    @Override
    public void generate(ContainerLoader container) {
        if (hasSnapshot()) {
            try {
                Map<String, List<String>> friendsMap = DaoFactory.getRootAccountDAO().getFriendIsFriend(snapshot.getValue(), snapshot.version());

                long startTime = System.currentTimeMillis();
                for (IFriend friend : DaoFactory.getFriendDAO().getFriendListSpecificVersion(snapshot.getValue(), snapshot.version())) {
                    NodeDraft n1 = container.factory().newNodeDraft();
                    n1.setId(friend.getId());
                    n1.setLabel(friend.getName());
                    container.addNode(n1);
                }

                for (Map.Entry<String, List<String>> entry : friendsMap.entrySet()) {
                    for (String edgeConnection : entry.getValue()) {
                        NodeDraft n0 = container.getNode(entry.getKey());
                        NodeDraft n1 = container.getNode(edgeConnection);
                        EdgeDraft e = container.factory().newEdgeDraft();
                        e.setSource(n0);
                        e.setTarget(n1);
                        container.addEdge(e);
                    }
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
    public String getName() {
        return NAME;
    }

    @Override
    public AbstractSnapshotRankingController getGeneratorRanking() {
        return new FriendIsFriendGraphRankingController();
    }

    @Override
    public AbstractSnapshotLayoutController getGeneratorLayout() {
        return new FriendIsFriendLayoutController();
    }
}
