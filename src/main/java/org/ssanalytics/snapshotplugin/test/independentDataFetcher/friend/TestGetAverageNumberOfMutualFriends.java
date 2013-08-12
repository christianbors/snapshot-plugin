/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.independentDataFetcher.friend;

import java.util.List;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.ActivityDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.FriendFetcher;

/**
 *
 * @author chw
 */
public class TestGetAverageNumberOfMutualFriends {

    public static void main(String args[]) throws Exception {

        /*
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";

        float f = FriendFetcher.getInstance().getAverageNumberOfMutualFriendsForSnapshotLatestVersion("dd");

        System.out.println("Mongo Average Mutual Friends for Snapshot dd " + f);
        */
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMySQL.json";

        float f = FriendFetcher.getInstance().getAverageNumberOfMutualFriendsForSnapshotLatestVersion("dd");

        System.out.println("Mongo Average Mutual Friends for Snapshot dd " + f);


        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";

        f = FriendFetcher.getInstance().getAverageNumberOfMutualFriendsForSnapshotLatestVersion("dd");

        System.out.println("Postgre Average Mutual Friends for Snapshot dd " + f);
    }
}
