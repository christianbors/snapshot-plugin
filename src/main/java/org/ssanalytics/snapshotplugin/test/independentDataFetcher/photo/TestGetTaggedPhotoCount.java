/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.independentDataFetcher.photo;

import org.ssanalytics.snapshotplugin.test.independentDataFetcher.locationPost.*;
import org.ssanalytics.snapshotplugin.test.independentDataFetcher.location.*;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.LocationFetcher;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.LocationPostFetcher;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.PhotoFetcher;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhoto;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;

/**
 *
 * @author chw
 */
public class TestGetTaggedPhotoCount {

    public static void main(String args[]) throws Exception {

        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        ConfigurationFileManager.readConfigFromFile();

        Map<String, Integer> map = PhotoFetcher.getInstance().getTaggedPhotoCountForSnapshotLatestVersion("dd");

        System.out.println(map.size());

        Integer max = 0;

        for (String s : map.keySet()) {

            if (map.get(s) > max) {
                max = map.get(s);
            }

            System.out.println("Profile name: " + s);
            System.out.println("times tagged: " + map.get(s));
            System.out.println("");
        }

        System.out.println("HIGHEST NUMBER OF TAGS WAS : " + max);



        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        ConfigurationFileManager.readConfigFromFile();

        map = PhotoFetcher.getInstance().getTaggedPhotoCountForSnapshotLatestVersion("dd");

        System.out.println(map.size());

        max = 0;

        for (String s : map.keySet()) {

            if (map.get(s) > max) {
                max = map.get(s);
            }

            System.out.println("Profile name: " + s);
            System.out.println("times tagged: " + map.get(s));
            System.out.println("");
        }

        System.out.println("HIGHEST NUMBER OF TAGS WAS : " + max);

    }
}
