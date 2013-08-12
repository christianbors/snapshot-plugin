package org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.ILikeDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.book.IBook;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.likes.ILike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ICategorizedDomain;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account.RootAccountJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.movie.MovieJsonMongo;

public class LikeDAOMongo extends AbstractSuperDAOMongo implements ILikeDAO {

    private final String collection_name = "likes";
    
    private static LikeDAOMongo instance = null;

    protected LikeDAOMongo() throws SQLException {
        super();
    }

    public static LikeDAOMongo getInstance() throws SQLException {
        if (instance == null) {
            instance = new LikeDAOMongo();
        }
        return instance;
    }

    @Override
    public List<ILike> getLikeListForProfileInSnapshotLatestVersion(String profileId, String snapshotId) throws MongoException, UnknownHostException {
        return this.getLikeListForProfileInSnapshotSpecificVersion(profileId, snapshotId, this.findHighestVersionForSnapshot(snapshotId));
    }

    @Override
    public List<ILike> getLikeListForProfileInSnapshotSpecificVersion(String profileId, String snapshotId, String version) throws MongoException, UnknownHostException {

        BasicDBObject params = new BasicDBObject();
        params.append("snapshot", snapshotId);
        params.append("version", version);
        params.append("accountId", profileId);
        DBCursor dbc = this.findInCollection(this.collection_name, params);

        BasicDBList bdl = new BasicDBList();
        bdl.addAll(dbc.toArray());

        RootAccountJsonMongo racson = new RootAccountJsonMongo();
        racson.setLikeList(bdl);

        return racson.getLikeList();
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
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameAsProfileForSnapshotSpecificVersion(String snapshotName, String version, String profile_id) throws MongoException, UnknownHostException {
        Map<ICategorizedDomain, List<NamedItem>> map = new HashMap<>();

        List<ILike> likeList = this.getLikeListForProfileInSnapshotSpecificVersion(profile_id, snapshotName, version);

        List<NamedItem> friends;

        BasicDBObject params;

        for (ILike l : likeList) {
            params = new BasicDBObject();
            params.append("snapshot", snapshotName);
            params.append("version", version);
            params.append("data.id", l.getId());

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
                map.put(l, friends);
            }
        }

        return map;
    }

    @Override
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameAsProfileForSnapshotLatestVersion(String snapshotName, String profile_id) throws MongoException, UnknownHostException {
        return this.getWhoLikesTheSameAsProfileForSnapshotSpecificVersion(snapshotName, this.findHighestVersionForSnapshot(snapshotName), profile_id);
    }

    @Override
    public List<String> getDistinctLikeCategories() throws MongoException, UnknownHostException {
        return CategoryItemDAOMongo.getInstance().getDistinctCategories(this.collection_name);
    }

    @Override
    public Map<String, Integer> getExtendedDistinctLikeCategories() throws MongoException, UnknownHostException {
        return CategoryItemDAOMongo.getInstance().getExtendedDistinctCategories(this.collection_name);
    }
    
    @Override
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotLatestVersion(String snapshotName, long timeStart, long timeEnd) throws UnknownHostException, MongoException {
        return this.getEvidenceCountForSnapshotSpecificVersion(snapshotName, this.findHighestVersionForSnapshot(snapshotName), timeStart, timeEnd);
    }

    @Override
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotSpecificVersion(String snapshotName, String version, long timeStart, long timeEnd) throws UnknownHostException, MongoException  {
        return CategoryItemDAOMongo.getInstance().getEvidenceCountForSnapshotSpecificVersion(snapshotName, version, this.collection_name, timeStart, timeEnd);
    }
}
