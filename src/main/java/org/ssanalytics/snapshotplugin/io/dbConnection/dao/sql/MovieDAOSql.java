package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IMovieDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.movie.IMovie;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.music.IMusic;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ICategorizedDomain;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.movie.MovieSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.music.MusicSql;

public class MovieDAOSql extends AbstractSuperDAOSql implements IMovieDAO {

    private static MovieDAOSql instance = null;

    protected MovieDAOSql() throws SQLException {
        super();
        this.table_name = "movie";
    }

    public static MovieDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new MovieDAOSql();
        }
        return instance;
    }

    public void saveMovieList(List<IMovie> toSave, long accountId) throws SQLException {

        if (toSave == null) {
            return;
        }

        PreparedStatement pst = this.prepareStatement("INSERT INTO movie (id, account_id, fb_id, fb_name, category, created_time) VALUES (?, ?, ?, ?, ?, ?)");
        pst.clearBatch();

        for (IMovie movie : toSave) {

            long movie_id = IdProvider.getInstance().getNextId();

            this.setLongOrNull(1, movie_id, pst);
            this.setLongOrNull(2, accountId, pst);
            this.setStringOrNull(3, movie.getId(), pst);
            this.setStringOrNull(4, movie.getName(), pst);
            this.setStringOrNull(5, movie.getCategory(), pst);
            this.setLongOrNull(6, movie.getCreated_time(), pst);

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
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameMoviesAsProfileForSnapshotSpecificVersion(String snapshotName, String version, String profile_id) throws SQLException {

        List<IMovie> profMusic = this.getMovieListForProfileInSnapshotSpecificVersion(profile_id, snapshotName, version);

        Map<ICategorizedDomain, List<NamedItem>> map = new HashMap<>();

        List<NamedItem> friends;

        long accountDbId = this.getAccountDbId(profile_id, snapshotName, version);

        PreparedStatement pst = this.prepareStatement("SELECT account_id from " + this.table_name + " WHERE fb_id = ? AND account_id != ?");
        this.setLongOrNull(2, accountDbId, pst);
        ResultSet rs;

        for (IMovie m : profMusic) {
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
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameMoviesAsProfileForSnapshotLatestVersion(String snapshotName, String profile_id) throws Exception {
        return this.getWhoLikesTheSameMoviesAsProfileForSnapshotSpecificVersion(snapshotName, this.getHighestVersion(snapshotName), profile_id);
    }

    @Override
    public List<IMovie> getMovieListForProfileInSnapshotLatestVersion(String profileId, String snapshotId) throws SQLException {
        return this.getMovieListForProfileInSnapshotSpecificVersion(profileId, snapshotId, this.getHighestVersion(snapshotId));
    }

    @Override
    public List<IMovie> getMovieListForProfileInSnapshotSpecificVersion(String profileId, String snapshotId, String version) throws SQLException {
        List<IMovie> returnList = new LinkedList<>();

        PreparedStatement pst = this.prepareStatement("SELECT fb_id, fb_name, category, created_time FROM " + this.table_name + " WHERE account_id = ?");
        this.setLongOrNull(1, this.getAccountDbId(profileId, snapshotId, version), pst);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            returnList.add(new MovieSql(rs.getString("fb_id"), rs.getString("fb_name"), rs.getLong("created_time"), rs.getString("category")));
        }
        return returnList;
    }

    @Override
    public List<String> getDistinctMovieCategories() throws Exception {
        return CategoryItemDAOSql.getInstance().getDistinctCategories(this.table_name);
    }

    @Override
    public Map<String, Integer> getExtendedDistinctMovieCategories() throws Exception {
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
