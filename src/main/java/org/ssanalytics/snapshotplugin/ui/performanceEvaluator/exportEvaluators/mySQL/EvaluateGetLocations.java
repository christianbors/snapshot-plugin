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
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.CommentsFetcher;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.LocationFetcher;
import org.ssanalytics.snapshotplugin.ui.performanceEvaluator.helpers.Padder;

/**
 *
 * @author chw
 */
public class EvaluateGetLocations {
    
    public static void main(String args[]) throws Exception{
        
        //MySQL
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        ConfigurationFileManager.readConfigFromFile();        
        
        //medium shot
        long time_mysql = 0 - System.currentTimeMillis();
        Map<NamedItem, List<ISharedPlace>> map = LocationFetcher.getInstance().getWhereFriendsHaveBeenSnapshotLatestVersion("EVAL_MYSQL_MEDIUM");
        time_mysql += System.currentTimeMillis();
        System.out.println("Finished fetching locations from MySQL/MEDIUM!");
        System.out.println("Fetched " + map.size() + " elements in " + time_mysql + " ms");                
        
        
        
        System.out.println("----------------------------------------");
        System.out.println("| EVALUATION: GET LCOATIONS            |");
        System.out.println("|--------------------------------------|");
        System.out.println("|TIME MySQL: " + Padder.padLeft(String.valueOf(time_mysql), 26) + "|");        
        System.out.println("----------------------------------------");
    }
}
