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
import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.link.ILink;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account.RootAccountJsonMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.ILinkDAO;

/**
 *
 * @author chw
 */
public class LinkDAOMongo extends AbstractSuperDAOMongo implements ILinkDAO{
    
    private String collection_name = "links";
    
    protected LinkDAOMongo() throws UnknownHostException, MongoException {
        super();
        // TODO Auto-generated constructor stub
    }
    private static LinkDAOMongo instance = null;

    public static LinkDAOMongo getInstance() throws UnknownHostException, MongoException {
        if (instance == null) {
            instance = new LinkDAOMongo();
        }
        return instance;
    }

    @Override
    public List<ILink> getAllLinksInSnapshotLatestVersion(String snapshotName) throws MongoException, UnknownHostException {
        return this.getAllLinksInSnapshotSpecificVersion(snapshotName, this.findHighestVersionForSnapshot(snapshotName));
    }

    @Override
    public List<ILink> getAllLinksInSnapshotSpecificVersion(String snapshotName, String version) throws MongoException, UnknownHostException {
        
        BasicDBObject params = new BasicDBObject().append("snapshot", snapshotName).append("version", version);
        DBCursor dbc = this.findInCollection(this.collection_name, params);
        
        BasicDBList bdl = new BasicDBList();
        bdl.addAll(dbc.toArray());
        
        RootAccountJsonMongo racson = new RootAccountJsonMongo();
        racson.setLinkList(bdl);
        
        return racson.getLinkList();
    }

    @Override
    public List<ISharedComments> getAllCommentsInSnapshotLatestVersion(String snapshot) throws Exception {
        return this.getAllCommentsInSnapshotSpecificVersion(snapshot, this.findHighestVersionForSnapshot(snapshot));
    }

    @Override
    public List<ISharedComments> getAllCommentsInSnapshotSpecificVersion(String snapshot, String version) throws Exception {
        List<ILink> linkList = this.getAllLinksInSnapshotSpecificVersion(snapshot, version);
        
        List<ISharedComments> cList = new ArrayList<>(linkList.size());
        
        for(ILink link : linkList){
            ISharedComments c = link.getComments();
            if(c != null)
                cList.add(c);
        }
        
        return cList;
    }
    
    
    
}
