/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.independentDataFetcher.location;

import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.LocationFetcher;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;

/**
 *
 * @author chw
 */
public class TestGetWhereFriendsHaveBeen {

    public static void main(String args[]) throws Exception {

        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        ConfigurationFileManager.readConfigFromFile();

        Map<NamedItem, List<ISharedPlace>> map = LocationFetcher.getInstance().getWhereFriendsHaveBeenSnapshotLatestVersion("dd");

        System.out.println("number of map items: " + map.size());

        for (NamedItem ni : map.keySet()) {
            System.out.println("Friend: " + ni.getName());
            for (ISharedPlace p : map.get(ni)) {
                System.out.println("   Place: " + p.getName());
            }
        }

        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        ConfigurationFileManager.readConfigFromFile();

        map = LocationFetcher.getInstance().getWhereFriendsHaveBeenSnapshotLatestVersion("dd");

        System.out.println("number of map items: " + map.size());

        for (NamedItem ni : map.keySet()) {
            System.out.println("Friend: " + ni.getName());
            for (ISharedPlace p : map.get(ni)) {
                System.out.println("   Place: " + p.getName());
            }
        }
    }
}
