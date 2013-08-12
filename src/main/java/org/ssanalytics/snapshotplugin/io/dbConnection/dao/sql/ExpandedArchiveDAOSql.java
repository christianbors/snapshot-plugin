/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.*;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.openide.util.NotImplementedException;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IAddressBookEntry;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IExpandedArchive;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IPreviousName;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.mongoWrapper.ExpandedArchiveMongoWrapper;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IExpandedArchiveDAO;

/**
 *
 * @author chw
 */
public class ExpandedArchiveDAOSql extends AbstractSuperDAOSql implements IExpandedArchiveDAO{    
    
    private final String collection_name = "expanded_archive";
    
    protected ExpandedArchiveDAOSql() throws SQLException {
        super();
        // TODO Auto-generated constructor stub
    }
    private static ExpandedArchiveDAOSql instance = null;

    public static ExpandedArchiveDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new ExpandedArchiveDAOSql();
        }
        return instance;
    }

    @Override
    public IExpandedArchive getExpandedArchiveForSnapshotLatestVersion(String snapshot) throws SQLException {
        
        return this.getExpandedArchiveForSnapshotSpecificVersion(snapshot, this.getHighestVersion(snapshot));
    }

    @Override
    public IExpandedArchive getExpandedArchiveForSnapshotSpecificVersion(String snapshot, String version) throws SQLException {

        throw new NotImplementedException();
    }

    @Override
    public void saveExpandedArchive(IExpandedArchive toSave, String snapshot) throws SQLException {
        String version = this.getHighestVersion(snapshot);
        if(!("".equals(version)))
            this.saveExpandedArchive(toSave, snapshot, version);
        else
            throw new MongoException("Highest Version of snapshot " + snapshot + " could not be found");
    }
    
    @Override
    public void saveExpandedArchive(IExpandedArchive toSave, String snapshot, String version) throws SQLException {
        
        String highestVersion = this.getHighestVersion(snapshot);
        if(Integer.valueOf(highestVersion) < Integer.valueOf(version))
            throw new SQLException("A snapshot " + snapshot + " was not found with version " + version);
        
        if(toSave != null){
            PreparedStatement pst = this.prepareStatement("SELECT id FROM snapshotinfo WHERE snapshotname = ? AND version = ?");
            this.setStringOrNull(1, snapshot, pst);
            this.setStringOrNull(2, version, pst);
            
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()){
                long snapshot_id = rs.getLong("id");
                
                pst = this.prepareStatement("SELECT id from expanded_archive WHERE snapshot_id = ?");
                pst.setLong(1, snapshot_id);
                rs = pst.executeQuery();
                if(rs.next())
                    throw new SQLException("An expanded archive as alredy been stored for this snapshot!");
                
                long ea_id = IdProvider.getInstance().getNextId();
                
                pst = this.prepareStatement("INSERT INTO expanded_archive(id, snapshot_id) values (?, ?)");
                pst.setLong(1, ea_id);
                pst.setLong(2, snapshot_id);
                
                pst.execute();
                
                this.savePreviousNames(toSave.getPreviousNames(), ea_id);        
                
                this.saveAddressBook(toSave.getAddressBook(), ea_id);
            }            
        }   
    }
    
    private void saveAddressBook(List<IAddressBookEntry> toSave, long ea_id) throws SQLException{
        if(toSave == null)
            return;
        
        PreparedStatement pstOuter = this.prepareStatement("INSERT INTO ea_address_book_entry (id, ea_id, contact_name) VALUES(?, ?, ?)");
        pstOuter.clearBatch();
        pstOuter.setLong(2, ea_id);
        
        PreparedStatement pstInner = this.prepareStatement("INSERT INTO ea_address_book_contact_info (id, address_book_id, contact_info) VALUES (?, ?, ?)");
        pstInner.clearBatch();
        
        for(IAddressBookEntry entry : toSave){
            long entry_id = IdProvider.getInstance().getNextId();
            pstOuter.setLong(1, entry_id);
            pstOuter.setString(3, entry.getName());
            pstOuter.addBatch();
            
            for(String s : entry.getContactInfoList()){
                pstInner.clearParameters();
                pstInner.setLong(1, IdProvider.getInstance().getNextId());
                pstInner.setLong(2, entry_id);
                pstInner.setString(3, s);
                pstInner.addBatch();                
            }            
        }
        
        pstInner.executeBatch();
        pstOuter.executeBatch();
    }
    
    private void savePreviousNames(List<IPreviousName> toSave, long ea_id) throws SQLException{
        if(toSave == null)
            return;
        
        PreparedStatement pst = this.prepareStatement("INSERT INTO ea_previous_name (id, ea_id, previous_name) VALUES(?, ?, ?)");
        pst.clearBatch();
        pst.setLong(2, ea_id);
        
        for(IPreviousName pv : toSave){
            pst.setLong(1, IdProvider.getInstance().getNextId());
            pst.setString(3, pv.getPreviousName());
            pst.addBatch();
        }
        
        try{
        pst.executeBatch();
        }catch(SQLException ex){
            ex.getNextException().printStackTrace();
        }
    }
    
    
}
