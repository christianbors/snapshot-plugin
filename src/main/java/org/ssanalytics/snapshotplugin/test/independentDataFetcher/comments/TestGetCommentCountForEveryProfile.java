package org.ssanalytics.snapshotplugin.test.independentDataFetcher.comments;

import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.CommentsFetcher;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;

/**
 *
 * @author chw
 */
public class TestGetCommentCountForEveryProfile {

    public static void main(String args[]) throws Exception {
        
        
        

/*
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        ConfigurationFileManager.readConfigFromFile();

        Map<NamedItem, Integer> map = CommentsFetcher.getInstance().getCommentCountForEveryProfileInSnapshotLatestVersion("dd");

        System.out.println(map.size());

        for (NamedItem ni : map.keySet()) {

            System.out.println("Friend Name:   " + ni.getName());
            System.out.println("comment count: " + map.get(ni));
            System.out.println("");
        }*/
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMySQL.json";
        ConfigurationFileManager.readConfigFromFile();

        long start = System.currentTimeMillis();
        Map<NamedItem, Integer> map = CommentsFetcher.getInstance().getCommentCountForEveryProfileInSnapshotLatestVersion("dd");
        long span = System.currentTimeMillis() - start; 

        System.out.println("MySQL: " + span + "ms \n map size: " + map.size());

        for (NamedItem ni : map.keySet()) {

            System.out.println("Friend Name:   " + ni.getName());
            System.out.println("comment count: " + map.get(ni));
            System.out.println("");
        }

        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        ConfigurationFileManager.readConfigFromFile();

        start = System.currentTimeMillis();
        map = CommentsFetcher.getInstance().getCommentCountForEveryProfileInSnapshotLatestVersion("dd");
        span = System.currentTimeMillis() - start; 

        System.out.println("POSTGRESQL: " + span + "ms \n map size: " + map.size());

        for (NamedItem ni : map.keySet()) {

            System.out.println("Friend Name:   " + ni.getName());
            System.out.println("comment count: " + map.get(ni));
            System.out.println("");
        }
    }
}
