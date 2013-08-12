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
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IOutboxDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sharedHelpers.WordCountHelper;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.outbox.IOutbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account.RootAccountJsonMongo;

/**
 *
 * @author chw
 */
public class OutboxDAOMongo extends AbstractSuperDAOMongo implements IOutboxDAO {

    private final String collection_name = "outbox";

    private OutboxDAOMongo() throws UnknownHostException, MongoException {
        super();
    }
    private static OutboxDAOMongo instance = null;

    public static OutboxDAOMongo getInstance() throws UnknownHostException, MongoException {
        if (instance == null) {
            instance = new OutboxDAOMongo();
        }
        return instance;
    }

    @Override
    public List<IOutbox> getOutboxListForRootAccountOfSnapshotSpecificVersion(String snapshotInfo, String version) throws UnknownHostException {

        DBCursor dbCursor = this.findInCollection(this.collection_name, new BasicDBObject().append("snapshot", snapshotInfo).append("version", version));

        RootAccountJsonMongo racson = new RootAccountJsonMongo();

        BasicDBList toAdd = new BasicDBList();
        toAdd.addAll(dbCursor.toArray());
        racson.setOutboxList(toAdd);

        return racson.getOutboxList();
    }

    @Override
    public List<IOutbox> getOutboxListForRootAccountOfSnapshotLatestVersion(String snapshotInfo) throws UnknownHostException {
        return this.getOutboxListForRootAccountOfSnapshotSpecificVersion(snapshotInfo, this.findHighestVersionForSnapshot(snapshotInfo));
    }

    @Override
    public Map<String, Integer> getWordCountForOutboxOfRootAccountSnapshotLatestVersion(String snapshotInfo) throws MongoException, UnknownHostException {
        return this.getWordCountForOutboxOfRootAccountSnapshotSpecificVersion(snapshotInfo, this.findHighestVersionForSnapshot(snapshotInfo));
    }

    @Override
    public Map<String, Integer> getWordCountForOutboxOfRootAccountSnapshotSpecificVersion(String snapshotInfo, String version) throws MongoException, UnknownHostException {

        BasicDBObject params = new BasicDBObject();
        params.append("version", version);
        params.append("snapshot", snapshotInfo);

        DBCursor dbc = this.findInCollection(this.collection_name, params);

        Map<String, Integer> retMap = new HashMap<>();

        RootAccountJsonMongo racson = new RootAccountJsonMongo();

        BasicDBList bdl = new BasicDBList();
        bdl.addAll(dbc.toArray());

        racson.setOutboxList(bdl);

        for (IOutbox o : racson.getOutboxList()) {
            retMap = WordCountHelper.getInstance().countWordsInString(o.getMessage(), retMap);
        }

        return retMap;
    }
}
