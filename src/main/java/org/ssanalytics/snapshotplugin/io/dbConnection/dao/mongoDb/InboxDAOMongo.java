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
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IInboxDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.inbox.IInbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account.RootAccountJsonMongo;

/**
 *
 * @author chw
 */
public class InboxDAOMongo extends AbstractSuperDAOMongo implements IInboxDAO {

    
    private final String collection_name = "inbox";
    
    private InboxDAOMongo() throws UnknownHostException, MongoException {
        super();
    }
    private static InboxDAOMongo instance = null;

    public static InboxDAOMongo getInstance() throws UnknownHostException, MongoException {
        if (instance == null) {
            instance = new InboxDAOMongo();
        }
        return instance;
    }

    @Override
    public List<IInbox> getInboxListForRootAccountOfSnapshotSpecificVersion(String snapshotInfo, String version) throws UnknownHostException {

        DBCursor dbCursor = this.findInCollection(this.collection_name, new BasicDBObject().append("snapshot", snapshotInfo).append("version", version));

        RootAccountJsonMongo racson = new RootAccountJsonMongo();

        BasicDBList toAdd = new BasicDBList();
        toAdd.addAll(dbCursor.toArray());
        racson.setInboxList(toAdd);

        return racson.getInboxList();
    }

    @Override
    public List<IInbox> getInboxListForRootAccountOfSnapshotLatestVersion(String snapshotInfo) throws UnknownHostException {
        return this.getInboxListForRootAccountOfSnapshotSpecificVersion(snapshotInfo, this.findHighestVersionForSnapshot(snapshotInfo));
    }
}
