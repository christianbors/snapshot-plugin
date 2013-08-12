/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.performanceEvaluator.importEvaluators;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import org.json.simple.parser.ParseException;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.PhotoDAOSql;
import org.ssanalytics.snapshotplugin.io.jsonSnapshotInsert.sql.JsonSnapshotInserterSql;

/**
 *
 * @author chw
 */
public class EvaluateBatchInsertSmallShot {
    
    public static void main(String args[]) throws SQLException, ParseException, IOException{        
     
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        
        JsonSnapshotInserterSql inserter = new JsonSnapshotInserterSql();
        
        PhotoDAOSql.NumberOfAccounts = 0;
        PhotoDAOSql.NumberOfOperations = 0;
        PhotoDAOSql.NumberOfPhotos = 0;
        PhotoDAOSql.totalTime = 0;
        PhotoDAOSql.withoutBatchInsert = false;
        
        inserter.insertFromFile(new File("D:/git/ssanalytics/testdata/socialsnapshot1336659346.json"), "PHOTOTEST", System.currentTimeMillis());
        
        System.out.println("");
        System.out.println("==> RESULTS USING BATCH INSERT");
        System.out.println("==> Number of Accounts: " + PhotoDAOSql.NumberOfAccounts);
        System.out.println("==> Number of Photos: " + PhotoDAOSql.NumberOfPhotos);
        System.out.println("==> Number of Operations: " + PhotoDAOSql.NumberOfOperations);        
        System.out.println("==> Total Time: " + PhotoDAOSql.totalTime);
        System.out.println("");
        
        PhotoDAOSql.NumberOfAccounts = 0;
        PhotoDAOSql.NumberOfOperations = 0;
        PhotoDAOSql.NumberOfPhotos = 0;
        PhotoDAOSql.totalTime = 0;
        PhotoDAOSql.withoutBatchInsert = true;
        
        inserter.insertFromFile(new File("D:/git/ssanalytics/testdata/socialsnapshot1336659346.json"), "PHOTOTEST", System.currentTimeMillis());
        
        System.out.println("");
        System.out.println("==> RESULTS WITHOUT BATCH INSERT");
        System.out.println("==> Number of Accounts: " + PhotoDAOSql.NumberOfAccounts);
        System.out.println("==> Number of Photos: " + PhotoDAOSql.NumberOfPhotos);
        System.out.println("==> Number of Operations: " + PhotoDAOSql.NumberOfOperations);        
        System.out.println("==> Total Time: " + PhotoDAOSql.totalTime);
        System.out.println("");
        
    }    
}
