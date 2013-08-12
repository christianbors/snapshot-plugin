package org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;

public abstract class AbstractSuperDAOMongo {

    private com.mongodb.DB con = null;
    
    protected com.mongodb.DB openDatabase() throws UnknownHostException, MongoException {
        
        if (con == null) {
            Mongo m = new Mongo(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getHost(), Integer.parseInt(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getPort()));
            con = m.getDB(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbName());
        }

        return con;
    }

    protected DBCollection getCollection(String collectionName) throws UnknownHostException {
        com.mongodb.DB db = this.openDatabase();
        return db.getCollection(collectionName);
    }

    protected DBCursor findInCollection(String collectionName, DBObject params) throws MongoException, UnknownHostException {
        return this.getCollection(collectionName).find(params);
    }

    protected DBObject findOne(String collectionNames, DBObject params) throws MongoException, UnknownHostException {
        return this.getCollection(collectionNames).findOne(params);
    }

    protected String findHighestVersionForSnapshot(String snapshotName) throws UnknownHostException, MongoException {
        com.mongodb.DB db = this.openDatabase();

        DBCollection snapshotInfoCollection = db.getCollection("snapshotInfo");
        DBCursor snapshotInfoCursor = snapshotInfoCollection.find(new BasicDBObject().append("value", snapshotName));
        int highestVersion = 0;
        if (snapshotInfoCursor.hasNext()) {
            for (DBObject snapshot : snapshotInfoCursor) {
                if (snapshot != null) {
                    String version = ((DBObject) snapshot.get(snapshot.get(
                            "value").toString())).get("version").toString();
                    if (version != null) {
                        int tempVersion = Integer.parseInt(version);
                        if (tempVersion > highestVersion) {
                            highestVersion = tempVersion;
                        }
                    }
                }
            }
        }

        return String.valueOf(highestVersion);
    }
}
