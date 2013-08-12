package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.print.attribute.HashPrintJobAttributeSet;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IMusicDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.music.IMusic;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ICategorizedDomain;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.INamedDomain;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.movie.MovieSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.music.MusicSql;

public class MusicDAOSql extends AbstractSuperDAOSql implements IMusicDAO {

    private static MusicDAOSql instance = null;

    protected MusicDAOSql() throws SQLException {
        super();
        this.table_name = "music";
    }

    public static MusicDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new MusicDAOSql();
        }
        return instance;
    }

    public void saveMusicList(List<IMusic> toSave, long accountId) throws SQLException {

        if (toSave == null) {
            return;
        }



        PreparedStatement pst = this.prepareStatement("INSERT INTO " + this.table_name + " (id, account_id, fb_id, fb_name, category, created_time) VALUES (?, ?, ?, ?, ?, ?)");
        pst.clearBatch();

        for (IMusic music : toSave) {

            long music_id = IdProvider.getInstance().getNextId();

            this.setLongOrNull(1, music_id, pst);
            this.setLongOrNull(2, accountId, pst);
            this.setStringOrNull(3, music.getId(), pst);
            this.setStringOrNull(4, music.getName(), pst);
            this.setStringOrNull(5, music.getCategory(), pst);
            this.setLongOrNull(6, music.getCreated_time(), pst);

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
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameMusicAsProfileForSnapshotSpecificVersion(String snapshotName, String version, String profile_id) throws SQLException {

        List<IMusic> profMusic = this.getMusicListForProfileInSnapshotSpecificVersion(profile_id, snapshotName, version);

        Map<ICategorizedDomain, List<NamedItem>> map = new HashMap<>();

        List<NamedItem> friends;

        long accountDbId = this.getAccountDbId(profile_id, snapshotName, version);

        PreparedStatement pst = this.prepareStatement("SELECT account_id from " + this.table_name + " WHERE fb_id = ? AND account_id != ?");
        this.setLongOrNull(2, accountDbId, pst);
        ResultSet rs;

        for (IMusic m : profMusic) {
            this.setStringOrNull(1, m.getId(), pst);
            rs = pst.executeQuery();

            if (rs.next()) {

                friends = new LinkedList<>();
                do {
                    friends.add(ProfileDAOSql.getInstance().getNameItemForAccountId(rs.getLong("account_id")));
                } while (rs.next());

                map.put(m, friends);
            }
        }

        return map;
    }

    @Override
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameMusicAsProfileForSnapshotLatestVersion(String snapshotName, String profile_id) throws Exception {
        return this.getWhoLikesTheSameMusicAsProfileForSnapshotSpecificVersion(snapshotName, this.getHighestVersion(snapshotName), profile_id);
    }

    @Override
    public List<IMusic> getMusicListForProfileInSnapshotLatestVersion(String profileId, String snapshotId) throws SQLException {
        return this.getMusicListForProfileInSnapshotSpecificVersion(profileId, snapshotId, this.getHighestVersion(snapshotId));
    }

    @Override
    public List<IMusic> getMusicListForProfileInSnapshotSpecificVersion(String profileId, String snapshotId, String version) throws SQLException {
        List<IMusic> returnList = new LinkedList<>();

        PreparedStatement pst = this.prepareStatement("SELECT fb_id, fb_name, category, created_time FROM " + this.table_name + " WHERE account_id = ?");
        this.setLongOrNull(1, this.getAccountDbId(profileId, snapshotId, version), pst);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            returnList.add(new MusicSql(rs.getString("fb_id"), rs.getString("fb_name"), rs.getLong("created_time"), rs.getString("category")));
        }
        return returnList;
    }

    @Override
    public List<String> getDistinctMusicCategories() throws SQLException {
        return CategoryItemDAOSql.getInstance().getDistinctCategories(this.table_name);
    }

    @Override
    public Map<String, Integer> getExtendedDistinctMusicCategories() throws Exception {
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
