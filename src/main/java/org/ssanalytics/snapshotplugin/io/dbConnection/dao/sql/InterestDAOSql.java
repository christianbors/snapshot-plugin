package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IInterestDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.activity.IActivity;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.interest.IInterest;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ICategorizedDomain;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.activity.ActivitySql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.interest.InterestSql;

public class InterestDAOSql extends AbstractSuperDAOSql implements IInterestDAO {

    private static InterestDAOSql instance = null;

    protected InterestDAOSql() throws SQLException {
        super();
        this.table_name = "interest";

    }

    public static InterestDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new InterestDAOSql();
        }
        return instance;
    }

    public void saveInterestList(List<IInterest> toSave, long accountId) throws SQLException {

        if (toSave == null) {
            return;
        }

        PreparedStatement pst = this.prepareStatement("INSERT INTO interest (id, account_id, fb_id, fb_name, category, created_time) VALUES (?, ?, ?, ?, ?, ?)");
        pst.clearBatch();

        for (IInterest interest : toSave) {
            long interest_id = IdProvider.getInstance().getNextId();
            this.setLongOrNull(1, interest_id, pst);
            this.setLongOrNull(2, accountId, pst);
            this.setStringOrNull(3, interest.getId(), pst);
            this.setStringOrNull(4, interest.getName(), pst);
            this.setStringOrNull(5, interest.getCategory(), pst);
            this.setLongOrNull(6, interest.getCreated_time(), pst);

            pst.addBatch();
        }

        pst.executeBatch();

    }

    @Override
    public List<String> getDistinctInterestCategories() throws Exception {
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
    public List<IInterest> getInterestListForProfileInSnapshotLatestVersion(String profileId, String snapshotId) throws SQLException {
        return this.getInterestListForProfileInSnapshotSpecificVersion(profileId, snapshotId, this.getHighestVersion(snapshotId));
    }

    @Override
    public List<IInterest> getInterestListForProfileInSnapshotSpecificVersion(String profileId, String snapshotId, String version) throws SQLException {
        List<IInterest> returnList = new LinkedList<>();

        PreparedStatement pst = this.prepareStatement("SELECT fb_id, fb_name, category FROM " + this.table_name + " WHERE account_id = ?");
        this.setLongOrNull(1, this.getAccountDbId(profileId, snapshotId, version), pst);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            returnList.add(new InterestSql(rs.getString("fb_id"), rs.getString("fb_name"), 0, rs.getString("category")));
        }
        return returnList;
    }

    @Override
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameInterestsAsProfileForSnapshotSpecificVersion(String snapshotName, String version, String profile_id) throws SQLException {

        List<IInterest> profInt = this.getInterestListForProfileInSnapshotSpecificVersion(profile_id, snapshotName, version);

        Map<ICategorizedDomain, List<NamedItem>> map = new HashMap<>();

        List<NamedItem> friends;

        Long accountDbId = this.getAccountDbId(profile_id, snapshotName, version);

        PreparedStatement pst = this.prepareStatement("SELECT account_id from " + this.table_name + " WHERE fb_id = ? AND account_id != ?");
        this.setLongOrNull(2, accountDbId, pst);
        ResultSet rs;

        for (IInterest i : profInt) {
            this.setStringOrNull(1, i.getId(), pst);
            rs = pst.executeQuery();

            if (rs.next()) {

                friends = new LinkedList<>();
                do {
                    friends.add(ProfileDAOSql.getInstance().getNameItemForAccountId(rs.getLong("account_id")));
                } while (rs.next());

                map.put(i, friends);
            }
        }

        return map;
    }

    @Override
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameInterestsAsProfileForSnapshotLatestVersion(String snapshotName, String profile_id) throws Exception {
        return this.getWhoLikesTheSameInterestsAsProfileForSnapshotSpecificVersion(snapshotName, this.getHighestVersion(snapshotName), profile_id);
    }

    @Override
    public Map<String, Integer> getExtendedDistinctInterestCategories() throws Exception {
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
