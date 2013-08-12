package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedToDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedCommentsDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedFromDAOSql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.openide.util.NotImplementedException;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.inbox.IInbox;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IOutboxDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sharedHelpers.WordCountHelper;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.outbox.IOutbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.inbox.InboxSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.outbox.OutboxSql;

public class OutboxDAOSql extends AbstractSuperDAOSql implements IOutboxDAO {

    private static OutboxDAOSql instance = null;

    protected OutboxDAOSql() throws SQLException {
        super();
        this.table_name = "outbox";
    }

    public static OutboxDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new OutboxDAOSql();
        }
        return instance;
    }

    public void saveOutboxList(List<IOutbox> toSave, long accountId) throws SQLException {

        if (toSave == null) {
            return;
        }

        PreparedStatement pst = this.prepareStatement("INSERT INTO outbox (id, account_id, message, unseen, unread, updated_time) VALUES (?, ?, ?, ?, ?, ?)");
        pst.clearBatch();

        for (IOutbox outbox : toSave) {
            long outbox_id = IdProvider.getInstance().getNextId();

            this.setLongOrNull(1, outbox_id, pst);
            this.setLongOrNull(2, accountId, pst);
            this.setStringOrNull(3, outbox.getMessage(), pst);
            this.setIntegerOrNull(4, outbox.getUnseen(), pst);
            this.setIntegerOrNull(5, outbox.getUnread(), pst);
            this.setLongOrNull(6, outbox.getUpdated_time(), pst);

            pst.addBatch();

            // save comments
            SharedCommentsDAOSql.getInstance().saveSharedComments(outbox.getComments(), outbox_id, this.table_name);

            // save from
            SharedFromDAOSql.getInstance().saveSharedFrom(outbox.getFrom(), outbox_id, this.table_name);

            // save to
            SharedToDAOSql.getInstance().saveSharedTo(outbox.getTo(), outbox_id, this.table_name);

        }
        pst.executeBatch();

    }

    @Override
    public List<IOutbox> getOutboxListForRootAccountOfSnapshotSpecificVersion(String snapshotInfo, String version) throws SQLException {
        
        Long rootAccountId = this.getRootAccountDbId(snapshotInfo, version);
        if(rootAccountId == null)
            throw new SQLException("Could not find the root account for snapshot " + snapshotInfo + " and version " + version);
        
        PreparedStatement pst = this.prepareStatement("SELECT id, message, unseen, unread, updated_time FROM " + this.table_name + " WHERE account_id = ?");
        pst.setLong(1, rootAccountId);
        
        ResultSet rs = pst.executeQuery();
        
        List<IOutbox> outboxList = new LinkedList<>();
        
        while(rs.next()){
            long inbox_id = rs.getLong("id");
            int unseen = rs.getInt("unseen");
            int unread = rs.getInt("unread");
            long updated_time = rs.getLong("updated_time");
            String message = rs.getString("message");
            
            ISharedComments comments = CommentDAOSql.getInstance().getCommentsForTableSnapshotSpecificVersion(this.table_name, snapshotInfo, version).get(0);
            ISharedFrom from = SharedFromDAOSql.getInstance().getSharedFrom(inbox_id, this.table_name);
            ISharedTo to = SharedToDAOSql.getInstance().getSharedTo(inbox_id, this.table_name);
                        
            outboxList.add(new OutboxSql(null, comments, from, to, message, unseen, unread, updated_time));
        }
        
        return outboxList;
    }

    @Override
    public List<IOutbox> getOutboxListForRootAccountOfSnapshotLatestVersion(String snapshotInfo) throws SQLException {
        return this.getOutboxListForRootAccountOfSnapshotSpecificVersion(snapshotInfo, this.getHighestVersion(snapshotInfo));
    }

    @Override
    public Map<String, Integer> getWordCountForOutboxOfRootAccountSnapshotLatestVersion(String snapshotInfo) throws SQLException {
        return this.getWordCountForOutboxOfRootAccountSnapshotSpecificVersion(snapshotInfo, this.getHighestVersion(snapshotInfo));
    }

    @Override
    public Map<String, Integer> getWordCountForOutboxOfRootAccountSnapshotSpecificVersion(String snapshotInfo, String version) throws SQLException {
        Long root_db_id = this.getRootAccountDbId(snapshotInfo, version);

        if (root_db_id == null) {
            throw new SQLException("Root Account was not found for snapshot " + snapshotInfo);
        }

        PreparedStatement pst = this.prepareStatement("SELECT message FROM outbox WHERE account_id = ?");
        this.setLongOrNull(1, root_db_id, pst);

        ResultSet rs = pst.executeQuery();

        Map<String, Integer> retMap = new HashMap<>();

        while (rs.next()) {

            retMap = WordCountHelper.getInstance().countWordsInString(rs.getString("message"), retMap);
        }

        return retMap;
    }
}
