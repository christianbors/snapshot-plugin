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
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.VideoDAOSql;
import org.ssanalytics.snapshotplugin.io.jsonSnapshotInsert.sql.JsonSnapshotInserterSql;

/**
 *
 * @author chw
 */
public class EvaluateBatchInsertMediumShot {
    
    public static void main(String args[]) throws SQLException, ParseException, IOException{        
     
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        
        JsonSnapshotInserterSql inserter = new JsonSnapshotInserterSql();
        
        PhotoDAOSql.NumberOfAccounts = 0;
        PhotoDAOSql.NumberOfOperations = 0;
        PhotoDAOSql.NumberOfPhotos = 0;
        PhotoDAOSql.totalTime = 0;
        PhotoDAOSql.withoutBatchInsert = false;
        VideoDAOSql.NumberOfAccounts = 0;
        VideoDAOSql.NumberOfOperations = 0;
        VideoDAOSql.NumberOfVideos = 0;
        VideoDAOSql.totalTime = 0;
        VideoDAOSql.withoutBatchInsert = false;
        
        inserter.insertFromFile(new File("D:/git/ssanalytics/testdata/snapshot1322584105.json"), "PHOTOTEST", System.currentTimeMillis());
        
        System.out.println("");
        System.out.println("==> RESULTS USING BATCH INSERT (PHOTO)");
        System.out.println("==> Number of Accounts: " + PhotoDAOSql.NumberOfAccounts);
        System.out.println("==> Number of Photos: " + PhotoDAOSql.NumberOfPhotos);
        System.out.println("==> Number of Operations: " + PhotoDAOSql.NumberOfOperations);        
        System.out.println("==> Total Time: " + PhotoDAOSql.totalTime);
        System.out.println("");
        System.out.println("==> RESULTS USING BATCH INSERT (VIDEO)");
        System.out.println("==> Number of Accounts: " + VideoDAOSql.NumberOfAccounts);
        System.out.println("==> Number of Videos: " + VideoDAOSql.NumberOfVideos);
        System.out.println("==> Number of Operations: " + VideoDAOSql.NumberOfOperations);        
        System.out.println("==> Total Time: " + VideoDAOSql.totalTime);
        System.out.println("");
        
        PhotoDAOSql.NumberOfAccounts = 0;
        PhotoDAOSql.NumberOfOperations = 0;
        PhotoDAOSql.NumberOfPhotos = 0;
        PhotoDAOSql.totalTime = 0;
        PhotoDAOSql.withoutBatchInsert = true;
        VideoDAOSql.NumberOfAccounts = 0;
        VideoDAOSql.NumberOfOperations = 0;
        VideoDAOSql.NumberOfVideos = 0;
        VideoDAOSql.totalTime = 0;
        VideoDAOSql.withoutBatchInsert = true;
        
        inserter.insertFromFile(new File("D:/git/ssanalytics/testdata/snapshot1322584105.json"), "PHOTOTEST", System.currentTimeMillis());
        
        System.out.println("");
        System.out.println("==> RESULTS WITHOUT BATCH INSERT (PHOTO)");
        System.out.println("==> Number of Accounts: " + PhotoDAOSql.NumberOfAccounts);
        System.out.println("==> Number of Photos: " + PhotoDAOSql.NumberOfPhotos);
        System.out.println("==> Number of Operations: " + PhotoDAOSql.NumberOfOperations);        
        System.out.println("==> Total Time: " + PhotoDAOSql.totalTime);
        System.out.println("");
        System.out.println("==> RESULTS WITHOUT BATCH INSERT (VIDEO)");
        System.out.println("==> Number of Accounts: " + VideoDAOSql.NumberOfAccounts);
        System.out.println("==> Number of Videos: " + VideoDAOSql.NumberOfVideos);
        System.out.println("==> Number of Operations: " + VideoDAOSql.NumberOfOperations);        
        System.out.println("==> Total Time: " + VideoDAOSql.totalTime);
        System.out.println("");
        
    }    
}
