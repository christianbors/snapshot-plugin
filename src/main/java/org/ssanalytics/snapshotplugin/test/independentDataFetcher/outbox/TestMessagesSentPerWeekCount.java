/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.independentDataFetcher.outbox;

import org.ssanalytics.snapshotplugin.test.independentDataFetcher.post.*;
import java.util.Date;
import org.ssanalytics.snapshotplugin.test.independentDataFetcher.photo.*;
import org.ssanalytics.snapshotplugin.test.independentDataFetcher.locationPost.*;
import org.ssanalytics.snapshotplugin.test.independentDataFetcher.location.*;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.LocationFetcher;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.LocationPostFetcher;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.OutboxFetcher;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.PhotoFetcher;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.PostFetcher;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhoto;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.post.IPost;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;

/**
 *
 * @author chw
 */
public class TestMessagesSentPerWeekCount {

    public static void main(String args[]) throws Exception {

        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        ConfigurationFileManager.readConfigFromFile();

        Map<Date, Integer> map = OutboxFetcher.getInstance().getOutboxCountPerWeekForSnapshotRootLatestVersion("dd");

        System.out.println(map.size());

        for (Date dt : map.keySet()) {

            System.out.println("Date of Weekstart: " + dt.toString());
            System.out.println("outbox count:      " + map.get(dt));
            System.out.println("");
        }

        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        ConfigurationFileManager.readConfigFromFile();

        map = OutboxFetcher.getInstance().getOutboxCountPerWeekForSnapshotRootLatestVersion("dd");

        System.out.println(map.size());

        for (Date dt : map.keySet()) {

            System.out.println("Date of Weekstart: " + dt.toString());
            System.out.println("outbox count:      " + map.get(dt));
            System.out.println("");
        }

    }
}
