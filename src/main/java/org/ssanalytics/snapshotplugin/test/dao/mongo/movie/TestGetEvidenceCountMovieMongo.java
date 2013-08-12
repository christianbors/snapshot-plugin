/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.mongo.movie;

import java.net.UnknownHostException;
import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.MovieDAOMongo;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;

/**
 *
 * @author chw
 */
public class TestGetEvidenceCountMovieMongo {
    
    
    public static void main(String args[]) throws UnknownHostException{
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        
        Map<CategoryItem, Integer> map = MovieDAOMongo.getInstance().getEvidenceCountForSnapshotLatestVersion("dd");
        
        System.out.println(map.size());
        
        for(CategoryItem ci : map.keySet()){
        
            Integer i = map.get(ci);
            System.out.println("Movie name: " + ci.getName() + "   count: " + i.toString());
        }        
    }    
}
