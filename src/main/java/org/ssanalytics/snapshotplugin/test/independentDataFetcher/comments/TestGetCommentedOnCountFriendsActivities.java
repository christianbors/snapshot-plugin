package org.ssanalytics.snapshotplugin.test.independentDataFetcher.comments;

import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.CommentsFetcher;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;

/**
 *
 * @author chw
 */
public class TestGetCommentedOnCountFriendsActivities {

    public static void main(String args[]) throws Exception {
        


        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        ConfigurationFileManager.readConfigFromFile();

        Map<NamedItem, Integer> map = CommentsFetcher.getInstance().getCommentedOnCountFriendsActivitiesForSnapshotLatestVersion("dd");

        System.out.println(map.size());

        for (NamedItem ni : map.keySet()) {

            System.out.println("Friend Name:   " + ni.getName());
            System.out.println("comment count: " + map.get(ni));
            System.out.println("");
        }


        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        ConfigurationFileManager.readConfigFromFile();

        map = CommentsFetcher.getInstance().getCommentedOnCountFriendsActivitiesForSnapshotLatestVersion("dd");

        System.out.println(map.size());

        for (NamedItem ni : map.keySet()) {

            System.out.println("Friend Name:   " + ni.getName());
            System.out.println("comment count: " + map.get(ni));
            System.out.println("");
        }

    }
}
