/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.independentDataFetcher.post;

import org.ssanalytics.snapshotplugin.test.independentDataFetcher.photo.*;
import org.ssanalytics.snapshotplugin.test.independentDataFetcher.locationPost.*;
import org.ssanalytics.snapshotplugin.test.independentDataFetcher.location.*;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
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
public class TestPostLikeCount {

    public static void main(String args[]) throws Exception {

        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        ConfigurationFileManager.readConfigFromFile();

        Map<IPost, Integer> map = PostFetcher.getInstance().getPostLikeCountForProfileForSnapshotLatestVersion("small", "100000458083750");

        System.out.println(map.size());

        for (IPost p : map.keySet()) {

            System.out.println("Post from:       " + p.getFrom().getName());
            System.out.println("number of likes: " + map.get(p));
            System.out.println("");
        }

        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        ConfigurationFileManager.readConfigFromFile();

        map = PostFetcher.getInstance().getPostLikeCountForProfileForSnapshotLatestVersion("Bob", "100000536291185");

        System.out.println(map.size());

        for (IPost p : map.keySet()) {

            System.out.println("Post from:       " + p.getFrom().getName());
            System.out.println("number of likes: " + map.get(p));
            System.out.println("");
        }

    }
}
