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
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.ILinkDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.INoteDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.link.ILink;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.note.INote;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account.RootAccountJsonMongo;

/**
 *
 * @author chw
 */
public class NoteDAOMongo extends AbstractSuperDAOMongo implements INoteDAO{
    
    private String collection_name = "notes";
    
    protected NoteDAOMongo() throws UnknownHostException, MongoException {
        super();
        // TODO Auto-generated constructor stub
    }
    private static NoteDAOMongo instance = null;

    public static NoteDAOMongo getInstance() throws UnknownHostException, MongoException {
        if (instance == null) {
            instance = new NoteDAOMongo();
        }
        return instance;
    }

    @Override
    public List<INote> getAllNotesInSnapshotLatestVersion(String snapshotName) throws MongoException, UnknownHostException {
        return this.getAllNotesInSnapshotSpecificVersion(snapshotName, this.findHighestVersionForSnapshot(snapshotName));
    }

    @Override
    public List<INote> getAllNotesInSnapshotSpecificVersion(String snapshotName, String version) throws MongoException, UnknownHostException {
        
        BasicDBObject params = new BasicDBObject().append("snapshot", snapshotName).append("version", version);
        DBCursor dbc = this.findInCollection(this.collection_name, params);
        
        BasicDBList bdl = new BasicDBList();
        bdl.addAll(dbc.toArray());
        
        RootAccountJsonMongo racson = new RootAccountJsonMongo();
        racson.setNoteList(bdl);
        
        return racson.getNoteList();
    }   

    @Override
    public List<ISharedComments> getAllCommentsInSnapshotLatestVersion(String snapshot) throws Exception {
        return this.getAllCommentsInSnapshotSpecificVersion(snapshot, this.findHighestVersionForSnapshot(snapshot));
    }

    @Override
    public List<ISharedComments> getAllCommentsInSnapshotSpecificVersion(String snapshot, String version) throws Exception {
        
        List<INote> noteList = this.getAllNotesInSnapshotSpecificVersion(snapshot, version);
        
        List<ISharedComments> cList = new ArrayList<>(noteList.size());
        
        for(INote note : noteList){
            ISharedComments c = note.getComments();
            if(c != null)
                cList.add(c);
        }
        
        return cList;
    }
    
}
