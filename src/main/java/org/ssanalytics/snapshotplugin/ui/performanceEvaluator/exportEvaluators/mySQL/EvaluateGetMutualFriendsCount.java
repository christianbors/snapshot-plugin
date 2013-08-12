/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.performanceEvaluator.exportEvaluators.mySQL;

import org.ssanalytics.snapshotplugin.ui.performanceEvaluator.exportEvaluators.dao.*;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhoto;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.status.IStatus;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.FriendDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.CommentsFetcher;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.FriendFetcher;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.LocationFetcher;
import org.ssanalytics.snapshotplugin.ui.performanceEvaluator.helpers.Padder;

/**
 *
 * @author chw
 */
public class EvaluateGetMutualFriendsCount {

    public static void main(String args[]) throws Exception {




        //set DB MODE
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        ConfigurationFileManager.readConfigFromFile();
        
        //preloading
        
        FriendFetcher.getInstance().getAverageNumberOfMutualFriendsForSnapshotLatestVersion("EVAL_MYSQL_MEDIUM");
        
        //medium shot
        long time = 0 - System.currentTimeMillis();
        float f = FriendFetcher.getInstance().getAverageNumberOfMutualFriendsForSnapshotLatestVersion("EVAL_MYSQL_MEDIUM");
        time += System.currentTimeMillis();
        System.out.println("Calculated " + f + " average mutual friends in " + time + " ms");
        
        
        System.out.println("");
        System.out.println("TOTAL TIME: " + time);
    }
}
