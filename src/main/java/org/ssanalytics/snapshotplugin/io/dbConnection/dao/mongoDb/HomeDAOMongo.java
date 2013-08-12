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
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IHomeDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.feed.IFeed;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.home.IHome;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.link.ILink;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account.RootAccountJsonMongo;

/**
 *
 * @author chw
 */
public class HomeDAOMongo extends AbstractSuperDAOMongo implements IHomeDAO {

    protected HomeDAOMongo() throws UnknownHostException, MongoException {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private String collection_name = "home";
    
    private static HomeDAOMongo instance = null;

    public static HomeDAOMongo getInstance() throws UnknownHostException, MongoException {
        if (instance == null) {
            instance = new HomeDAOMongo();
        }
        return instance;
    }

    @Override
    public List<IHome> getHomeListForRootAccountOfSnapshotLatestVersion(String snapshotName) throws Exception {
        return this.getHomeListForRootAccountOfSnapshotSpecificVersion(snapshotName, this.findHighestVersionForSnapshot(snapshotName));
    }

    @Override
    public List<IHome> getHomeListForRootAccountOfSnapshotSpecificVersion(String snapshotName, String version) throws MongoException, UnknownHostException {
        
        BasicDBObject params = new BasicDBObject("snapshot", snapshotName).append("version", version).append("accountId", SnapshotInfoDAOMongo.getInstance().getRootAccountFbId(snapshotName, version));
        
        DBCursor dbc = this.findInCollection(this.collection_name, params);
        
        BasicDBList toAdd = new BasicDBList();
        toAdd.addAll(dbc.toArray());

        RootAccountJsonMongo racson = new RootAccountJsonMongo();
        racson.setHomeList(toAdd);
        
        return racson.getHomeList();
        
    }

    @Override
    public List<ISharedComments> getAllCommentsInSnapshotLatestVersion(String snapshot) throws Exception {
        return this.getAllCommentsInSnapshotSpecificVersion(snapshot, this.findHighestVersionForSnapshot(snapshot));
    }

    @Override
    public List<ISharedComments> getAllCommentsInSnapshotSpecificVersion(String snapshot, String version) throws Exception {
        List<IHome> homeList = this.getHomeListForRootAccountOfSnapshotSpecificVersion(snapshot, version);
        
        List<ISharedComments> cList = new ArrayList<>(homeList.size());
        
        for(IHome home : homeList){
            ISharedComments c = home.getComments();
            if(c != null)
                cList.add(c);
        }
        
        return cList;
    }
}
