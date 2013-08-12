package org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import java.util.Map;
import java.net.UnknownHostException;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IRootAccountDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account.RootAccountJsonMongo;

public class RootAccountDAOMongo extends AbstractSuperDAOMongo implements IRootAccountDAO {

    private RootAccountDAOMongo() throws UnknownHostException, MongoException {
        super();
    }
    private static RootAccountDAOMongo instance = null;

    public static RootAccountDAOMongo getInstance() throws UnknownHostException, MongoException {
        if (instance == null) {
            instance = new RootAccountDAOMongo();
        }
        return instance;
    }

    @Override
    public Map<String, List<String>> getFriendIsFriend(String snapshotName, String version) throws UnknownHostException, MongoException {

        DB db = this.openDatabase();
        DBCollection fifCollection = db.getCollection("friendisfriend");
        DBObject dbo = fifCollection.findOne(new BasicDBObject().append("snapshot", snapshotName).append("version", version));

        RootAccountJsonMongo racson = new RootAccountJsonMongo();
        racson.setFriendIsFriend(dbo);
        return racson.getFriendIsFriend();
    }

    @Override
    public List<IFriend> getFriendList(String snapshotName, String version) throws UnknownHostException, MongoException {
        return FriendDAOMongo.getInstance().getFriendListSpecificVersion(snapshotName, version);

    }
}
