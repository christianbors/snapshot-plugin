/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.independentDataFetcher.locationPhoto;

import org.ssanalytics.snapshotplugin.test.independentDataFetcher.locationPost.*;
import org.ssanalytics.snapshotplugin.test.independentDataFetcher.location.*;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.LocationFetcher;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.LocationPhotoFetcher;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.LocationPostFetcher;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;

/**
 *
 * @author chw
 */
public class TestWhoHasBeenWherePhoto {

    public static void main(String args[]) throws Exception {

        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        ConfigurationFileManager.readConfigFromFile();

        Map<ISharedPlace, List<NamedItem>> map = LocationPhotoFetcher.getInstance().getWhoHasBeenWhereSnapshotLatestVersion("dd");

        System.out.println(map.size());

        for (ISharedPlace place : map.keySet()) {

            System.out.println("place name: " + place.getName());
            for (NamedItem ni : map.get(place)) {
                System.out.println("   * " + ni.getName());
            }
        }

        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        ConfigurationFileManager.readConfigFromFile();

        map = LocationPhotoFetcher.getInstance().getWhoHasBeenWhereSnapshotLatestVersion("dd");

        System.out.println(map.size());

        for (ISharedPlace place : map.keySet()) {

            System.out.println("place name: " + place.getName());
            for (NamedItem ni : map.get(place)) {
                System.out.println("   * " + ni.getName());
            }
        }
    }
}
