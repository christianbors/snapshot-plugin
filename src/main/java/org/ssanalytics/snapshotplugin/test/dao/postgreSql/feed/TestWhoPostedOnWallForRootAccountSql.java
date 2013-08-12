/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.postgreSql.feed;

import java.sql.SQLException;
import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.FeedDAOSql;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;

/**
 *
 * @author chw
 */
public class TestWhoPostedOnWallForRootAccountSql {
    
    public static void main(String args[]) throws SQLException{
        
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        
        Map<NamedItem, Integer> map = FeedDAOSql.getInstance().getWhoPostedOnWallForRootAccountOfSnapshotLatestVersion("");
        for(NamedItem item : map.keySet()){
            System.out.println(item.getName() + " " + map.get(item).toString());
        }
        
        
        
    }    
}
