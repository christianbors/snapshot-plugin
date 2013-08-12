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
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IStatusDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.status.IStatus;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account.RootAccountJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.status.StatusJsonMongo;

/**
 *
 * @author chw
 */
public class StatusDAOMongo extends AbstractSuperDAOMongo implements IStatusDAO {

    private final String collection_name = "statuses";

    private StatusDAOMongo() throws UnknownHostException, MongoException {
        super();
    }
    private static StatusDAOMongo instance = null;

    public static StatusDAOMongo getInstance() throws UnknownHostException, MongoException {
        if (instance == null) {
            instance = new StatusDAOMongo();
        }
        return instance;
    }

    @Override
    public List<ISharedPlace> getLocationsForProfileSnapshotLatestVersion(String profile_id, String snapshot) throws UnknownHostException, MongoException {
        return this.getLocationsForProfileSnapshotSpecificVersion(profile_id, snapshot, this.findHighestVersionForSnapshot(snapshot));
    }

    @Override
    public List<ISharedPlace> getLocationsForProfileSnapshotSpecificVersion(String profile_id, String snapshot, String version) throws UnknownHostException, MongoException {

        BasicDBObject params = new BasicDBObject("snapshot", snapshot).append("version", version).append("accountId", profile_id);

        DBCursor dbc = this.findInCollection(this.collection_name, params);

        List<ISharedPlace> list = new LinkedList<>();

        while (dbc.hasNext()) {
            StatusJsonMongo status = new StatusJsonMongo(dbc.next());

            ISharedPlace place = status.getPlace();
            if ((place != null) && (place.getName() != null)) {
                list.add(place);
            }

        }

        return list;
    }

    @Override
    public List<IStatus> getAllSatusInSnapshotLatestVersion(String snapshot) throws UnknownHostException, MongoException {
        return this.getAllSatusInSnapshotSpecificVersion(snapshot, this.findHighestVersionForSnapshot(snapshot));
    }

    @Override
    public List<IStatus> getAllSatusInSnapshotSpecificVersion(String snapshot, String version) throws UnknownHostException, MongoException {
        BasicDBObject params = new BasicDBObject().append("snapshot", snapshot).append("version", version);
        
        DBCursor dbc = this.findInCollection(this.collection_name, params);
        
        BasicDBList bdl = new BasicDBList();
        bdl.addAll(dbc.toArray());
        
        RootAccountJsonMongo racson = new RootAccountJsonMongo();
        racson.setStatusList(bdl);
        return racson.getStatusList();
    }

    @Override
    public List<ISharedComments> getAllCommentsInSnapshotLatestVersion(String snapshot) throws UnknownHostException, MongoException {
        return this.getAllCommentsInSnapshotSpecificVersion(snapshot, this.findHighestVersionForSnapshot(snapshot));
    }

    @Override
    public List<ISharedComments> getAllCommentsInSnapshotSpecificVersion(String snapshot, String version) throws UnknownHostException, MongoException {
        
        List<IStatus> statusList = this.getAllSatusInSnapshotSpecificVersion(snapshot, version);
        
        List<ISharedComments> cList = new ArrayList<>(statusList.size());
        
        for(IStatus status : statusList){
            ISharedComments c = status.getComments();
            if(c != null)
                cList.add(c);
        }
        
        return cList;
    }
}
