/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.performanceEvaluator.exportEvaluators.mySQL;

import org.ssanalytics.snapshotplugin.ui.performanceEvaluator.exportEvaluators.dao.*;
import java.util.List;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhoto;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.status.IStatus;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.ui.performanceEvaluator.helpers.Padder;

/**
 *
 * @author chw
 */
public class EvaluateGetAllStatus {
    
    public static void main(String args[]) throws Exception{
        
        //MySQL
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        ConfigurationFileManager.readConfigFromFile();        
        
        //medium shot
        long time_mysql = 0 - System.currentTimeMillis();
        List<IStatus> statusList = DaoFactory.getStatusDAO().getAllSatusInSnapshotLatestVersion("EVAL_MYSQL_SMALL");
        time_mysql += System.currentTimeMillis();
        System.out.println("Fetched " + statusList.size() + " elements in " + time_mysql + " ms");                
        
        
        
        System.out.println("----------------------------------------");
        System.out.println("| EVALUATION: GET ALL STATUS           |");
        System.out.println("|--------------------------------------|");
        System.out.println("|TIME MySQL: " + Padder.padLeft(String.valueOf(time_mysql), 26) + "|");        
        System.out.println("----------------------------------------");
    }
}
