/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.performanceEvaluator.exportEvaluators.timeline;


import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.LocationFetcher;
import org.ssanalytics.snapshotplugin.io.dbConnection.timeflow.TimeflowEventExporter;
import org.ssanalytics.snapshotplugin.ui.performanceEvaluator.helpers.Padder;

/**
 *
 * @author chw
 */
public class EvaluateEventExport {
    
    public static void main(String args[]) throws Exception{
        
        //MongoDB
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        ConfigurationFileManager.readConfigFromFile();
      
        
        //TimeflowEventExporter.getInstance().exportEventTimelineForProfileAndSnapshotLatestVersion("TimelineEventSmallMongo.timeline", "EVAL_LARGE", "1067184503");
        
        //large shot
        long time_mongo_large = 0 - System.currentTimeMillis();
        TimeflowEventExporter.getInstance().exportEventTimelineForProfileAndSnapshotLatestVersion("TimelineEventSmallMongo.timeline", "EVAL_LARGE", "633050370");
        time_mongo_large += System.currentTimeMillis();
        System.out.println("Finished exporting event timeline from MongoDB/SMALL!");
        System.out.println("Time taken: " + time_mongo_large + " ms");
        
        
        //medium shot
        long time_mongo_medium = 0 - System.currentTimeMillis();
        TimeflowEventExporter.getInstance().exportEventTimelineForProfileAndSnapshotLatestVersion("TimelineEventMediumMongo.timeline", "EVAL_MEDIUM", "1828629418");
        time_mongo_medium += System.currentTimeMillis();
        System.out.println("Finished exporting event timeline from MongoDB/MEDIUM!");
        System.out.println("Time taken: " + time_mongo_medium + " ms");
        
        
        //small shot
        long time_mongo_small = 0 - System.currentTimeMillis();
        TimeflowEventExporter.getInstance().exportEventTimelineForProfileAndSnapshotLatestVersion("TimelineEventSmallMongo.timeline", "EVAL_SMALL", "100001535874373");
        time_mongo_small += System.currentTimeMillis();
        System.out.println("Finished exporting event timeline from MongoDB/SMALL!");
        System.out.println("Time taken: " + time_mongo_small + " ms");     
        
        
        //PostgreSQL
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        ConfigurationFileManager.readConfigFromFile();
        
 
        //TimeflowEventExporter.getInstance().exportEventTimelineForProfileAndSnapshotLatestVersion("TimelineEventLargePostgre.timeline", "EVAL_LARGE", "1067184503");
        
        //large shot
        long time_postgre_large = 0 - System.currentTimeMillis();
        TimeflowEventExporter.getInstance().exportEventTimelineForProfileAndSnapshotLatestVersion("TimelineEventLargePostgre.timeline", "EVAL_LARGE", "633050370");
        time_postgre_large += System.currentTimeMillis();
        System.out.println("Finished exporting event timeline from PostgreSQL/SMALL!");
        System.out.println("Time taken: " + time_postgre_large + " ms");
        
        //medium shot
        long time_postgre_medium = 0 - System.currentTimeMillis();
        TimeflowEventExporter.getInstance().exportEventTimelineForProfileAndSnapshotLatestVersion("TimelineEventMediumPostgre.timeline", "EVAL_MEDIUM", "1828629418");
        time_postgre_medium += System.currentTimeMillis();
        System.out.println("Finished exporting event timeline from PostgreSQL/MEDIUM!");
        System.out.println("Time taken: " + time_postgre_medium + " ms");
        
        //small shot
        long time_postgre_small = 0 - System.currentTimeMillis();
        TimeflowEventExporter.getInstance().exportEventTimelineForProfileAndSnapshotLatestVersion("TimelineEventSmallPostgre.timeline", "EVAL_SMALL", "100001535874373");
        time_postgre_small += System.currentTimeMillis();
        System.out.println("Finished exporting event timeline from PostgreSQL/SMALL!");
        System.out.println("Time taken: " + time_postgre_small + " ms");
        
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
