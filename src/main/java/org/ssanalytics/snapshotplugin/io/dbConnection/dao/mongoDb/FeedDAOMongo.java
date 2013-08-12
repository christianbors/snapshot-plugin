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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IFeedDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.feed.IFeed;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account.RootAccountJsonMongo;

/**
 *
 * @author chw
 */
public class FeedDAOMongo extends AbstractSuperDAOMongo implements IFeedDAO {

    
    private final String collection_name = "feed";
    
    protected FeedDAOMongo() throws UnknownHostException, MongoException {
        super();
        // TODO Auto-generated constructor stub
    }
    private static FeedDAOMongo instance = null;

    public static FeedDAOMongo getInstance() throws UnknownHostException, MongoException {
        if (instance == null) {
            instance = new FeedDAOMongo();
        }
        return instance;
    }

    @Override
    public Map<NamedItem, Integer> getWhoPostedOnWallForRootAccountOfSnapshotLatestVersion(String snapshotName) throws MongoException, UnknownHostException {
        String highestVersion = this.findHighestVersionForSnapshot(snapshotName);
        return this.getWhoPostedOnWallForRootAccountOfSnapshotSpecificVersion(snapshotName, highestVersion);
    }

    @Override
    public Map<NamedItem, Integer> getWhoPostedOnWallForRootAccountOfSnapshotSpecificVersion(String snapshotName, String version) throws MongoException, UnknownHostException {


        Map<NamedItem, Integer> returnMap = new HashMap<>();
        NamedItem item;

        for (IFeed feed : this.getAllFeedsForRootAccountOfSnapshotSpecificVersion(snapshotName, version)) {

            ISharedFrom from = feed.getFrom();
            String id = from.getId();
            String name = from.getName();

            item = new NamedItem(feed.getFrom().getId(), feed.getFrom().getName());
            if (returnMap.containsKey(item)) {
                Integer i = returnMap.get(item);
                i++;
                returnMap.remove(item);
                returnMap.put(item, i);
            } else {
                returnMap.put(item, 1);
            }
        }

        return returnMap;
    }

    @Override
    public List<IFeed> getAllFeedsForRootAccountOfSnapshotLatestVersion(String snapshotName) throws Exception {
        return this.getAllFeedsForRootAccountOfSnapshotSpecificVersion(snapshotName, this.findHighestVersionForSnapshot(snapshotName));
    }

    @Override
    public List<IFeed> getAllFeedsForRootAccountOfSnapshotSpecificVersion(String snapshotName, String version) throws MongoException, UnknownHostException {
        
        BasicDBObject params = new BasicDBObject("snapshot", snapshotName).append("version", version).append("accountId", SnapshotInfoDAOMongo.getInstance().getRootAccountFbId(snapshotName, version));
        
        DBCursor dbc = this.findInCollection(this.collection_name, params);
        
        BasicDBList toAdd = new BasicDBList();
        toAdd.addAll(dbc.toArray());

        RootAccountJsonMongo racson = new RootAccountJsonMongo();
        racson.setFeedList(toAdd);
        
        return racson.getFeedList();
        
    }

    @Override
    public List<ISharedComments> getAllCommentsInSnapshotLatestVersion(String snapshot) throws Exception {
        return this.getAllCommentsInSnapshotSpecificVersion(snapshot, this.findHighestVersionForSnapshot(snapshot));
    }

    @Override
    public List<ISharedComments> getAllCommentsInSnapshotSpecificVersion(String snapshot, String version) throws Exception {
        List<IFeed> feedList = this.getAllFeedsForRootAccountOfSnapshotSpecificVersion(snapshot, version);
        
        List<ISharedComments> cList = new ArrayList<>(feedList.size());
        
        for(IFeed feed : feedList){
            ISharedComments c = feed.getComments();
            if(c != null)
                cList.add(c);
        }
        
        return cList;
    }
}
