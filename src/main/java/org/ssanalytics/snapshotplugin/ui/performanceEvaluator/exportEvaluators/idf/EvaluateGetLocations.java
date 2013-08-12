/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.performanceEvaluator.exportEvaluators.idf;

import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhoto;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.CommentsFetcher;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.LocationFetcher;
import org.ssanalytics.snapshotplugin.ui.performanceEvaluator.helpers.Padder;

/**
 *
 * @author chw
 */
public class EvaluateGetLocations {
    
    public static void main(String args[]) throws Exception{
        
        //MongoDB
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        ConfigurationFileManager.readConfigFromFile();
        
        //large shot
        long time_mongo_large = 0 - System.currentTimeMillis();
        Map<NamedItem, List<ISharedPlace>> map = LocationFetcher.getInstance().getWhereFriendsHaveBeenSnapshotLatestVersion("EVAL_LARGE");
        time_mongo_large += System.currentTimeMillis();
        System.out.println("Finished fetching locations from MongoDB/LARGE!");
        System.out.println("Fetched " + map.size() + " elements in " + time_mongo_large + " ms");
        
        //medium shot
        long time_mongo_medium = 0 - System.currentTimeMillis();
        map = LocationFetcher.getInstance().getWhereFriendsHaveBeenSnapshotLatestVersion("EVAL_MEDIUM");
        time_mongo_medium += System.currentTimeMillis();
        System.out.println("Finished fetching locations from MongoDB/MEDIUM!");
        System.out.println("Fetched " + map.size() + " elements in " + time_mongo_medium + " ms");
        
        //small shot
        long time_mongo_small = 0 - System.currentTimeMillis();
        map = LocationFetcher.getInstance().getWhereFriendsHaveBeenSnapshotLatestVersion("EVAL_SMALL");
        time_mongo_small += System.currentTimeMillis();
        System.out.println("Finished fetching locations from MongoDB/SMALL!");
        System.out.println("Fetched " + map.size() + " elements in " + time_mongo_small + " ms");
        
        
        //PostgreSQL
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        ConfigurationFileManager.readConfigFromFile();
        
        
        LocationFetcher.getInstance().getWhereFriendsHaveBeenSnapshotLatestVersion("EVAL_SMALL");
        
        //small shot
        long time_postgre_small = 0 - System.currentTimeMillis();
        map = LocationFetcher.getInstance().getWhereFriendsHaveBeenSnapshotLatestVersion("EVAL_SMALL");
        time_postgre_small += System.currentTimeMillis();
        System.out.println("Finished fetching locations from PostgreSQL/SMALL!");
        System.out.println("Fetched " + map.size() + " elements in " + time_postgre_small + " ms");
        
        //medium shot
        long time_postgre_medium = 0 - System.currentTimeMillis();
        map = LocationFetcher.getInstance().getWhereFriendsHaveBeenSnapshotLatestVersion("EVAL_MEDIUM");
        time_postgre_medium += System.currentTimeMillis();
        System.out.println("Finished fetching locations from PostgreSQL/MEDIUM!");
        System.out.println("Fetched " + map.size() + " elements in " + time_postgre_medium + " ms");
        
        //large shot
        long time_postgre_large = 0 - System.currentTimeMillis();
        map = LocationFetcher.getInstance().getWhereFriendsHaveBeenSnapshotLatestVersion("EVAL_LARGE");
        time_postgre_large += System.currentTimeMillis();
        System.out.println("Finished fetching locations from PostgreSQL/LARGE!");
        System.out.println("Fetched " + map.size() + " elements in " + time_postgre_large + " ms");
        
        //MySQL
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMySQL.json";
        ConfigurationFileManager.readConfigFromFile();
        
        //small shot
        long time_mysql_small = 0 - System.currentTimeMillis();
        //map = LocationFetcher.getInstance().getWhereFriendsHaveBeenSnapshotLatestVersion("EVAL_SMALL");
        time_mysql_small += System.currentTimeMillis();
        //System.out.println("Finished fetching comments from MySQL/SMALL!");
        //System.out.println("Fetched " + map.size() + " elements in " + time_mysql_small + " ms");
        
        //medium shot
        long time_mysql_medium = 0 - System.currentTimeMillis();
        //map = LocationFetcher.getInstance().getWhereFriendsHaveBeenSnapshotLatestVersion("EVAL_MEDIUM");
        time_mysql_medium += System.currentTimeMillis();
        //System.out.println("Finished fetching comments from MySQL/MEDIUM!");
        //System.out.println("Fetched " + map.size() + " elements in " + time_mysql_medium + " ms");
        
        //large shot
        long time_mysql_large = 0 - System.currentTimeMillis();
        //map = LocationFetcher.getInstance().getWhereFriendsHaveBeenSnapshotLatestVersion("EVAL_LARGE");
        time_mysql_large += System.currentTimeMillis();
        //System.out.println("Finished fetching comments from MySQL/LARGE!");
        //System.out.println("Fetched " + map.size() + " elements in " + time_mysql_large + " ms");        
        
        System.out.println("----------------------------------------");
        System.out.println("| EVALUATION: GET ALL LOCATIONS        |");
        System.out.println("|--------------------------------------|");
        System.out.println("|  SHOT  |  MONGO  | POSTGRE |  MySQL  |");
        System.out.println("|  SMALL | " + Padder.padLeft(String.valueOf(time_mongo_small), 8) + "| " + Padder.padLeft(String.valueOf(time_postgre_small), 8) + "| " + Padder.padLeft(String.valueOf(time_mysql_small), 8) + "|");
        System.out.println("| MEDIUM | " + Padder.padLeft(String.valueOf(time_mongo_medium), 8) + "| " + Padder.padLeft(String.valueOf(time_postgre_medium), 8) + "| " + Padder.padLeft(String.valueOf(time_mysql_medium), 8) + "|");
        System.out.println("|  LARGE | " + Padder.padLeft(String.valueOf(time_mongo_large), 8) + "| " + Padder.padLeft(String.valueOf(time_postgre_large), 8) + "| " + Padder.padLeft(String.valueOf(time_mysql_large), 8) + "|");
        System.out.println("----------------------------------------");
    }
}
