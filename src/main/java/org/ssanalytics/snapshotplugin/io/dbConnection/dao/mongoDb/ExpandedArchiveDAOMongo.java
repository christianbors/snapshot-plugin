/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IAddressBookEntry;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IExpandedArchive;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IPreviousName;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.mongoWrapper.ExpandedArchiveMongoWrapper;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IExpandedArchiveDAO;

/**
 *
 * @author chw
 */
public class ExpandedArchiveDAOMongo extends AbstractSuperDAOMongo implements IExpandedArchiveDAO{    
    
    private final String collection_name = "expanded_archive";
    
    protected ExpandedArchiveDAOMongo() throws UnknownHostException, MongoException {
        super();
        // TODO Auto-generated constructor stub
    }
    private static ExpandedArchiveDAOMongo instance = null;

    public static ExpandedArchiveDAOMongo getInstance() throws UnknownHostException, MongoException {
        if (instance == null) {
            instance = new ExpandedArchiveDAOMongo();
        }
        return instance;
    }

    @Override
    public IExpandedArchive getExpandedArchiveForSnapshotLatestVersion(String snapshot) throws MongoException, UnknownHostException {
        
        return this.getExpandedArchiveForSnapshotSpecificVersion(snapshot, this.findHighestVersionForSnapshot(snapshot));
    }

    @Override
    public IExpandedArchive getExpandedArchiveForSnapshotSpecificVersion(String snapshot, String version) throws MongoException, UnknownHostException {
        BasicDBObject params = new BasicDBObject();
        params.append("version", version);
        params.append("snapshot", snapshot);
        
        DBObject dbo = this.findOne(this.collection_name, params);
        
        return new ExpandedArchiveMongoWrapper(dbo);
    }

    @Override
    public void saveExpandedArchive(IExpandedArchive toSave, String snapshot) throws MongoException, UnknownHostException {
        String version = this.findHighestVersionForSnapshot(snapshot);
        if(!("".equals(version)))
            this.saveExpandedArchive(toSave, snapshot, version);
        else
            throw new MongoException("Highest Version of snapshot " + snapshot + " could not be found");
    }
    
    @Override
    public void saveExpandedArchive(IExpandedArchive toSave, String snapshot, String version) throws MongoException, UnknownHostException {
        
        String root_fb_id = null;
        
        BasicDBObject params = new BasicDBObject().append("value", snapshot).append(snapshot + ".version", version);
        DBObject test = this.findOne("snapshotInfo", params);
        if ((test != null) && (snapshot.equals(test.get("value").toString())))
            root_fb_id = (String) ((DBObject)test.get(snapshot)).get("root");
        
        if((root_fb_id == null) || (root_fb_id.equals("")))
            throw new MongoException("No snapshotInfo entry found for snapshot: " + snapshot + " and version: " + version);
        
        BasicDBObject expandedArchiveDbo = new BasicDBObject();
        expandedArchiveDbo.append("snapshot", snapshot);
        expandedArchiveDbo.append("version", version);
        expandedArchiveDbo.append("accountId", root_fb_id);
        expandedArchiveDbo.append("timestamp", System.currentTimeMillis());
        
        BasicDBObject data = new BasicDBObject();
                
        // prev names
        if(toSave.getPreviousNames() != null){
            
            BasicDBList prevNamesBdl = new BasicDBList();
            for(IPreviousName pvn : toSave.getPreviousNames()){
                BasicDBObject dbo = new BasicDBObject();
                dbo.put("previous_name", pvn.getPreviousName());
                prevNamesBdl.add(dbo);                
            }
            
            data.append("previous_names", prevNamesBdl);
        }
        
        // address book
        if(toSave.getAddressBook() != null){
            
            BasicDBList addressBookBdl = new BasicDBList();
            for(IAddressBookEntry abe : toSave.getAddressBook()){
                BasicDBObject dbo = new BasicDBObject();
                dbo.put("name", abe.getName());
                BasicDBList contacts = new BasicDBList();
                for(String s : abe.getContactInfoList()){
                    contacts.add(s);
                }
                dbo.put("contact_info", contacts);
                addressBookBdl.add(dbo);
            }           
            
            data.append("address_book", addressBookBdl);
        }
        
        expandedArchiveDbo.append("data", data);
        
        DBCollection dbColl = this.getCollection("expanded_archive");
        
        dbColl.insert(expandedArchiveDbo);
        
        
    }
}
