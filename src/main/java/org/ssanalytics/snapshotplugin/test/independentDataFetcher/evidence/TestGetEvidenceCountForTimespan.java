/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.independentDataFetcher.evidence;

import org.ssanalytics.snapshotplugin.test.independentDataFetcher.post.*;
import org.ssanalytics.snapshotplugin.test.independentDataFetcher.photo.*;
import org.ssanalytics.snapshotplugin.test.independentDataFetcher.locationPost.*;
import org.ssanalytics.snapshotplugin.test.independentDataFetcher.location.*;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.EvidenceFetcher;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.LocationFetcher;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.LocationPostFetcher;
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
public class TestGetEvidenceCountForTimespan {

    public static void main(String args[]) throws Exception {
        
        long oneYear = 1000;
        oneYear *= 60;
        oneYear *= 60;
        oneYear *= 24;
        oneYear *= 365;
        
        long oneYearAgo = System.currentTimeMillis() - oneYear;

        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        ConfigurationFileManager.readConfigFromFile();

        Map<String, Integer> map = EvidenceFetcher.getInstance().getEvidenceCountForTimespanForSnapsthotLatestVersion("small", oneYearAgo, System.currentTimeMillis());

        System.out.println(map.size());

        for (String s : map.keySet()) {

            System.out.println("Category:       " + s);
            System.out.println("evidence count: " + map.get(s));
            System.out.println("");
        }


        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        ConfigurationFileManager.readConfigFromFile();

        map = EvidenceFetcher.getInstance().getEvidenceCountForTimespanForSnapsthotLatestVersion("Bob", oneYearAgo, System.currentTimeMillis());

        System.out.println(map.size());

        for (String s : map.keySet()) {

            System.out.println("Category:       " + s);
            System.out.println("evidence count: " + map.get(s));
            System.out.println("");
        }

    }
}
