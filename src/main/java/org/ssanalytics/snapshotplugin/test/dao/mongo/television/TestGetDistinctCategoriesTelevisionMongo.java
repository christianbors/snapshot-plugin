/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.mongo.television;

import java.util.List;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.TelevisionDAOMongo;

/**
 *
 * @author chw
 */
public class TestGetDistinctCategoriesTelevisionMongo {
    
    public static void main(String args[]) throws Exception{
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        
        List<String> list = TelevisionDAOMongo.getInstance().getDistinctTelevisionCategories();
        
        System.out.println(list.size());
        
        for(String s : list){
        
            System.out.println(s);
        }   
    } 
}
