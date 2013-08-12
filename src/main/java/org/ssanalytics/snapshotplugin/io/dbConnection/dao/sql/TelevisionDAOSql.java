package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.ITelevisionDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.music.IMusic;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ICategorizedDomain;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.television.ITelevision;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.television.TelevisionSql;

public class TelevisionDAOSql extends AbstractSuperDAOSql implements ITelevisionDAO {

    private static TelevisionDAOSql instance = null;

    protected TelevisionDAOSql() throws SQLException {
        super();
        this.table_name = "television";
    }

    public static TelevisionDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new TelevisionDAOSql();
        }
        return instance;
    }

    public void saveTelevision(ITelevision toSave, long accountId) throws SQLException {
        if (toSave == null) {
            return;
        }

        ArrayList<ITelevision> al = new ArrayList<>(1);
        al.add(toSave);
        this.saveTelevisionList(al, accountId);
    }

    public void saveTelevisionList(List<ITelevision> toSave, long accountId) throws SQLException {

        if (toSave == null) {
            return;
        }

        PreparedStatement pst = this.prepareStatement("INSERT INTO " + this.table_name + " (id, account_id, category, created_time, fb_name, fb_id) VALUES(?, ?, ?, ?, ?, ?)");
        pst.clearBatch();

        for (ITelevision tv : toSave) {

            long television_id = IdProvider.getInstance().getNextId();
            pst.clearParameters();

            this.setLongOrNull(1, television_id, pst);
            this.setLongOrNull(2, accountId, pst);
            this.setStringOrNull(3, tv.getCategory(), pst);
            this.setLongOrNull(4, tv.getCreated_time(), pst);
            this.setStringOrNull(5, tv.getName(), pst);
            this.setStringOrNull(6, tv.getId(), pst);

            pst.addBatch();
        }

        try {
            pst.executeBatch();
        } catch (SQLException sex) {
            sex.getNextException().printStackTrace();
        }

    }

    @Override
    public Map<CategoryItem, Integer> getTelevisionEvidenceCountForSnapshotLatestVersion(String snapshotName) throws Exception {
        return this.getTelevisionEvidenceCountForSnapshotSpecificVersion(snapshotName, this.getHighestVersion(snapshotName));
    }

    @Override
    public Map<CategoryItem, Integer> getTelevisionEvidenceCountForSnapshotSpecificVersion(String snapshotName, String version) throws Exception {
        return CategoryItemDAOSql.getInstance().getEvidenceCountForSnapshotAndVersion(snapshotName, version, this.table_name);
    }

    @Override
    public List<ITelevision> getTelevisionListForProfileInSnapshotLatestVersion(String snapshotName, String accountId) throws SQLException {
        return this.getTelevisionListForProfileInSnapshotLatestVersion(snapshotName, accountId);
    }

    @Override
    public List<ITelevision> getTelevisionListForProfileInSnapshotSpecificVersion(String snapshotName, String version, String accountId) throws SQLException {

        List<ITelevision> returnList = new LinkedList<>();

        PreparedStatement pst = this.prepareStatement("SELECT fb_id, fb_name, category, created_time FROM " + this.table_name + " WHERE account_id = ?");
        this.setLongOrNull(1, this.getAccountDbId(accountId, snapshotName, version), pst);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            returnList.add(new TelevisionSql(rs.getString("fb_id"), rs.getString("fb_name"), rs.getLong("created_time"), rs.getString("category")));
        }
        return returnList;
    }

    @Override
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameTelevisionAsProfileForSnapshotSpecificVersion(String snapshotName, String version, String profile_id) throws SQLException {


        List<ITelevision> profTv = this.getTelevisionListForProfileInSnapshotSpecificVersion(snapshotName, version, profile_id);

        Map<ICategorizedDomain, List<NamedItem>> map = new HashMap<>();

        List<NamedItem> friends;

        long accountDbId = this.getAccountDbId(profile_id, snapshotName, version);

        PreparedStatement pst = this.prepareStatement("SELECT account_id from " + this.table_name + " WHERE fb_id = ? AND account_id != ?");
        this.setLongOrNull(2, accountDbId, pst);
        ResultSet rs;

        for (ITelevision tv : profTv) {
            this.setStringOrNull(1, tv.getId(), pst);
            rs = pst.executeQuery();

            if (rs.next()) {

                friends = new LinkedList<>();
                do {
                    friends.add(ProfileDAOSql.getInstance().getNameItemForAccountId(rs.getLong("account_id")));
                } while (rs.next());

                map.put(tv, friends);
            }
        }

        return map;
    }

    @Override
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameTelevisionAsProfileForSnapshotLatestVersion(String snapshotName, String profile_id) throws SQLException {
        return this.getWhoLikesTheSameTelevisionAsProfileForSnapshotSpecificVersion(snapshotName, this.getHighestVersion(snapshotName), profile_id);
    }

    @Override
    public List<String> getDistinctTelevisionCategories() throws SQLException {

        return CategoryItemDAOSql.getInstance().getDistinctCategories(this.table_name);
    }

    @Override
    public Map<String, Integer> getExtendedDistinctTelevisionCategories() throws Exception {
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
