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
public class TestPhotoTagCount {

    public static void main(String args[]) throws Exception {

        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        ConfigurationFileManager.readConfigFromFile();

        Map<NamedItem, Float> map = PhotoFetcher.getInstance().getAverageTaggedCountForPhotosOfEveryProfileInSnapshotLatestVersion("dd");

        System.out.println(map.size());

        for (NamedItem ni : map.keySet()) {

            System.out.println("profile fb_name: " + ni.getName());
            System.out.println("average tags:    " + map.get(ni));
            System.out.println("");
        }

        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        ConfigurationFileManager.readConfigFromFile();

        map = PhotoFetcher.getInstance().getAverageTaggedCountForPhotosOfEveryProfileInSnapshotLatestVersion("dd");

        System.out.println(map.size());

        for (NamedItem ni : map.keySet()) {

            System.out.println("profile fb_name: " + ni.getName());
            System.out.println("average tags:    " + map.get(ni));
            System.out.println("");
        }

    }
}
