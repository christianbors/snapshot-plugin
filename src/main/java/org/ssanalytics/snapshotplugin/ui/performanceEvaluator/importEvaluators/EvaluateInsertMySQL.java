/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.performanceEvaluator.importEvaluators;

import java.io.File;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.jsonSnapshotInsert.contract.IJsonSnapshotInserter;
import org.ssanalytics.snapshotplugin.io.jsonSnapshotInsert.sql.JsonSnapshotInserterSql;

/**
 *
 * @author chw
 */
public class EvaluateInsertMySQL {
    
    public static void main(String args[]) throws Exception {
                
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        ConfigurationFileManager.readConfigFromFile();
        
        IJsonSnapshotInserter inserter = new JsonSnapshotInserterSql();       
        
        
        long timeSmall = 0 - System.currentTimeMillis();
        inserter.insertFromFile(new File("D:/git/ssanalytics/testdata/evalshot_mysql_small.json"), "EVAL_MYSQL_SMALL", System.currentTimeMillis());
        timeSmall += System.currentTimeMillis();  
        
        long timeMed = 0 - System.currentTimeMillis();
        inserter.insertFromFile(new File("D:/git/ssanalytics/testdata/evalshot_mysql_medium.json"), "EVAL_MYSQL_MEDIUM", System.currentTimeMillis());
        timeMed += System.currentTimeMillis();        
  
        
        System.out.println("----------------------------");
        System.out.println("TIME MEDIUM: " + timeMed);
        System.out.println("TIME SMALL:  " + timeSmall);
        System.out.println("----------------------------");
    }
    
}
