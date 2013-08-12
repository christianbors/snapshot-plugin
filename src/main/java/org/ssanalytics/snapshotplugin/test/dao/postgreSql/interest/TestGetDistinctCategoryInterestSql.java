/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.postgreSql.interest;

import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.BookDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.InterestDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.MovieDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.TelevisionDAOSql;

/**
 *
 * @author Robert
 */
public class TestGetDistinctCategoryInterestSql {
    
        public static void main(String args[]) throws Exception{
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        
        java.util.List<String> list = InterestDAOSql.getInstance().getDistinctInterestCategories();
        
        System.out.println(list.size());
        
        for(String s : list){
        
            System.out.println(s);
        }   
    }  
}
