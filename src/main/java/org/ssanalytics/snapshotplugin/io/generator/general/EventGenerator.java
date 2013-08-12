/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.generator.general;

import java.util.ArrayList;
import java.util.Date;
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
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.event.IEvent;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.ui.helper.layout.AbstractSnapshotLayoutController;
import org.ssanalytics.snapshotplugin.ui.helper.ranking.AbstractSnapshotRankingController;

/**
 *
 * @author christian
 */
@ServiceProviders(value={
@ServiceProvider(service = Generator.class),
@ServiceProvider(service = GeneralGenerator.class)})
public class EventGenerator extends AbstractGeneralGenerator implements GeneralGenerator{

    public static String NAME = "Events Graph";
    private int total_att = 0;
    private int total_dec = 0;
    private int total_uns = 0;

    @Override
    public void generate(ContainerLoader container) {
        AttributeModel model = container.getAttributeModel();
        model.getNodeTable().addColumn("attending", AttributeType.INT);
        model.getNodeTable().addColumn("declined", AttributeType.INT);
        model.getNodeTable().addColumn("unsure", AttributeType.INT);
        model.getNodeTable().addColumn("sum", AttributeType.INT);
        model.getNodeTable().addColumn("type", AttributeType.STRING);
        model.getNodeTable().addColumn("Location", AttributeType.STRING);
        model.getNodeTable().addColumn("Date Start", AttributeType.STRING);
        model.getNodeTable().addColumn("Date End", AttributeType.STRING);

        model.getEdgeTable().addColumn("rsvp_status", AttributeType.INT);

        if (hasSnapshot() || hasProfiles()) {
            List<IProfile> listProfiles = new ArrayList<>();
            List<IEvent> listEvents = new ArrayList<>();
            try {
                if (hasSnapshot() && hasProfiles()) {
                    listProfiles = new ArrayList<>();
                    listProfiles.addAll(this.profiles);
                    for (IProfile profile : listProfiles) {
                        listEvents.addAll(DaoFactory.getEventDAO().getEventListForProfileAndSnapshotLatestVersion(snapshot.getValue(), profile.getId()));
                    }
                } else if (hasSnapshot()) {
                    listProfiles = DaoFactory.getProfileDAO().getProfileListForSnapshotLatestVersion(snapshot.getValue());
                    listEvents = DaoFactory.getEventDAO().getEventListForSnapshotLatestVersion(snapshot.getValue());
                } else {
                    throw new Exception("not possible");
                }
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
            long startTime = System.currentTimeMillis();

            for (IProfile currentProfile : listProfiles) {
                NodeDraft nProfile = container.factory().newNodeDraft();
                nProfile.setId(currentProfile.getId());
                nProfile.setLabel(currentProfile.getName());
                nProfile.addAttributeValue(model.getNodeTable().getColumn("type"), "Profile");
                container.addNode(nProfile);
            }

            Map<String, List<IEvent>> mapEvents = new HashMap<>();
            for (IEvent event : listEvents) {
                List<IEvent> eventListFound;
                if (mapEvents.containsKey(event.getId())) {
                    eventListFound = mapEvents.get(event.getId());
                    if (!eventListFound.contains(event)) {
                        eventListFound.add(event);
                    }
                    HashMap<String, IEvent> mapEventReduce = new HashMap<>();
                    for (IEvent tempEvent : eventListFound) {
                        mapEventReduce.put(tempEvent.getAccountId(), tempEvent);
                    }
                    eventListFound.clear();
                    eventListFound.addAll(mapEventReduce.values());
                } else {
                    eventListFound = new ArrayList<>();
                    eventListFound.add(event);
                    mapEvents.put(event.getId(), eventListFound);
                }
            }

            NodeDraft nEvent;
            for (Map.Entry<String, List<IEvent>> eventList : mapEvents.entrySet()) {
                IEvent firstEvent = eventList.getValue().get(0);
                int attend = 0;
                int unsure = 0;
                int declined = 0;
                int sum = 0;
                for (IEvent ev : eventList.getValue()) {
                    ++sum;
                    switch (ev.getRsvp_status()) {
                        case "attending":
                            ++total_att;
                            ++attend;
                            break;
                        case "unsure":
                            ++total_uns;
                            ++unsure;
                            break;
                        case "declined":
                            ++total_dec;
                            ++declined;
                        default:
                            --sum;
                            break;
                    }
                }

                if (sum > 1) {
                    nEvent = container.getNode(firstEvent.getId());
                    if (nEvent == null) {
                        nEvent = container.factory().newNodeDraft();
                        nEvent.setId(firstEvent.getId());
                    } else {
                        nEvent.setLabel(firstEvent.getName());
                    }
                    nEvent.addAttributeValue(model.getNodeTable().getColumn("attending"), attend);
                    nEvent.addAttributeValue(model.getNodeTable().getColumn("declined"), unsure);
                    nEvent.addAttributeValue(model.getNodeTable().getColumn("unsure"), declined);
                    nEvent.addAttributeValue(model.getNodeTable().getColumn("sum"), sum);
                    nEvent.addAttributeValue(model.getNodeTable().getColumn("type"), "Event");
                    nEvent.addAttributeValue(model.getNodeTable().getColumn("Location"), firstEvent.getLocation());
                    nEvent.addAttributeValue(model.getNodeTable().getColumn("Date Start"), new Date(firstEvent.getStartTime()).toString());
                    nEvent.addAttributeValue(model.getNodeTable().getColumn("Date End"), new Date(firstEvent.getEndTime()).toString());
                    container.addNode(nEvent);

                    for (IEvent ev : eventList.getValue()) {
                        if (container.getNode(ev.getAccountId()) != null && nEvent != null) {
                            EdgeDraft eAttending = container.factory().newEdgeDraft();
                            eAttending.setSource(container.getNode(ev.getAccountId()));
                            eAttending.setTarget(nEvent);
                            int att;
                            switch (ev.getRsvp_status()) {
                                case "attending":
                                    att = 0;
                                    break;
                                case "unsure":
                                    att = 1;
                                    break;
                                default:
                                    att = 2;
                                    break;
                            }
                            eAttending.addAttributeValue(model.getEdgeTable().getColumn("rsvp_status"), att);
                            container.addEdge(eAttending);
                        }
                    }
                }
            }
            System.out.println("Time employed: " + Float.toString(System.currentTimeMillis() - startTime));
            
        } else {
            cancel();
        }
        System.out.println("attenders: " + total_att + " unsurers: " + total_uns + " decliners: " + total_dec);

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
