/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.chart.charts4j;

import com.googlecode.charts4j.Country;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.GeographicalArea;
import com.googlecode.charts4j.MapChart;
import com.googlecode.charts4j.PoliticalBoundary;
import java.util.Collections;
import java.util.HashMap;
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
 * @author christian
 */
public class TestGeoChart {

    public static void main(String args[]) throws Exception {
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";

        MapChart chart = GCharts.newMapChart(GeographicalArea.WORLD);

        Map<String, Integer> countryCount = new HashMap<>();
        for (Map.Entry<ISharedPlace, List<NamedItem>> entry : LocationPhotoFetcher.getInstance().getWhoHasBeenWhereSnapshotLatestVersion("Bob").entrySet()) {
            if (!countryCount.containsKey(entry.getKey().getLocation().getCountry())) {
                countryCount.put(entry.getKey().getLocation().getCountry(), 1);
            } else {
                int val = countryCount.get(entry.getKey().getLocation().getCountry()).intValue();
                val++;
            }
        }

        for (Map.Entry<String, Integer> country : countryCount.entrySet()) {
            System.out.println(country.getKey() + " had " + country.getValue().toString() + " visitors");
        }

        System.out.println("");
        for (Map.Entry<NamedItem, List<ISharedPlace>> entry : LocationFetcher.getInstance().getWhereFriendsHaveBeenSnapshotLatestVersion("Bob").entrySet()) {
            for (ISharedPlace place : entry.getValue()) {
                System.out.println(place.getLocation().getCountry());
                System.out.println(place.getName());
                System.out.println("");
                if (!countryCount.containsKey(place.getLocation().getCountry())) {
                    countryCount.put(place.getLocation().getCountry(), 1);
                } else {
                    countryCount.put(place.getLocation().getCountry(), countryCount.get(place.getLocation().getCountry()).intValue()+1);
                }
            }
        }

//        for (Map.Entry<String, Integer> country : countryCount.entrySet()) {
//            System.out.println(country.getKey() + " had " + country.getValue().toString() + " visitors");
//        }
        
    }
}
