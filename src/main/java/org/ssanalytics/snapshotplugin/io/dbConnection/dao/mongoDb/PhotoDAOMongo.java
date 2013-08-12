/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IPhotoDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhoto;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.post.IPost;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account.AccountJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.post.PostJsonMongo;

/**
 *
 * @author chw
 */
public class PhotoDAOMongo extends AbstractSuperDAOMongo implements IPhotoDAO {

    private final String collection_name = "photos";

    private PhotoDAOMongo() {
        super();
    }
    private static PhotoDAOMongo instance = null;

    public static PhotoDAOMongo getInstance() {
        if (instance == null) {
            instance = new PhotoDAOMongo();
        }
        return instance;
    }

    @Override
    public List<IPhoto> getPhotoListForProfileInSnapshotSpecificVersion(String snapshotName, String version, String profile_id) throws MongoException, UnknownHostException {

        BasicDBObject params = new BasicDBObject();
        params.append("snapshot", snapshotName).append("version", version).append("accountId", profile_id);

        return this.getPhotoList(params);

    }

    @Override
    public List<IPhoto> getPhotoListForProfileInSnapshotLatestVersion(String snapsthoName, String profile_id) throws MongoException, UnknownHostException {
        String latestVersion = this.findHighestVersionForSnapshot(snapsthoName);
        return this.getPhotoListForProfileInSnapshotSpecificVersion(snapsthoName, latestVersion, profile_id);
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
            PostJsonMongo post = new PostJsonMongo(dbc.next());

            ISharedPlace place = post.getPlace();
            if ((place != null) && (place.getName() != null)) {
                list.add(place);
            }

        }

        return list;
    }

    @Override
    public List<IPhoto> getPhotoListSnapshotSpecificVersion(String snapshotName, String version) throws Exception {
        BasicDBObject params = new BasicDBObject();
        params.append("snapshot", snapshotName).append("version", version);

        return this.getPhotoList(params);
    }

    @Override
    public List<IPhoto> getPhotoListSnapshotLatestVersion(String snapsthoName) throws Exception {
        return this.getPhotoListSnapshotSpecificVersion(snapsthoName, this.findHighestVersionForSnapshot(snapsthoName));
    }

    private List<IPhoto> getPhotoList(DBObject params) throws MongoException, UnknownHostException {
        DBCursor dbCursor = this.findInCollection(this.collection_name, params);

        BasicDBList dbl = new BasicDBList();
        dbl.addAll(dbCursor.toArray());

        AccountJsonMongo ason = new AccountJsonMongo();
        ason.setPhotoList(dbl);

        return ason.getPhotoList();
    }
    
    @Override
    public List<ISharedComments> getAllCommentsInSnapshotLatestVersion(String snapshot) throws Exception {
        return this.getAllCommentsInSnapshotSpecificVersion(snapshot, this.findHighestVersionForSnapshot(snapshot));
    }

    @Override
    public List<ISharedComments> getAllCommentsInSnapshotSpecificVersion(String snapshot, String version) throws Exception {
       
        List<IPhoto> photoList = this.getPhotoListSnapshotSpecificVersion(snapshot, version);
        
        List<ISharedComments> cList = new ArrayList<>(photoList.size());
        
        for(IPhoto post : photoList){
            ISharedComments c = post.getComments();
            if(c != null)
                cList.add(c);
        }
        
        return cList;
    }
}
