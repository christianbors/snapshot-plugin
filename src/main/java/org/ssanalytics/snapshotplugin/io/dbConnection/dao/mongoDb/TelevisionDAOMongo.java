/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.ITelevisionDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ICategorizedDomain;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.television.ITelevision;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account.RootAccountJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.television.TelevisionJsonMongo;

/**
 *
 * @author chw
 */
public class TelevisionDAOMongo extends AbstractSuperDAOMongo implements ITelevisionDAO {

    private final String collection_name = "television";

    private TelevisionDAOMongo() throws UnknownHostException, MongoException {
        super();
    }
    private static TelevisionDAOMongo instance = null;

    public static TelevisionDAOMongo getInstance() throws UnknownHostException, MongoException {
        if (instance == null) {
            instance = new TelevisionDAOMongo();
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
    public Map<CategoryItem, Integer> getTelevisionEvidenceCountForSnapshotLatestVersion(String snapshotName) throws Exception {
        return this.getTelevisionEvidenceCountForSnapshotSpecificVersion(snapshotName, this.findHighestVersionForSnapshot(snapshotName));
    }

    @Override
    public Map<CategoryItem, Integer> getTelevisionEvidenceCountForSnapshotSpecificVersion(String snapshotId, String version) throws MongoException, UnknownHostException {

        return CategoryItemDAOMongo.getInstance().getEvidenceCountForSnapshotSpecificVersion(snapshotId, version, this.collection_name);
    }

    @Override
    public List<ITelevision> getTelevisionListForProfileInSnapshotLatestVersion(String snapshotName, String accountId) throws MongoException, UnknownHostException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ITelevision> getTelevisionListForProfileInSnapshotSpecificVersion(String snapshotName, String version, String accountId) throws MongoException, UnknownHostException {

        BasicDBObject params = new BasicDBObject();
        params.append("snapshot", snapshotName);
        params.append("version", version);
        params.append("accountId", accountId);
        DBCursor dbc = this.findInCollection(this.collection_name, params);

        BasicDBList bdl = new BasicDBList();
        bdl.addAll(dbc.toArray());

        RootAccountJsonMongo racson = new RootAccountJsonMongo();
        racson.setTelevisionList(bdl);

        return racson.getTelevisionList();
    }

    @Override
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameTelevisionAsProfileForSnapshotSpecificVersion(String snapshotName, String version, String profile_id) throws MongoException, UnknownHostException {


        Map<ICategorizedDomain, List<NamedItem>> map = new HashMap<>();

        List<ITelevision> televisionList = this.getTelevisionListForProfileInSnapshotSpecificVersion(snapshotName, version, profile_id);

        List<NamedItem> friends;

        BasicDBObject params;

        for (ITelevision tv : televisionList) {
            params = new BasicDBObject();
            params.append("snapshot", snapshotName);
            params.append("version", version);
            params.append("data.id", tv.getId());

            DBCursor dbc = this.findInCollection(this.collection_name, params);
            friends = new LinkedList<>();
            while (dbc.hasNext()) {

                String friend_fb_id = new TelevisionJsonMongo(dbc.next()).getAccountId();
                if (profile_id.equals(friend_fb_id)) {
                    continue;
                }
                friends.add(new NamedItem(friend_fb_id, ProfileDAOMongo.getInstance().getNameForAccountId(friend_fb_id)));
            }

            if (friends.size() > 0) {
                map.put(tv, friends);
            }
        }

        return map;
    }

    @Override
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameTelevisionAsProfileForSnapshotLatestVersion(String snapshotName, String profile_id) throws MongoException, UnknownHostException {
        return this.getWhoLikesTheSameTelevisionAsProfileForSnapshotSpecificVersion(snapshotName, this.findHighestVersionForSnapshot(snapshotName), profile_id);
    }

    @Override
    public List<String> getDistinctTelevisionCategories() throws Exception {

        return CategoryItemDAOMongo.getInstance().getDistinctCategories(this.collection_name);
    }

    @Override
    public Map<String, Integer> getExtendedDistinctTelevisionCategories() throws Exception {
        return CategoryItemDAOMongo.getInstance().getExtendedDistinctCategories(this.collection_name);
    }
}
