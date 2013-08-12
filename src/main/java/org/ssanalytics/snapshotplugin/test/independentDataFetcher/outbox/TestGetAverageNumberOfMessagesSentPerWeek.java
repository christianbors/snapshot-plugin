/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.independentDataFetcher.outbox;

import org.ssanalytics.snapshotplugin.test.independentDataFetcher.friend.*;
import java.util.List;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.ActivityDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.FriendFetcher;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.OutboxFetcher;

/**
 *
 * @author chw
 */
public class TestGetAverageNumberOfMessagesSentPerWeek {

    public static void main(String args[]) throws Exception {

        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";

        float f = OutboxFetcher.getInstance().getAverageMessagesSentPerWeekForRootOfSnapshotLatestVersion("dd");

        System.out.println("Mongo Average Number of messages sent per week for Snapshot dd " + f);


        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";

        f = OutboxFetcher.getInstance().getAverageMessagesSentPerWeekForRootOfSnapshotLatestVersion("dd");

        System.out.println("Postgre Average Number of messages sent per week for Snapshot dd " + f);
    }
}
