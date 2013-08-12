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
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IPostDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.post.IPost;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.status.IStatus;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account.AccountJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.post.PostJsonMongo;

/**
 *
 * @author chw
 */
public class PostDAOMongo extends AbstractSuperDAOMongo implements IPostDAO {

    private final String collection_name = "posts";

    private PostDAOMongo() throws UnknownHostException, MongoException {
        super();
    }
    private static PostDAOMongo instance = null;

    public static PostDAOMongo getInstance() throws UnknownHostException, MongoException {
        if (instance == null) {
            instance = new PostDAOMongo();
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
            PostJsonMongo post = new PostJsonMongo(dbc.next());

            ISharedPlace place = post.getPlace();
            if ((place != null) && (place.getName() != null)) {
                list.add(place);
            }

        }

        return list;
    }

    @Override
    public List<IPost> getPostListForProfileSnapshotLatestVersion(String profile_id, String snapshot) throws Exception {
        return this.getPostListForProfileSnapshotSpecificVersion(profile_id, snapshot, this.findHighestVersionForSnapshot(snapshot));
    }

    @Override
    public List<IPost> getPostListForProfileSnapshotSpecificVersion(String profile_id, String snapshot, String version) throws Exception {
        BasicDBObject params = new BasicDBObject();
        params.append("snapshot", snapshot);
        params.append("accountId", profile_id);
        params.append("version", version);
        
        return this.getPosts(params);
        
     
    }
    
    private List<IPost> getPosts(DBObject params) throws MongoException, UnknownHostException{

        DBCursor dbc = this.findInCollection(this.collection_name, params);
        
        AccountJsonMongo acson = new AccountJsonMongo();
        BasicDBList bdl = new BasicDBList();
        bdl.addAll(dbc.toArray());
        acson.setPostList(bdl);
        
        return acson.getPostList();   
    }

    @Override
    public List<IPost> getAllPostsInSnapshotLatestVersion(String snapshot) throws MongoException, UnknownHostException {
        return this.getAllPostsInSnapshotSpecificVersion(snapshot, this.findHighestVersionForSnapshot(snapshot));
    }

    @Override
    public List<IPost> getAllPostsInSnapshotSpecificVersion(String snapshot, String version) throws MongoException, UnknownHostException {
        BasicDBObject params = new BasicDBObject().append("snapshot", snapshot).append("version", version);
        return this.getPosts(params);
    }

    @Override
    public List<ISharedComments> getAllCommentsInSnapshotLatestVersion(String snapshot) throws Exception {
        return this.getAllCommentsInSnapshotSpecificVersion(snapshot, this.findHighestVersionForSnapshot(snapshot));
    }

    @Override
    public List<ISharedComments> getAllCommentsInSnapshotSpecificVersion(String snapshot, String version) throws Exception {
       
        List<IPost> postList = this.getAllPostsInSnapshotSpecificVersion(snapshot, version);
        
        List<ISharedComments> cList = new ArrayList<>(postList.size());
        
        for(IPost post : postList){
            ISharedComments c = post.getComments();
            if(c != null)
                cList.add(c);
        }
        
        return cList;
    }
}
