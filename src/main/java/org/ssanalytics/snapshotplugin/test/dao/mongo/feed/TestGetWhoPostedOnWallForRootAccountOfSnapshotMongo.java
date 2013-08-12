/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.mongo.feed;


import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;

/**
 *
 * @author chw
 */
public class TestGetWhoPostedOnWallForRootAccountOfSnapshotMongo {
    
        public static void main(String args[]) throws Exception{
            
            
            ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        
            System.out.println(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getConfigurationName());
            
            Map<NamedItem, Integer> map = DaoFactory.getFeedDAO().getWhoPostedOnWallForRootAccountOfSnapshotLatestVersion("dd");
            for(NamedItem item : map.keySet()){
                System.out.println(item.getName() + " " + map.get(item).toString());
            } 
    } 
}
