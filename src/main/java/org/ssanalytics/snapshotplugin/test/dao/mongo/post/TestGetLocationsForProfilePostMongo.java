/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.mongo.post;

import java.util.List;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhoto;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.post.IPost;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;

/**
 *
 * @author chw
 */
public class TestGetLocationsForProfilePostMongo {
    
        public static void main(String args[]) throws Exception{
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        
        List<ISharedPlace> list = DaoFactory.getPostDAO().getLocationsForProfileSnapshotLatestVersion("773956213", "dd");
        
        System.out.println(list.size());
        
        for(ISharedPlace place : list){
            
            
            System.out.println("place name: " + place.getName());
            if (place.getLocation() != null) {
                System.out.println("   * longitude:  " + place.getLocation().getLongitude());
                System.out.println("   * latitude:   " + place.getLocation().getLatitude());
            }
        }
    }    
}
