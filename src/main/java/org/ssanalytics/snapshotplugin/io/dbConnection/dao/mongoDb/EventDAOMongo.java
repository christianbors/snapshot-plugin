package org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.event.IEvent;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account.AccountJsonMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IEventDAO;

public class EventDAOMongo extends AbstractSuperDAOMongo implements IEventDAO {

    private EventDAOMongo() throws UnknownHostException, MongoException {
        super();
    }
    private final String collection_name = "events";
    private static EventDAOMongo instance = null;

    public static EventDAOMongo getInstance() throws UnknownHostException, MongoException {
        if (instance == null) {
            instance = new EventDAOMongo();
        }
        return instance;
    }

    @Override
    public List<IEvent> getEventListForSnapshotLatestVersion(String snapshotInfoName) throws Exception {

        String highestVersion = this.findHighestVersionForSnapshot(snapshotInfoName);
        return this.getEventListForSnapshotSpecificVersion(snapshotInfoName, highestVersion);
    }

    @Override
    public List<IEvent> getEventListForSnapshotSpecificVersion(String snapshotInfoName, String version) throws UnknownHostException, MongoException {
        com.mongodb.DB db = this.openDatabase();

        DBCollection eventCollection = db.getCollection(this.collection_name);

        DBCursor crs = eventCollection.find(new BasicDBObject().append("snapshot", snapshotInfoName).append("version", version));

        AccountJsonMongo amon = new AccountJsonMongo();

        BasicDBList toAdd = new BasicDBList();
        toAdd.addAll(crs.toArray());

        amon.setEventList(toAdd);
        return amon.getEventList();
    }

    @Override
    public List<IEvent> getEventListForProfileAndSnapshotSpecificVersion(String snapshotInfoname, String profile_fb_id, String version) throws Exception {

        BasicDBObject params = new BasicDBObject();
        params.append("snapshot", snapshotInfoname);
        params.append("version", version);
        params.append("accountId", profile_fb_id);
        DBCursor crs = this.findInCollection("events", params);

        AccountJsonMongo amon = new AccountJsonMongo();

        BasicDBList toAdd = new BasicDBList();
        toAdd.addAll(crs.toArray());

        amon.setEventList(toAdd);
        return amon.getEventList();
    }

    @Override
    public List<IEvent> getEventListForProfileAndSnapshotLatestVersion(String snapshotInfoname, String profile_fb_id) throws Exception {
        String highestVersion = this.findHighestVersionForSnapshot(snapshotInfoname);
        return this.getEventListForProfileAndSnapshotSpecificVersion(snapshotInfoname, profile_fb_id, highestVersion);
    }
}
