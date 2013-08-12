/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;

/**
 *
 * @author chw
 */
public class CategoryItemDAOSql extends AbstractSuperDAOSql {

    private static CategoryItemDAOSql instance = null;

    protected CategoryItemDAOSql() throws SQLException {
        super();
    }

    public static CategoryItemDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new CategoryItemDAOSql();
        }
        return instance;
    }

    public List<String> getDistinctCategories(String table_name) throws SQLException {

        PreparedStatement pst = this.prepareStatement("SELECT DISTINCT category FROM " + table_name);

        ResultSet rs = pst.executeQuery();
        List<String> retList = new LinkedList<>();
        while (rs.next()) {
            retList.add(rs.getString(1));
        }

        return retList;
    }
    
    public Long getLastDate(String snapshot, String version, String table_name) throws SQLException{
        PreparedStatement pst = this.prepareStatement("SELECT MAX(created_time) FROM " + table_name + " WHERE created_time > ? AND created_time < ? AND account_id IN (SELECT id FROM account WHERE snapshotinfo_id = (SELECT id from snapshotinfo WHERE snapshotname = ? and version = ?))");
        this.setStringOrNull(1, snapshot, pst);
        this.setStringOrNull(2, version, pst);
        
        ResultSet rs = pst.executeQuery();
        if(rs != null)
            return rs.getLong(1);
        else
            return null;
    }
    
    public Long getFirstDate(String snapshot, String version, String table_name) throws SQLException{
        PreparedStatement pst = this.prepareStatement("SELECT MIN(created_time) FROM " + table_name + " WHERE created_time > 1000 AND account_id IN (SELECT id FROM account WHERE snapshotinfo_id = (SELECT id from snapshotinfo WHERE snapshotname = ? and version = ?))");
        this.setStringOrNull(1, snapshot, pst);
        this.setStringOrNull(2, version, pst);
        
        ResultSet rs = pst.executeQuery();
        if(rs.next())
            return rs.getLong(1);
        else
            return null;
    }
    
    private Map<CategoryItem, Integer> getEvidenceCount(PreparedStatement pst) throws SQLException {
        
        Map<CategoryItem, Integer> retMap = new HashMap<>();

        ResultSet rs = pst.executeQuery();
        CategoryItem ci;

        int cnt = 0;

        while (rs.next()) {

            cnt++;

            ci = new CategoryItem(rs.getString("fb_id"), rs.getString("fb_name"), rs.getString("category"), rs.getLong("created_time"));
            if (retMap.containsKey(ci)) {
                Integer i = retMap.get(ci);
                i++;
                retMap.remove(ci);
                retMap.put(ci, i);
            } else {
                retMap.put(ci, 1);
            }
        }

        return retMap;
    }
    
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotAndVersion(String snapshotname, String version, String table_name, long timeStart, long timeEnd) throws SQLException{
        
        PreparedStatement pst = this.prepareStatement("SELECT fb_id, fb_name, category, created_time FROM " + table_name + " WHERE created_time > ? AND created_time < ? AND account_id IN (SELECT id FROM account WHERE snapshotinfo_id = (SELECT id from snapshotinfo WHERE snapshotname = ? and version = ?))");
        
        this.setLongOrNull(1, timeStart, pst);
        this.setLongOrNull(2, timeEnd, pst);
        this.setStringOrNull(3, snapshotname, pst);
        this.setStringOrNull(4, version, pst);
        
        return this.getEvidenceCount(pst);    
    }

    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotAndVersion(String snapshotname, String version, String table_name) throws SQLException {
        
        PreparedStatement pst = this.prepareStatement("SELECT fb_id, fb_name, category, created_time FROM " + table_name + " WHERE account_id IN (SELECT id FROM account WHERE snapshotinfo_id = (SELECT id from snapshotinfo WHERE snapshotname = ? and version = ?))");
        this.setStringOrNull(1, snapshotname, pst);
        this.setStringOrNull(2, version, pst);
        
        return this.getEvidenceCount(pst);        
    }

    public Map<String, String> getDistinctSnapshotsAndHighestVersion() throws SQLException {

        Map<String, String> retMap = new HashMap<>();

        ResultSet rs = this.prepareStatement("SELECT snapshotname FROM snapshotinfo").executeQuery();

        while (rs.next()) {
            String shot = rs.getString("snapshotname");
            retMap.put(shot, this.getHighestVersion(shot));
        }

        return retMap;
    }

    public Map<String, Integer> getExtendedDistinctCategories(String table_name) throws SQLException {

        Map<String, Integer> retMap = new HashMap<>();

        Map<String, String> shots = this.getDistinctSnapshotsAndHighestVersion();

        for (String snapshot : shots.keySet()) {

            PreparedStatement pst = this.prepareStatement("SELECT category FROM " + table_name + " WHERE account_id IN (SELECT id FROM account WHERE snapshotinfo_id IN (SELECT id FROM snapshotinfo WHERE snapshotname = ? AND version = ?))");
            this.setStringOrNull(1, snapshot, pst);
            this.setStringOrNull(2, shots.get(snapshot), pst);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                String category = rs.getString("category");

                if (category == null) {
                    continue;
                }

                if (retMap.containsKey(category)) {
                    Integer i = retMap.get(category);
                    i++;
                    retMap.put(category, i);
                } else {
                    retMap.put(category, 1);
                }
            }
        }

        return retMap;
    }
}
