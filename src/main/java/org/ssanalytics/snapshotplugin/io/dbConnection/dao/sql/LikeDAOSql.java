package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.ILikeDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.CategoryItemDAOMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.book.IBook;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.likes.ILike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ICategorizedDomain;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.book.BookSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.likes.LikeSql;

public class LikeDAOSql extends AbstractSuperDAOSql implements ILikeDAO {

    private static LikeDAOSql instance = null;

    protected LikeDAOSql() throws SQLException {
        super();
        this.table_name = "fb_like";
    }

    public static LikeDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new LikeDAOSql();
        }
        return instance;
    }

    public void saveLikeList(List<ILike> toSave, long accountId) throws SQLException {

        if (toSave == null) {
            return;
        }



        PreparedStatement pst = this.prepareStatement("INSERT INTO " + this.table_name + " (id, account_id, fb_id, fb_name, category, created_time) VALUES (?, ?, ?, ?, ?, ?)");
        pst.clearBatch();

        for (ILike like : toSave) {

            long like_id = IdProvider.getInstance().getNextId();

            this.setLongOrNull(1, like_id, pst);
            this.setLongOrNull(2, accountId, pst);
            this.setStringOrNull(3, like.getId(), pst);
            this.setStringOrNull(4, like.getName(), pst);
            this.setStringOrNull(5, like.getCategory(), pst);
            this.setLongOrNull(6, like.getCreated_time(), pst);

            pst.addBatch();
        }


        pst.executeBatch();

    }

    @Override
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotLatestVersion(String snapshotId) throws SQLException {
        return this.getEvidenceCountForSnapshotSpecificVersion(snapshotId, this.getHighestVersion(snapshotId));
    }

    @Override
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotSpecificVersion(String snapshotId, String version) throws SQLException {
        return CategoryItemDAOSql.getInstance().getEvidenceCountForSnapshotAndVersion(snapshotId, version, this.table_name);
    }

    @Override
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameAsProfileForSnapshotSpecificVersion(String snapshotName, String version, String profile_id) throws SQLException {

        List<ILike> profLike = this.getLikeListForProfileInSnapshotSpecificVersion(profile_id, snapshotName, version);

        Map<ICategorizedDomain, List<NamedItem>> map = new HashMap<>();

        List<NamedItem> friends;

        Long accountDbId = this.getAccountDbId(profile_id, snapshotName, version);

        PreparedStatement pst = this.prepareStatement("SELECT account_id from " + this.table_name + " WHERE fb_id = ? AND account_id != ?");
        this.setLongOrNull(2, accountDbId, pst);
        ResultSet rs;

        for (ILike l : profLike) {
            this.setStringOrNull(1, l.getId(), pst);
            rs = pst.executeQuery();

            if (rs.next()) {

                friends = new LinkedList<>();
                do {
                    friends.add(ProfileDAOSql.getInstance().getNameItemForAccountId(rs.getLong("account_id")));
                } while (rs.next());

                map.put(l, friends);
            }
        }

        return map;
    }

    @Override
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameAsProfileForSnapshotLatestVersion(String snapshotName, String profile_id) throws Exception {
        return this.getWhoLikesTheSameAsProfileForSnapshotSpecificVersion(snapshotName, this.getHighestVersion(snapshotName), profile_id);
    }

    @Override
    public List<ILike> getLikeListForProfileInSnapshotLatestVersion(String profileId, String snapshotId) throws SQLException {
        return this.getLikeListForProfileInSnapshotSpecificVersion(profileId, snapshotId, this.getHighestVersion(snapshotId));
    }

    @Override
    public List<ILike> getLikeListForProfileInSnapshotSpecificVersion(String profileId, String snapshotId, String version) throws SQLException {
        List<ILike> returnList = new LinkedList<>();

        PreparedStatement pst = this.prepareStatement("SELECT fb_id, fb_name, category, created_time FROM " + this.table_name + " WHERE account_id = ?");
        this.setLongOrNull(1, this.getAccountDbId(profileId, snapshotId, version), pst);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            returnList.add(new LikeSql(rs.getString("fb_id"), rs.getString("fb_name"), rs.getLong("created_time"), rs.getString("category")));
        }
        return returnList;
    }

    @Override
    public List<String> getDistinctLikeCategories() throws SQLException {
        return CategoryItemDAOSql.getInstance().getDistinctCategories(this.table_name);
    }

    @Override
    public Map<String, Integer> getExtendedDistinctLikeCategories() throws Exception {
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
