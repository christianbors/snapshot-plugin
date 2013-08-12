/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.postgreSql.like;

import org.ssanalytics.snapshotplugin.test.dao.mongo.like.*;
import org.ssanalytics.snapshotplugin.test.dao.mongo.activity.*;
import org.ssanalytics.snapshotplugin.test.dao.mongo.movie.*;
import org.ssanalytics.snapshotplugin.test.dao.mongo.television.*;
import java.util.List;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.ActivityDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.MovieDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.TelevisionDAOMongo;

/**
 *
 * @author chw
 */
public class TestDistinctCategoriesLikeSql {
    
    public static void main(String args[]) throws Exception{
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        
        List<String> list = DaoFactory.getLikeDAO().getDistinctLikeCategories();
        
        System.out.println(list.size());
        
        for(String s : list){
        
            System.out.println(s);
        }   
    } 
}
