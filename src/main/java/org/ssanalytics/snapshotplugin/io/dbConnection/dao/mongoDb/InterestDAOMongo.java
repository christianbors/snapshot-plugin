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
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.interest.IInterest;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ICategorizedDomain;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account.RootAccountJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.movie.MovieJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IInterestDAO;

/**
 *
 * @author chw
 */
public class InterestDAOMongo extends AbstractSuperDAOMongo implements IInterestDAO {

    private final String collection_name = "interests";

    private InterestDAOMongo() throws UnknownHostException, MongoException {
        super();
    }
    private static InterestDAOMongo instance = null;

    public static InterestDAOMongo getInstance() throws UnknownHostException, MongoException {
        if (instance == null) {
            instance = new InterestDAOMongo();
        }
        return instance;
    }

    @Override
    public List<String> getDistinctInterestCategories() throws Exception {
        return CategoryItemDAOMongo.getInstance().getDistinctCategories(this.collection_name);
    }

    @Override
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotLatestVersion(String snapshotName) throws MongoException, UnknownHostException {
        return this.getEvidenceCountForSnapshotSpecificVersion(snapshotName, this.findHighestVersionForSnapshot(snapshotName));
    }

    @Override
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotSpecificVersion(String snapshotName, String version) throws MongoException, UnknownHostException {
        return CategoryItemDAOMongo.getInstance().getEvidenceCountForSnapshotSpecificVersion(snapshotName, version, this.collection_name);
    }

    @Override
    public List<IInterest> getInterestListForProfileInSnapshotLatestVersion(String profileId, String snapshotId) throws MongoException, UnknownHostException {
        return this.getInterestListForProfileInSnapshotSpecificVersion(profileId, snapshotId, this.findHighestVersionForSnapshot(snapshotId));
    }

    @Override
    public List<IInterest> getInterestListForProfileInSnapshotSpecificVersion(String profileId, String snapshotId, String version) throws MongoException, UnknownHostException {

        BasicDBObject params = new BasicDBObject();
        params.append("snapshot", snapshotId);
        params.append("version", version);
        params.append("accountId", profileId);
        DBCursor dbc = this.findInCollection(this.collection_name, params);

        BasicDBList bdl = new BasicDBList();
        bdl.addAll(dbc.toArray());

        RootAccountJsonMongo racson = new RootAccountJsonMongo();
        racson.setInterestList(bdl);

        return racson.getInterests();
    }

    @Override
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameInterestsAsProfileForSnapshotSpecificVersion(String snapshotName, String version, String profile_id) throws MongoException, UnknownHostException {
        Map<ICategorizedDomain, List<NamedItem>> map = new HashMap<>();

        List<IInterest> interestList = this.getInterestListForProfileInSnapshotSpecificVersion(profile_id, snapshotName, version);

        List<NamedItem> friends;

        BasicDBObject params;

        for (IInterest i : interestList) {
            params = new BasicDBObject();
            params.append("snapshot", snapshotName);
            params.append("version", version);
            params.append("data.id", i.getId());

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
                map.put(i, friends);
            }
        }

        return map;
    }

    @Override
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameInterestsAsProfileForSnapshotLatestVersion(String snapshotName, String profile_id) throws MongoException, UnknownHostException {
        return this.getWhoLikesTheSameInterestsAsProfileForSnapshotSpecificVersion(snapshotName, this.findHighestVersionForSnapshot(snapshotName), profile_id);
    }

    @Override
    public Map<String, Integer> getExtendedDistinctInterestCategories() throws Exception {
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
