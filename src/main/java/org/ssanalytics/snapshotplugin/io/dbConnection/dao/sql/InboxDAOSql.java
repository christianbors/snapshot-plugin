package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedToDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedCommentsDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedFromDAOSql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IInboxDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.inbox.IInbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.inbox.InboxSql;

public class InboxDAOSql extends AbstractSuperDAOSql implements IInboxDAO{

    private static InboxDAOSql instance = null;

    protected InboxDAOSql() throws SQLException {
        super();
        this.table_name = "inbox";
    }

    public static InboxDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new InboxDAOSql();
        }
        return instance;
    }

    public void saveInboxList(List<IInbox> toSave, long accountId) throws SQLException {

        if (toSave == null) {
            return;
        }
        
        PreparedStatement pst = this.prepareStatement("INSERT INTO inbox (id, account_id, unseen, unread, updated_time, message, fb_id) VALUES (?, ?, ?, ?, ?, ?, ?)");
        pst.clearBatch();

        for (IInbox inbox : toSave) {

            long inbox_id = IdProvider.getInstance().getNextId();

            this.setLongOrNull(1, inbox_id, pst);
            this.setLongOrNull(2, accountId, pst);

            Integer unseen = inbox.getUnseen();
            this.setIntegerOrNull(3, unseen, pst);

            Integer unread = inbox.getUnread();
            this.setIntegerOrNull(4, unread, pst);

            Long time = inbox.getUpdated_time();

            this.setLongOrNull(5, time, pst);

            this.setStringOrNull(6, inbox.getMessage(), pst);
            
            this.setStringOrNull(7, inbox.getId(), pst);

            pst.addBatch();

            // save comments
            SharedCommentsDAOSql.getInstance().saveSharedComments(inbox.getComments(), inbox_id, this.table_name);

            // save from
            SharedFromDAOSql.getInstance().saveSharedFrom(inbox.getFrom(), inbox_id, this.table_name);

            // save to
            SharedToDAOSql.getInstance().saveSharedTo(inbox.getTo(), inbox_id, this.table_name);

        }

        pst.executeBatch();
    }

    @Override
    public List<IInbox> getInboxListForRootAccountOfSnapshotSpecificVersion(String snapshotInfo, String version) throws SQLException {
        
        Long rootAccountId = this.getRootAccountDbId(snapshotInfo, version);
        if(rootAccountId == null)
            throw new SQLException("Could not find the root account for snapshot " + snapshotInfo + " and version " + version);
        
        PreparedStatement pst = this.prepareStatement("SELECT id, fb_id, unseen, unread, updated_time, message FROM " + this.table_name + " WHERE account_id = ?");
        pst.setLong(1, rootAccountId);
        
        ResultSet rs = pst.executeQuery();
        
        List<IInbox> inboxList = new LinkedList<>();
        
        while(rs.next()){
            long inbox_id = rs.getLong("id");
            int unseen = rs.getInt("unseen");
            int unread = rs.getInt("unread");
            long updated_time = rs.getLong("updated_time");
            String fb_id = rs.getString("fb_id");
            String message = rs.getString("message");
            
            ISharedComments comments = SharedCommentsDAOSql.getInstance().getComments(inbox_id, this.table_name);
            ISharedFrom from = SharedFromDAOSql.getInstance().getSharedFrom(inbox_id, this.table_name);
            ISharedTo toList = SharedToDAOSql.getInstance().getSharedTo(inbox_id, this.table_name);
            
            inboxList.add(new InboxSql(fb_id, comments, from, toList, unseen, unread, updated_time, message));            
        }
        
        return inboxList;
    }

    @Override
    public List<IInbox> getInboxListForRootAccountOfSnapshotLatestVersion(String snapshotInfo) throws Exception {
        return this.getInboxListForRootAccountOfSnapshotSpecificVersion(snapshotInfo, this.getHighestVersion(snapshotInfo));
    }
}
