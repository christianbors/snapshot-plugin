/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.exportReport.contentGenerator;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.feed.IFeed;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.inbox.IInbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.outbox.IOutbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedCommentData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ReportContent;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.Content;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.TableContent;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.TextContent;

/**
 *
 * @author christian
 */
@ServiceProvider(service = ReportContent.class)
public class CommunicationProtocol implements ReportContent {

    private boolean includePrivate = false;
    private boolean includeWall = false;
    private List<Content> communicationList;

    @Override
    public void generate(ISnapshotInfo snapshot) {
        communicationList = new LinkedList<>();
        if (includePrivate) {
            try {
                communicationList.add(new TextContent("Outbox Communication"));
                communicationList.add(new TextContent("\n"));
                for (IOutbox box : DaoFactory.getOutboxDAO().getOutboxListForRootAccountOfSnapshotLatestVersion(snapshot.getValue())) {
                    communicationList.addAll(getTableContent(box));
                }
                communicationList.add(new TextContent("Inbox Communication"));
                communicationList.add(new TextContent("\n"));
                for (IInbox box : DaoFactory.getInboxDAO().getInboxListForRootAccountOfSnapshotLatestVersion(snapshot.getValue())) {
                    communicationList.addAll(getTableContent(box));
                }
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        if (includeWall) {
            try {
                communicationList.add(new TextContent("Wall Feed"));
                communicationList.add(new TextContent("\n"));
                for (IFeed feed : DaoFactory.getFeedDAO().getAllFeedsForRootAccountOfSnapshotLatestVersion(snapshot.getValue())) {
                    communicationList.addAll(getTableContent(feed));
                }
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    @Override
    public Content[] getContent() {
        return communicationList.toArray(new Content[0]);
    }

    public void setProfileList(List<IProfile> profileList) {
    }

    public void includePrivateCommunication(boolean include) {
        this.includePrivate = include;
    }

    public void includeWallCommunication(boolean include) {
        this.includeWall = include;
    }

    private List<Content> getTableContent(IOutbox box) {
        List<Content> listTables = new LinkedList<>();
        Map<String, String> table = new LinkedHashMap<>();
        table.put("name", box.getFrom().getName());
        table.put("updated time", new Date(box.getUpdated_time()).toString());
        table.put("message", box.getMessage());
        listTables.add(new TableContent(table));
        if (box.getComments().getDataList().size() > 0) {
            listTables.addAll(getCommentTables(box.getComments().getDataList()));
        }
        listTables.add(new TextContent("\n"));
        return listTables;
    }

    private List<Content> getTableContent(IInbox box) {
        List<Content> listTables = new LinkedList<>();
        Map<String, String> table = new LinkedHashMap<>();
        table.put("name", box.getFrom().getName());
        table.put("updated time", new Date(box.getUpdated_time()).toString());
        table.put("message", box.getMessage());
        listTables.add(new TableContent(table));
        if (box.getComments().getDataList().size() > 0) {
            listTables.addAll(getCommentTables(box.getComments().getDataList()));
        }
        listTables.add(new TextContent("\n"));
        return listTables;
    }

    private List<Content> getTableContent(IFeed feed) {
        List<Content> listTables = new LinkedList<>();
        Map<String, String> table = new LinkedHashMap<>();
        table.put("name", feed.getFrom().getName());
        table.put("created time", new Date(feed.getCreated_time()).toString());
        if (feed.getUpdated_time() != null) {
            table.put("updated time", new Date(feed.getUpdated_time()).toString());
        }
        table.put("message", feed.getMessage());
        table.put("privacy setting", feed.getPrivacy().getValue());
        listTables.add(new TableContent(table));
        if (feed.getComments().getDataList().size() > 0) {
            listTables.addAll(getCommentTables(feed.getComments().getDataList()));
        }
        listTables.add(new TextContent("\n"));
        return listTables;
    }

    private List<TableContent> getCommentTables(List<ISharedCommentData> comments) {

        List<TableContent> listComments = new LinkedList<>();
        List<String> reply = new LinkedList<>();
        reply.add("Replies");
        listComments.add(new TableContent(reply));
        for (ISharedCommentData comment : comments) {
            Map<String, String> table = new LinkedHashMap<>();
            if (comment.getFrom() != null) {
                table.put("name", comment.getFrom().getName());
                table.put("created time", new Date(comment.getCreatedTime()).toString());
                table.put("message", comment.getMessage());
                listComments.add(new TableContent(table));
            }
        }
        return listComments;
    }
}
