package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedToDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedActionDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedCommentsDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedFromDAOSql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IFeedDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedPrivacyDAOSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.feed.IFeed;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPrivacy;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.feed.FeedSql;

public class FeedDAOSql extends AbstractSuperDAOSql implements IFeedDAO {

    private static FeedDAOSql instance = null;

    protected FeedDAOSql() throws SQLException {
        super();
        this.table_name = "feed";
    }

    public static FeedDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new FeedDAOSql();
        }
        return instance;
    }

    public void saveFeedList(List<IFeed> toSave, long accountId) throws SQLException {

        if (toSave == null) {
            return;
        }
        
        PreparedStatement pst = this.prepareStatement("INSERT INTO feed (id, account_id, created_time, message, feed_type, updated_time, fb_id) VALUES (?, ?, ?, ?, ?, ?, ?)");
        pst.clearBatch();

        for (IFeed feed : toSave) {
            long feedId = IdProvider.getInstance().getNextId();

            this.setLongOrNull(1, feedId, pst);
            this.setLongOrNull(2, accountId, pst);

            this.setLongOrNull(3, feed.getCreated_time(), pst);

            this.setStringOrNull(4, feed.getMessage(), pst);
            this.setStringOrNull(5, feed.getType(), pst);

            this.setLongOrNull(6, feed.getUpdated_time(), pst);
            
            this.setStringOrNull(7, feed.getId(), pst);

            pst.addBatch();

            // action list
            SharedActionDAOSql.getInstance().saveSharedAction(feed.getActionList(), feedId, this.table_name);

            // comments
            SharedCommentsDAOSql.getInstance().saveSharedComments(feed.getComments(), feedId, this.table_name);

            // shared from
            SharedFromDAOSql.getInstance().saveSharedFrom(feed.getFrom(), feedId, this.table_name);

            // shared to
            SharedToDAOSql.getInstance().saveSharedTo(feed.getTo(), feedId, this.table_name);
            
            // privacy
            SharedPrivacyDAOSql.getInstance().saveSharedPrivacy(feed.getPrivacy(), feedId, this.table_name);

        }

        pst.executeBatch();

    }

    @Override
    public Map<NamedItem, Integer> getWhoPostedOnWallForRootAccountOfSnapshotLatestVersion(String snapshotName) throws SQLException {
        String highestVersion = this.getHighestVersion(snapshotName);
        return this.getWhoPostedOnWallForRootAccountOfSnapshotSpecificVersion(snapshotName, highestVersion);
    }

    @Override
    public Map<NamedItem, Integer> getWhoPostedOnWallForRootAccountOfSnapshotSpecificVersion(String snapshotName, String version) throws SQLException {
        PreparedStatement pst = this.prepareStatement("SELECT fb_id, fb_name FROM shared_from WHERE papa_name = 'feed' AND papa_id in (SELECT ID FROM FEED WHERE account_id = (SELECT root_db_id FROM snapshotinfo where snapshotname = ? AND version = ?))");

        this.setStringOrNull(1, snapshotName, pst);
        this.setStringOrNull(2, version, pst);

        ResultSet rs = pst.executeQuery();

        Map<NamedItem, Integer> returnMap = new HashMap<>();
        NamedItem item;

        while (rs.next()) {
            item = new NamedItem(rs.getString("fb_id"), rs.getString("fb_name"));
            if (returnMap.containsKey(item)) {
                Integer i = returnMap.get(item);
                i++;
                returnMap.remove(item);
                returnMap.put(item, i);
            } else {
                returnMap.put(item, 1);
            }
        }

        return returnMap;
    }

    @Override
    public List<IFeed> getAllFeedsForRootAccountOfSnapshotLatestVersion(String snapshotName) throws SQLException {
        return this.getAllFeedsForRootAccountOfSnapshotSpecificVersion(snapshotName, this.getHighestVersion(snapshotName));
    }

    @Override
    public List<IFeed> getAllFeedsForRootAccountOfSnapshotSpecificVersion(String snapshotName, String version) throws SQLException {
        Long racDbId = this.getRootAccountDbId(snapshotName, version);
        
        if(racDbId == null)
            throw new SQLException("Could not find root account for snapshot: " + snapshotName + " and version: " + version);
        
        PreparedStatement pst = this.prepareStatement("SELECT id, fb_id, created_time, message, feed_type, updated_time FROM feed WHERE account_id = ?");
        this.setLongOrNull(1, racDbId, pst);
        
        ResultSet rs = pst.executeQuery();
        
        List<IFeed> feedList = new LinkedList<>();
        
        while(rs.next()){
            long feedId = rs.getLong("id");
            Long created_time = rs.getLong("created_time");
            Long updated_time = rs.getLong("updated_time");
            String message = rs.getString("message");
            String feed_type = rs.getString("feed_type");
            String fb_id = rs.getString("fb_id");
            
            List<ISharedAction> actionList = SharedActionDAOSql.getInstance().getSharedActionList(feedId, this.table_name);
            ISharedComments comments = SharedCommentsDAOSql.getInstance().getComments(feedId, this.table_name);
            ISharedFrom from = SharedFromDAOSql.getInstance().getSharedFrom(feedId, this.table_name);
            ISharedTo to = SharedToDAOSql.getInstance().getSharedTo(feedId, this.table_name);
            ISharedPrivacy privacy = SharedPrivacyDAOSql.getInstance().getSharedPrivacy(feedId, this.table_name);
            
            IFeed feed = new FeedSql(fb_id, actionList, comments, created_time, from, message, to, feed_type, updated_time, privacy);
            
            feedList.add(feed);            
        }
        
        return feedList;        
    }

    @Override
    public List<ISharedComments> getAllCommentsInSnapshotLatestVersion(String snapshot) throws SQLException {
        return this.getAllCommentsInSnapshotSpecificVersion(snapshot, this.getHighestVersion(snapshot));
    }

    @Override
    public List<ISharedComments> getAllCommentsInSnapshotSpecificVersion(String snapshot, String version) throws SQLException {
        return CommentDAOSql.getInstance().getCommentsForTableSnapshotSpecificVersion(this.table_name, snapshot, version);
    }
}
