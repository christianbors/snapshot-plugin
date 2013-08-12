package org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account.RootAccountJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account.RootAccountJsonMongo.RootAccountComponents;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IFriendDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.filter.FriendFilterData;

public class FriendDAOMongo extends AbstractSuperDAOMongo implements IFriendDAO {

    
    private final String collection_name = "friends";
    
    protected FriendDAOMongo() throws UnknownHostException, MongoException {
        super();
        // TODO Auto-generated constructor stub
    }
    private static FriendDAOMongo instance = null;

    public static FriendDAOMongo getInstance() throws UnknownHostException, MongoException {
        if (instance == null) {
            instance = new FriendDAOMongo();
        }
        return instance;
    }

    @Override
    public List<IFriend> getFriendListSpecificVersion(String snapshotName, String version) throws UnknownHostException, MongoException {

        DB db = this.openDatabase();
        DBCollection friendsCollection = db.getCollection(this.collection_name);
        DBCursor dbCursor = friendsCollection.find(new BasicDBObject().append("snapshot", snapshotName).append("version", version));
        RootAccountJsonMongo racson = new RootAccountJsonMongo();
        BasicDBList toAdd = new BasicDBList();
        toAdd.addAll(dbCursor.toArray());
        racson.setFriendList(toAdd);

        return racson.getFriendList();
    }

    @Override
    public List<IFriend> getFriendList(String snapshotName, String version, FriendFilterData filter) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IFriend> getFriendListLatestVersion(String snapshotName) throws Exception {
        return this.getFriendListSpecificVersion(snapshotName, this.findHighestVersionForSnapshot(snapshotName));
    }

    @Override
    public List<String> getFriendIdListSpecificVersion(String snapshotName, String version) throws MongoException, UnknownHostException {
        List<String> retList = new LinkedList<>();

        for (IFriend f : this.getFriendListSpecificVersion(snapshotName, version)) {
            retList.add(f.getId());
        }

        return retList;
    }

    @Override
    public List<String> getFriendIdListLatestVersion(String snapshotName) throws MongoException, UnknownHostException {
        return this.getFriendIdListSpecificVersion(snapshotName, this.findHighestVersionForSnapshot(snapshotName));
    }
}
