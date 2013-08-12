/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.movie.IMovie;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ICategorizedDomain;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account.RootAccountJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.movie.MovieJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IMovieDAO;

/**
 *
 * @author chw
 */
public class MovieDAOMongo extends AbstractSuperDAOMongo implements IMovieDAO {

    private final String collection_name = "movies";

    private MovieDAOMongo() throws UnknownHostException, MongoException {
        super();
    }
    private static MovieDAOMongo instance = null;

    public static MovieDAOMongo getInstance() throws UnknownHostException, MongoException {
        if (instance == null) {
            instance = new MovieDAOMongo();
        }
        return instance;
    }
    
    @Override
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotLatestVersion(String snapshotName, long timeStart, long timeEnd) throws UnknownHostException, MongoException {
        return this.getEvidenceCountForSnapshotSpecificVersion(snapshotName, this.findHighestVersionForSnapshot(snapshotName), timeStart, timeEnd);
    }

    @Override
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotSpecificVersion(String snapshotName, String version, long timeStart, long timeEnd) throws UnknownHostException, MongoException  {
        return CategoryItemDAOMongo.getInstance().getEvidenceCountForSnapshotSpecificVersion(snapshotName, version, this.collection_name, timeStart, timeEnd);
    }

    @Override
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotLatestVersion(String snapshotId) throws MongoException, UnknownHostException {
        return this.getEvidenceCountForSnapshotSpecificVersion(snapshotId, this.findHighestVersionForSnapshot(snapshotId));
    }

    @Override
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotSpecificVersion(String snapshotId, String version) throws MongoException, UnknownHostException {

        return CategoryItemDAOMongo.getInstance().getEvidenceCountForSnapshotSpecificVersion(snapshotId, version, this.collection_name);
    }

    @Override
    public List<IMovie> getMovieListForProfileInSnapshotLatestVersion(String profileId, String snapshotId) throws MongoException, UnknownHostException {
        return this.getMovieListForProfileInSnapshotSpecificVersion(profileId, snapshotId, this.findHighestVersionForSnapshot(snapshotId));
    }

    @Override
    public List<IMovie> getMovieListForProfileInSnapshotSpecificVersion(String profileId, String snapshotId, String version) throws MongoException, UnknownHostException {

        BasicDBObject params = new BasicDBObject();
        params.append("snapshot", snapshotId);
        params.append("version", version);
        params.append("accountId", profileId);
        DBCursor dbc = this.findInCollection(this.collection_name, params);

        BasicDBList bdl = new BasicDBList();
        bdl.addAll(dbc.toArray());

        RootAccountJsonMongo racson = new RootAccountJsonMongo();
        racson.setMovieList(bdl);

        return racson.getMovieList();
    }

    @Override
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameMoviesAsProfileForSnapshotSpecificVersion(String snapshotName, String version, String profile_id) throws MongoException, UnknownHostException {
        Map<ICategorizedDomain, List<NamedItem>> map = new HashMap<>();

        List<IMovie> movieList = this.getMovieListForProfileInSnapshotSpecificVersion(profile_id, snapshotName, version);

        List<NamedItem> friends;

        BasicDBObject params;

        for (IMovie m : movieList) {
            params = new BasicDBObject();
            params.append("snapshot", snapshotName);
            params.append("version", version);
            params.append("data.id", m.getId());

            DBCursor dbc = this.findInCollection(this.collection_name, params);
            friends = new LinkedList<>();
            while (dbc.hasNext()) {

                String friend_fb_id = new MovieJsonMongo(dbc.next()).getAccountId();
                if (profile_id.equals(friend_fb_id)) {
                    continue;
                }
                friends.add(new NamedItem(friend_fb_id, ProfileDAOMongo.getInstance().getNameForAccountId(friend_fb_id)));
            }

            if (friends.size() > 0) {
                map.put(m, friends);
            }
        }

        return map;
    }

    @Override
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameMoviesAsProfileForSnapshotLatestVersion(String snapshotName, String profile_id) throws MongoException, UnknownHostException {
        return this.getWhoLikesTheSameMoviesAsProfileForSnapshotSpecificVersion(snapshotName, this.findHighestVersionForSnapshot(snapshotName), profile_id);
    }

    @Override
    public List<String> getDistinctMovieCategories() throws Exception {
        return CategoryItemDAOMongo.getInstance().getDistinctCategories(this.collection_name);
    }

    @Override
    public Map<String, Integer> getExtendedDistinctMovieCategories() throws Exception {
        return CategoryItemDAOMongo.getInstance().getExtendedDistinctCategories(this.collection_name);
    }
}
