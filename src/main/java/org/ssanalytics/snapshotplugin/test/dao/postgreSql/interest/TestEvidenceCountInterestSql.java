/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.postgreSql.interest;

import java.sql.SQLException;
import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.ActivityDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.BookDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.InterestDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.MovieDAOSql;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;

/**
 *
 * @author chw
 */
public class TestEvidenceCountInterestSql {
    
    
    public static void main(String args[]) throws SQLException{
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        
        Map<CategoryItem, Integer> map = InterestDAOSql.getInstance().getEvidenceCountForSnapshotLatestVersion("");
        
        System.out.println(map.size());
        
        for(CategoryItem ci : map.keySet()){
        
            Integer i = map.get(ci);
            System.out.println("Interest name: " + ci.getName() + "   count: " + i.toString());
        }        
    }    
}
