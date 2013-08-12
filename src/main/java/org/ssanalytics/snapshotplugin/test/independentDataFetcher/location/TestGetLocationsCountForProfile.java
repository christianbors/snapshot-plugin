/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.independentDataFetcher.location;

import org.ssanalytics.snapshotplugin.test.dao.mongo.post.*;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.LocationFetcher;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhoto;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.post.IPost;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;

/**
 *
 * @author chw
 */
public class TestGetLocationsCountForProfile {
    
    public static void main(String args[]) throws Exception{
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        
        Map<ISharedPlace, Integer> map = LocationFetcher.getInstance().getPlacesAndCountWhereProfileHasBeenLatestVersion("773956213", "dd");
        
        System.out.println(map.size());
        
        for(ISharedPlace place : map.keySet()){
            System.out.println("place name: " + place.getName() + "   count: " + map.get(place));
        }
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        ConfigurationFileManager.readConfigFromFile();
        
        map = LocationFetcher.getInstance().getPlacesAndCountWhereProfileHasBeenLatestVersion("773956213", "dd");
        
        System.out.println(map.size());
        
        for(ISharedPlace place : map.keySet()){
            System.out.println("place name: " + place.getName() + "   count: " + map.get(place));
        }
    }    
}
