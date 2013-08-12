/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.statistics;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeTable;
import org.gephi.graph.api.GraphModel;
import org.gephi.statistics.spi.Statistics;
import org.gephi.utils.longtask.spi.LongTask;
import org.gephi.utils.progress.ProgressTicket;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.inbox.IInbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.outbox.IOutbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.Content;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ReportContent;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.TextContent;

/**
 *
 * @author christian
 */
@ServiceProvider(service = ReportContent.class)
public class AverageCommunication implements Statistics, ReportContent, LongTask {

    private boolean isCanceled;
    private ProgressTicket progress;
    private float avgCount;
    private ISnapshotInfo snapshot;

    @Override
    public void execute(GraphModel graphModel, AttributeModel attributeModel) {
        isCanceled = false;
        AttributeTable nodeTable = attributeModel.getNodeTable();
        AttributeColumn communications = nodeTable.getColumn("message_count");

        if (communications == null) {
            cancel();
        }
        generate(snapshot);
    }

    @Override
    public void generate(ISnapshotInfo snapshot) {
        try {
            List<IOutbox> boxOut = DaoFactory.getOutboxDAO().getOutboxListForRootAccountOfSnapshotLatestVersion(snapshot.getValue());
            List<IInbox> boxIn = DaoFactory.getInboxDAO().getInboxListForRootAccountOfSnapshotLatestVersion(snapshot.getValue());

            Map<String, Integer> messageCounts = new HashMap<>();
            for (IOutbox out : boxOut) {
                if (!messageCounts.containsKey(out.getId())) {
                    messageCounts.put(out.getId(), 0);
                    if (out.getComments() != null) {
                        messageCounts.put(out.getId(), out.getComments().getDataList().size());
                    } else {
                        messageCounts.put(out.getId(), 1);
                    }
                }
            }
            
            for (IInbox in : boxIn) {
                if (!messageCounts.containsKey(in.getId())) {
                    messageCounts.put(in.getId(), 0);
                    if (in.getComments() != null) {
                        messageCounts.put(in.getId(), in.getComments().getDataList().size());
                    } else {
                        messageCounts.put(in.getId(), 1);
                    }
                }
            }

            execute(messageCounts);
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }

    }

    public void execute(Map<String, Integer> list) {
        int countTotal = 0;
        for (Map.Entry<String, Integer> entry : list.entrySet()) {
            countTotal += entry.getValue();
        }
        avgCount = ((float) countTotal) / ((float) list.size());
    }

    public void setSnapshot(ISnapshotInfo snapshot) {
        this.snapshot = snapshot;
    }

    public float getValue() {
        return this.avgCount;
    }

    /* thoughts:
     *      add Fonts to TestContent?
     *      Fixed content for statistics content?
     */
    @Override
    public Content[] getContent() {
        List<Content> content = new LinkedList<>();
        List<String> listText = new LinkedList<>();
        listText.add("Graph Density Report");
        listText.add("Parameters: ");
        listText.add("Snapshot : " + snapshot.getValue());
        listText.add("Results: ");
        listText.add("Average Communication Length: " + avgCount);
        content.add(new TextContent(listText.toArray(new String[0])));
        return content.toArray(new Content[0]);
    }

    @Override
    public boolean cancel() {
        this.isCanceled = true;
        return true;
    }

    @Override
    public void setProgressTicket(ProgressTicket progressTicket) {
        this.progress = progressTicket;
    }

    @Override
    public String getReport() {
        return "<HTML> <BODY> <h1>Average Communication Length </h1> "
                + "<hr>"
                + "<br>"
                + "<h2> Parameters: </h2>"
                + "Snapshot:  " + snapshot.getValue() + "<br>"
                + "<br> <h2> Results: </h2>"
                + "Average Communication Length: " + avgCount
                + "</BODY></HTML>";
    }
}
