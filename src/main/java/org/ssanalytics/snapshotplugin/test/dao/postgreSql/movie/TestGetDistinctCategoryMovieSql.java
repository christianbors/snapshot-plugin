/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.postgreSql.movie;

import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.MovieDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.TelevisionDAOSql;

/**
 *
 * @author Robert
 */
public class TestGetDistinctCategoryMovieSql {
    
        public static void main(String args[]) throws Exception{
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        
        java.util.List<String> list = MovieDAOSql.getInstance().getDistinctMovieCategories();
        
        System.out.println(list.size());
        
        for(String s : list){
        
            System.out.println(s);
        }   
    }  
}
