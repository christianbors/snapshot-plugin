/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.postgreSql.television;

import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;


/**
 *
 * @author chw
 */
public class TestExtendedDistinctCategoriesTelevisionSql {
    
    public static void main(String args[]) throws Exception{
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        
        Map<String, Integer> map = DaoFactory.getTelevisionDAO().getExtendedDistinctTelevisionCategories();
        
        System.out.println(map.size());
        
        for(String s : map.keySet()){
        
            System.out.println("Category " + s + "  count: " + map.get(s));
        }   
    } 
}
