/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.generator.snapshot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.data.attributes.type.StringList;
import org.gephi.io.generator.spi.Generator;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.EdgeDraft;
import org.gephi.io.importer.api.NodeDraft;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.inbox.IInbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.outbox.IOutbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedCommentData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedToData;
import org.ssanalytics.snapshotplugin.ui.helper.layout.AbstractSnapshotLayoutController;
import org.ssanalytics.snapshotplugin.ui.helper.layout.PrivateCommunicationProtocolLayoutController;
import org.ssanalytics.snapshotplugin.ui.helper.ranking.AbstractSnapshotRankingController;
import org.ssanalytics.snapshotplugin.ui.helper.ranking.PrivateCommunicationProtocolRankingController;

/**
 *
 * @author christian
 */
@ServiceProviders(value = {
    @ServiceProvider(service = Generator.class),
    @ServiceProvider(service = SnapshotGenerator.class)})
public class PrivateCommunicationProtocolGenerator extends AbstractSnapshotGenerator implements SnapshotGenerator {

    public static String NAME = "Private Communication";

    @Override
    public void generate(ContainerLoader container) {

        if (hasSnapshot()) {
            try {
                NodeDraft msgThread = null;
                NodeDraft toNode;
                Map<String, Map<String, Integer>> messageCount = new HashMap<>();

                List<IOutbox> boxOut = DaoFactory.getOutboxDAO().getOutboxListForRootAccountOfSnapshotLatestVersion(snapshot.getValue());

                List<IInbox> boxIn = DaoFactory.getInboxDAO().getInboxListForRootAccountOfSnapshotLatestVersion(snapshot.getValue());

                long startTime = System.currentTimeMillis();

                AttributeModel model = container.getAttributeModel();
                model.getNodeTable().addColumn("message_count", AttributeType.INT);
                model.getNodeTable().addColumn("message_list", AttributeType.LIST_STRING);
                model.getNodeTable().addColumn("date", AttributeType.STRING);
                // get connections between speaking friends, determine how often who has spoken to root
                // this has to be determined prior to adding edges
                // but adding nodes can be done without problems.
                NodeDraft rootNode = container.factory().newNodeDraft();
                rootNode.setId(snapshot.getRoot());
                rootNode.setLabel(snapshot.getValue());
                rootNode.setX(0f);
                rootNode.setY(0f);
                rootNode.setSize(30);
                container.addNode(rootNode);

                for (IOutbox box : boxOut) {
                    if (!container.nodeExists(box.getId())) {
                        String threadName = new Date(box.getUpdated_time()).toString();

                        msgThread = container.factory().newNodeDraft();
                        msgThread.setId(box.getId());
                        msgThread.setLabel(threadName);
                        msgThread.setColor(Color.CYAN);
                        container.addNode(msgThread);

                        if (box.getComments() != null) {
                            msgThread.addAttributeValue(model.getNodeTable().getColumn("message_count"), box.getComments().getDataList().size());
                        } else {
                            msgThread.addAttributeValue(model.getNodeTable().getColumn("message_count"), 0);
                        }
                        messageCount.put(box.getId(), new HashMap<String, Integer>());
                    }

                    Map<String, Integer> currentCount = messageCount.get(box.getId());
//                    Map<String, String> currentDate = messageDate.get(box.getId());

                    if (box.getFrom() != null) {
                        toNode = container.factory().newNodeDraft();
                        toNode.setId(box.getFrom().getId());
                        toNode.setLabel(box.getFrom().getName());
                        container.addNode(toNode);

                        currentCount.put(box.getFrom().getId(), 1);
                    }

                    for (ISharedToData to : box.getTo().getToDataList()) {
                        if (to.getId() != null) {
                            if (!container.nodeExists(to.getId())) {
                                toNode = container.factory().newNodeDraft();
                                toNode.setId(to.getId());
                                toNode.setLabel(to.getName());
                                container.addNode(toNode);
                            }
                            if (!currentCount.containsKey(to.getId())) {
                                currentCount.put(to.getId(), 0);
                            }
                        }
                    }

                    for (ISharedToData to : box.getTo().getToDataList()) {
                        if (to.getId() != null) {
                            if (!to.getId().equals(snapshot.getRoot())) {
                                for (ISharedToData toTwo : box.getTo().getToDataList()) {
                                    if (toTwo.getId() != null && to.getId() != null) {
                                        if (!to.getId().equals(snapshot.getRoot()) && !toTwo.getId().equals(snapshot.getRoot())) {
                                            if (!to.getId().equals(toTwo.getId())) {
                                                EdgeDraft toRelation = container.factory().newEdgeDraft();
                                                toRelation.setSource(container.getNode(toTwo.getId()));
                                                toRelation.setTarget(container.getNode(to.getId()));
                                                toRelation.setWeight(0.01f);
                                                toRelation.setColor(Color.WHITE);
                                                toRelation.setType(EdgeDraft.EdgeType.UNDIRECTED);
                                                container.addEdge(toRelation);
                                                // make edge "invisible" by setting color to white?
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (box.getComments() != null) {
                        List<String> listMessages = new ArrayList<>();
                        listMessages.add(box.getMessage());
                        for (ISharedCommentData comment : box.getComments().getDataList()) {
                            listMessages.add(comment.getMessage());
                            if (messageCount.get(box.getId()) != null && comment.getFrom() != null) {
                                if (currentCount.containsKey(comment.getFrom().getId())) {
                                    if (currentCount.get(comment.getFrom().getId()) == 0) {
                                        container.getNode(comment.getFrom().getId()).addAttributeValue(model.getNodeTable().getColumn("date"), new Date(comment.getCreatedTime()).toString());
                                    }
                                    currentCount.put(comment.getFrom().getId(), currentCount.get(comment.getFrom().getId()) + 1);
                                }
                                container.getNode(comment.getFrom().getId()).setColor(Color.YELLOW);
                            }
                        }
                        msgThread.addAttributeValue(model.getNodeTable().getColumn("message_list"), new StringList(listMessages.toArray(new String[0])));
                    }
                    if (box.getFrom() != null) {
                        if (box.getFrom().getId() != null && currentCount.containsKey(box.getFrom().getId())) {
                            currentCount.put(box.getFrom().getId(), currentCount.get(box.getFrom().getId()) + 1);
                        }
                    }

                }

                for (IInbox box : boxIn) {
                    if (!container.nodeExists(box.getId())) {
                        String threadName = new Date(box.getUpdated_time()).toString();

                        msgThread = container.factory().newNodeDraft();
                        msgThread.setId(box.getId());
                        msgThread.setLabel(threadName);
                        msgThread.setColor(Color.CYAN);
                        container.addNode(msgThread);

                        if (box.getComments() != null) {
                            msgThread.addAttributeValue(model.getNodeTable().getColumn("message_count"), box.getComments().getDataList().size());
                        } else {
                            msgThread.addAttributeValue(model.getNodeTable().getColumn("message_count"), 0);
                        }
                        messageCount.put(box.getId(), new HashMap<String, Integer>());
                    }

                    Map<String, Integer> currentCount = messageCount.get(box.getId());
//                    Map<String, String> currentDate = messageDate.get(box.getId());

                    if (box.getFrom() != null) {
                        toNode = container.factory().newNodeDraft();
                        toNode.setId(box.getFrom().getId());
                        toNode.setLabel(box.getFrom().getName());
                        container.addNode(toNode);

                        currentCount.put(box.getFrom().getId(), 1);
                    }

                    for (ISharedToData to : box.getTo().getToDataList()) {
                        if (to.getId() != null) {
                            if (!container.nodeExists(to.getId())) {
                                toNode = container.factory().newNodeDraft();
                                toNode.setId(to.getId());
                                toNode.setLabel(to.getName());
                                container.addNode(toNode);
                            }
                            if (!currentCount.containsKey(to.getId())) {
                                currentCount.put(to.getId(), 0);
                            }
                        }
                    }

                    for (ISharedToData to : box.getTo().getToDataList()) {
                        if (to.getId() != null) {
                            if (!to.getId().equals(snapshot.getRoot())) {
                                for (ISharedToData toTwo : box.getTo().getToDataList()) {
                                    if (toTwo.getId() != null && to.getId() != null) {
                                        if (!to.getId().equals(snapshot.getRoot()) && !toTwo.getId().equals(snapshot.getRoot())) {
                                            if (!to.getId().equals(toTwo.getId())) {
                                                EdgeDraft toRelation = container.factory().newEdgeDraft();
                                                toRelation.setSource(container.getNode(toTwo.getId()));
                                                toRelation.setTarget(container.getNode(to.getId()));
                                                toRelation.setWeight(0.01f);
                                                toRelation.setColor(Color.WHITE);
                                                toRelation.setType(EdgeDraft.EdgeType.UNDIRECTED);
                                                container.addEdge(toRelation);
                                                // make edge "invisible" by setting color to white?
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (box.getComments() != null) {
                        List<String> listMessages = new ArrayList<>();
                        listMessages.add(box.getMessage());
                        for (ISharedCommentData comment : box.getComments().getDataList()) {
                            listMessages.add(comment.getMessage());
                            if (messageCount.get(box.getId()) != null && comment.getFrom() != null) {
                                if (currentCount.containsKey(comment.getFrom().getId())) {
                                    if (currentCount.get(comment.getFrom().getId()) == 0) {
                                        container.getNode(comment.getFrom().getId()).addAttributeValue(model.getNodeTable().getColumn("date"), new Date(comment.getCreatedTime()).toString());
                                    }
                                    currentCount.put(comment.getFrom().getId(), currentCount.get(comment.getFrom().getId()) + 1);
                                }
                            }
                        }
                        msgThread.addAttributeValue(model.getNodeTable().getColumn("message_list"), new StringList(listMessages.toArray(new String[0])));
                    }
                    if (box.getFrom().getId() != null && currentCount.containsKey(box.getFrom().getId())) {
                        currentCount.put(box.getFrom().getId(), currentCount.get(box.getFrom().getId()) + 1);
                    }

                }
                for (Map.Entry<String, Map<String, Integer>> entry : messageCount.entrySet()) {
                    EdgeDraft countEdge;
                    for (Map.Entry<String, Integer> count : entry.getValue().entrySet()) {
                        countEdge = container.factory().newEdgeDraft();
                        countEdge.setWeight((float) count.getValue());
                        countEdge.setSource(container.getNode(count.getKey()));
                        countEdge.setTarget(container.getNode(entry.getKey()));

                        container.addEdge(countEdge);
                    }
                }
                System.out.println("computation time: " + (System.currentTimeMillis() - startTime));
                rootNode.setColor(Color.RED);
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    @Override
    public AbstractSnapshotRankingController getGeneratorRanking() {
        return new PrivateCommunicationProtocolRankingController();
    }

    @Override
    public AbstractSnapshotLayoutController getGeneratorLayout() {
        return new PrivateCommunicationProtocolLayoutController();
    }

    @Override
    public String getName() {
        return NAME;
    }
}
