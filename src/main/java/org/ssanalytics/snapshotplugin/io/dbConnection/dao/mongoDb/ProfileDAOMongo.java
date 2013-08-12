package org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IProfileDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account.AccountJsonMongo.AccountComponents;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.profile.ProfileJsonMongo;

public class ProfileDAOMongo extends AbstractSuperDAOMongo implements IProfileDAO {

    private final String collection_name = "profile";

    private ProfileDAOMongo() throws UnknownHostException, MongoException {
        super();
    }
    private static ProfileDAOMongo instance = null;

    public static ProfileDAOMongo getInstance() throws UnknownHostException, MongoException {
        if (instance == null) {
            instance = new ProfileDAOMongo();
        }
        return instance;
    }

    public List<IProfile> getProfileList() throws UnknownHostException, MongoException {

        DB db = this.openDatabase();
        DBCollection profileCollection = db.getCollection(this.collection_name);
        DBCursor dbCursor = profileCollection.find();

        List<IProfile> returnList = new ArrayList<IProfile>();

        while (dbCursor.hasNext()) {
            returnList.add(new ProfileJsonMongo((DBObject) dbCursor.next()));
        }

        return returnList;
    }

    //TODO: enhance this method by shifting logic to the db.
    @Override
    public List<IProfile> getProfileListForSnapshotLatestVersion(String snapshotInfoName) throws Exception {

        String highestVersion = this.findHighestVersionForSnapshot(snapshotInfoName);
        return this.getProfileListForSnapshotSpecificVersion(snapshotInfoName, highestVersion);


    }

    @Override
    public List<IProfile> getProfileListForSnapshotSpecificVersion(String snapshotInfoName, String version) throws Exception {

        List<IProfile> source = this.getProfileList();

        List<IProfile> target = new LinkedList<IProfile>();

        for (IProfile ip : source) {
            ProfileJsonMongo mongoProfile = (ProfileJsonMongo) ip;

            if ((mongoProfile != null) && (mongoProfile.getSnapshotId() != null)) {
                if (mongoProfile.getSnapshotId().equals(snapshotInfoName)) {
                    if (mongoProfile.getSnapshotVersion().trim().equals(version.trim())) {
                        target.add(ip);
                    }
                }
            }
        }

        return target;
    }

    @Override
    public IProfile getProfileForAccountIdInSnapshotLatestVersion(String accountId, String snapshotInfoName) throws MongoException, UnknownHostException {
        return this.getProfileForAccountIdInSnapshotSpecificVersion(accountId, snapshotInfoName, this.findHighestVersionForSnapshot(snapshotInfoName));
    }

    @Override
    public IProfile getProfileForAccountIdInSnapshotSpecificVersion(String accountId, String snapshotInfoName, String version) throws MongoException, UnknownHostException {
        BasicDBObject params = new BasicDBObject().append("snapshot", snapshotInfoName).append("version", version).append("accountId", accountId);
        DBCursor dbc = this.findInCollection("profile", params);

        if (dbc != null && dbc.hasNext()) {
            return new ProfileJsonMongo(dbc.next());
        }

        return null;
    }

    @Override
    public String getNameForAccountId(String accountId) throws MongoException, UnknownHostException {
        BasicDBObject params = new BasicDBObject();
        params.append("accountId", accountId);

        DBObject dbo = this.findOne(this.collection_name, params);

        if (dbo != null) {
            return new ProfileJsonMongo(dbo).getName();
        } else {
            return "";
        }

    }
}
