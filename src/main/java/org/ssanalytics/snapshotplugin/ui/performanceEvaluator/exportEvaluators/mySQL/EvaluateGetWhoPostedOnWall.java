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
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.status.IStatus;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.ui.performanceEvaluator.helpers.Padder;

/**
 *
 * @author chw
 */
public class EvaluateGetWhoPostedOnWall {
    
    public static void main(String args[]) throws Exception{
        
        
        //set DB MODE
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMySQL.json";
        ConfigurationFileManager.readConfigFromFile();
        
        //preloading
        
        DaoFactory.getFeedDAO().getWhoPostedOnWallForRootAccountOfSnapshotLatestVersion("EVAL_MYSQL_MEDIUM");
        
        //medium shot
        long time = 0 - System.currentTimeMillis();
        Map<NamedItem, Integer> map = DaoFactory.getFeedDAO().getWhoPostedOnWallForRootAccountOfSnapshotLatestVersion("EVAL_MYSQL_MEDIUM");
        time += System.currentTimeMillis();
        System.out.println("Finished fetching feeds!");
        System.out.println("Fetched " + map.size() + " elements in " + time + " ms");                
        
        
        
        System.out.println("");
        System.out.println("TOTAL TIME: " + time);
        
    }
}
