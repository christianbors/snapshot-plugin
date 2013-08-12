package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IActivityDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.activity.IActivity;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.book.IBook;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ICategorizedDomain;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.activity.ActivitySql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.book.BookSql;

public class ActivityDAOSql extends AbstractSuperDAOSql implements IActivityDAO {

    private static ActivityDAOSql instance = null;

    protected ActivityDAOSql() throws SQLException {
        super();
        this.table_name = "activity";
    }

    public static ActivityDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new ActivityDAOSql();
        }
        return instance;
    }

    public void saveActivityList(List<IActivity> toSave, long accountId)
            throws SQLException {

        if (toSave == null) {
            return;
        }


        PreparedStatement pst = this.prepareStatement("INSERT INTO activity (id, account_id, fb_id, fb_name, category, created_time) VALUES (?, ?, ?, ?, ?, ?)");
        pst.clearBatch();
        for (IActivity activity : toSave) {

            long activityId = IdProvider.getInstance().getNextId();
            this.setLongOrNull(1, activityId, pst);
            this.setLongOrNull(2, accountId, pst);
            this.setStringOrNull(3, activity.getId(), pst);
            this.setStringOrNull(4, activity.getName(), pst);
            this.setStringOrNull(5, activity.getCategory(), pst);
            this.setLongOrNull(6, activity.getCreated_time(), pst);

            pst.addBatch();
        }

        pst.executeBatch();

    }

    @Override
    public List<String> getDistinctActivityCategories() throws Exception {
        return CategoryItemDAOSql.getInstance().getDistinctCategories(this.table_name);
    }

    @Override
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotLatestVersion(String snapshotName) throws SQLException {
        return this.getEvidenceCountForSnapshotSpecificVersion(snapshotName, this.getHighestVersion(snapshotName));
    }

    @Override
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotSpecificVersion(String snapshotName, String version) throws SQLException {
        return CategoryItemDAOSql.getInstance().getEvidenceCountForSnapshotAndVersion(snapshotName, version, this.table_name);
    }

    @Override
    public List<IActivity> getActivityListForProfileInSnapshotLatestVersion(String profileId, String snapshotId) throws SQLException {
        return this.getActivityListForProfileInSnapshotSpecificVersion(profileId, snapshotId, this.getHighestVersion(snapshotId));
    }

    @Override
    public List<IActivity> getActivityListForProfileInSnapshotSpecificVersion(String profileId, String snapshotId, String version) throws SQLException {
        List<IActivity> returnList = new LinkedList<>();

        PreparedStatement pst = this.prepareStatement("SELECT fb_id, fb_name, category FROM " + this.table_name + " WHERE account_id = ?");
        this.setLongOrNull(1, this.getAccountDbId(profileId, snapshotId, version), pst);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            returnList.add(new ActivitySql(rs.getString("fb_id"), rs.getString("fb_name"), 0, rs.getString("category")));
        }
        return returnList;
    }

    @Override
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameActivitiesAsProfileForSnapshotSpecificVersion(String snapshotName, String version, String profile_id) throws SQLException {

        List<IActivity> profAct = this.getActivityListForProfileInSnapshotSpecificVersion(profile_id, snapshotName, version);

        Map<ICategorizedDomain, List<NamedItem>> map = new HashMap<>();

        List<NamedItem> friends;

        Long accountDbId = this.getAccountDbId(profile_id, snapshotName, version);

        PreparedStatement pst = this.prepareStatement("SELECT account_id from " + this.table_name + " WHERE fb_id = ? AND account_id != ?");
        this.setLongOrNull(2, accountDbId, pst);
        ResultSet rs;

        for (IActivity a : profAct) {
            this.setStringOrNull(1, a.getId(), pst);
            rs = pst.executeQuery();

            if (rs.next()) {

                friends = new LinkedList<>();
                do {
                    friends.add(ProfileDAOSql.getInstance().getNameItemForAccountId(rs.getLong("account_id")));
                } while (rs.next());

                map.put(a, friends);
            }
        }

        return map;
    }

    @Override
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameActivitiesAsProfileForSnapshotLatestVersion(String snapshotName, String profile_id) throws Exception {
        return this.getWhoLikesTheSameActivitiesAsProfileForSnapshotSpecificVersion(snapshotName, this.getHighestVersion(snapshotName), profile_id);
    }

    @Override
    public Map<String, Integer> getExtendedDistinctActivityCategories() throws Exception {
        return CategoryItemDAOSql.getInstance().getExtendedDistinctCategories(this.table_name);
    }
    
    @Override
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotLatestVersion(String snapshotName, long timeStart, long timeEnd) throws SQLException {
        return this.getEvidenceCountForSnapshotSpecificVersion(snapshotName, this.getHighestVersion(snapshotName), timeStart, timeEnd);
    }

    @Override
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotSpecificVersion(String snapshotName, String version, long timeStart, long timeEnd) throws SQLException {
        return CategoryItemDAOSql.getInstance().getEvidenceCountForSnapshotAndVersion(snapshotName, version, this.table_name, timeStart, timeEnd);
    }
}
