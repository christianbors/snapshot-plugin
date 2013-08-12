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
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IMusicDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.music.IMusic;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ICategorizedDomain;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account.RootAccountJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.music.MusicJsonMongo;

/**
 *
 * @author chw
 */
public class MusicDAOMongo extends AbstractSuperDAOMongo implements IMusicDAO {

    private final String collection_name = "music";

    private MusicDAOMongo() throws UnknownHostException, MongoException {
        super();
    }
    private static MusicDAOMongo instance = null;

    public static MusicDAOMongo getInstance() throws UnknownHostException, MongoException {
        if (instance == null) {
            instance = new MusicDAOMongo();
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
    public List<IMusic> getMusicListForProfileInSnapshotLatestVersion(String profileId, String snapshotId) throws Exception {
        return this.getMusicListForProfileInSnapshotSpecificVersion(profileId, snapshotId, this.findHighestVersionForSnapshot(snapshotId));
    }

    @Override
    public List<IMusic> getMusicListForProfileInSnapshotSpecificVersion(String profileId, String snapshotId, String version) throws Exception {

        BasicDBObject params = new BasicDBObject();
        params.append("snapshot", snapshotId);
        params.append("version", version);
        params.append("accountId", profileId);
        DBCursor dbc = this.findInCollection(this.collection_name, params);

        BasicDBList bdl = new BasicDBList();
        bdl.addAll(dbc.toArray());

        RootAccountJsonMongo racson = new RootAccountJsonMongo();
        racson.setMusicList(bdl);

        return racson.getMusicList();
    }

    @Override
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameMusicAsProfileForSnapshotSpecificVersion(String snapshotName, String version, String profile_id) throws Exception {
        Map<ICategorizedDomain, List<NamedItem>> map = new HashMap<>();

        List<IMusic> musicList = this.getMusicListForProfileInSnapshotSpecificVersion(profile_id, snapshotName, version);

        List<NamedItem> friends;

        BasicDBObject params;

        for (IMusic m : musicList) {
            params = new BasicDBObject();
            params.append("snapshot", snapshotName);
            params.append("version", version);
            params.append("data.id", m.getId());

            DBCursor dbc = this.findInCollection(this.collection_name, params);
            friends = new LinkedList<>();
            while (dbc.hasNext()) {

                String friend_fb_id = new MusicJsonMongo(dbc.next()).getAccountId();
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
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameMusicAsProfileForSnapshotLatestVersion(String snapshotName, String profile_id) throws Exception {
        return this.getWhoLikesTheSameMusicAsProfileForSnapshotSpecificVersion(snapshotName, this.findHighestVersionForSnapshot(snapshotName), profile_id);
    }

    @Override
    public List<String> getDistinctMusicCategories() throws Exception {
        return CategoryItemDAOMongo.getInstance().getDistinctCategories(this.collection_name);
    }

    @Override
    public Map<String, Integer> getExtendedDistinctMusicCategories() throws Exception {
        return CategoryItemDAOMongo.getInstance().getExtendedDistinctCategories(this.collection_name);
    }
}
