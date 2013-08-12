/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.mongo.television;

import org.ssanalytics.snapshotplugin.test.dao.mongo.book.*;
import org.ssanalytics.snapshotplugin.test.dao.mongo.movie.*;
import org.ssanalytics.snapshotplugin.test.dao.mongo.television.*;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.BookDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.MovieDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.TelevisionDAOMongo;

/**
 *
 * @author chw
 */
public class TestExtendedDistinctCategoriesTelevisionMongo {
    
    public static void main(String args[]) throws Exception{
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        
        Map<String, Integer> map = DaoFactory.getTelevisionDAO().getExtendedDistinctTelevisionCategories();
        
        System.out.println(map.size());
        
        for(String s : map.keySet()){
        
            System.out.println("Category " + s + "  count: " + map.get(s));
        }   
    } 
}
