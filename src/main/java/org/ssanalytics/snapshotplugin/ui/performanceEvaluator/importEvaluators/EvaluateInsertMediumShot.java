/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.performanceEvaluator.importEvaluators;

import java.io.File;
import java.sql.SQLException;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.jsonSnapshotInsert.contract.IJsonSnapshotInserter;
import org.ssanalytics.snapshotplugin.io.jsonSnapshotInsert.mongodb.JsonSnapshotInserterMongo;
import org.ssanalytics.snapshotplugin.io.jsonSnapshotInsert.sql.JsonSnapshotInserterSql;

/**
 *
 * @author chw
 */
public class EvaluateInsertMediumShot {
    
    public static void main(String args[]) throws Exception{
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        ConfigurationFileManager.readConfigFromFile();
        
        IJsonSnapshotInserter inserter = new JsonSnapshotInserterSql();       
        
        long timeSql = 0 - System.currentTimeMillis();
        inserter.insertFromFile(new File("D:/git/ssanalytics/testdata/evalshot_medium.json"), "EVAL_MEDIUM", System.currentTimeMillis());
        timeSql += System.currentTimeMillis();
        
        
        
        inserter = new JsonSnapshotInserterMongo();
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        ConfigurationFileManager.readConfigFromFile();
        
        long timeMongo = 0-System.currentTimeMillis();
        inserter.insertFromFile(new File("D:/git/ssanalytics/testdata/evalshot_medium.json"), "EVAL_MEDIUM", System.currentTimeMillis());
        timeMongo += System.currentTimeMillis();       
        
        
        System.out.println("----------------------------");
        System.out.println("TIME SQL: " + timeSql);
        System.out.println("TIME MONGO: " + timeMongo);
        System.out.println("----------------------------");
        
        
    }
    
}
