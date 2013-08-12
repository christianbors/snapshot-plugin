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
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.IdProvider;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.PhotoDAOSql;
import org.ssanalytics.snapshotplugin.io.jsonSnapshotInsert.sql.JsonSnapshotInserterSql;

/**
 *
 * @author chw
 */
public class EvaluateIdProviderMediumShot {
    
    public static void main(String args[]) throws SQLException, ParseException, IOException{        
     
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        
        JsonSnapshotInserterSql inserter = new JsonSnapshotInserterSql();
        
        IdProvider.setNewBufferSize(1);
        
        long current_id = IdProvider.getInstance().getNextId();
        long time = 0 - System.currentTimeMillis();
        inserter.insertFromFile(new File("D:/git/ssanalytics/testdata/snapshot1322584105.json"), "ID_TEST", System.currentTimeMillis());
        time += System.currentTimeMillis();
        long id_range = IdProvider.getInstance().getNextId() - current_id - 1;
        
        System.out.println("");
        System.out.println("==> RESULTS FOR BUFFER SIZE 1");
        System.out.println("==> Number of ids: " + id_range);
        System.out.println("==> Total Time: " + time);
        System.out.println("");
        
        IdProvider.setNewBufferSize(10);
        
        current_id = IdProvider.getInstance().getNextId();
        time = 0 - System.currentTimeMillis();
        inserter.insertFromFile(new File("D:/git/ssanalytics/testdata/snapshot1322584105.json"), "ID_TEST", System.currentTimeMillis());
        time += System.currentTimeMillis();
        id_range = IdProvider.getInstance().getNextId() - current_id - 1;
        
        System.out.println("");
        System.out.println("==> RESULTS FOR BUFFER SIZE 10");
        System.out.println("==> Number of ids: " + id_range);
        System.out.println("==> Total Time: " + time);
        System.out.println("");
        
        IdProvider.setNewBufferSize(100);
        
        current_id = IdProvider.getInstance().getNextId();
        time = 0 - System.currentTimeMillis();
        inserter.insertFromFile(new File("D:/git/ssanalytics/testdata/snapshot1322584105.json"), "ID_TEST", System.currentTimeMillis());
        time += System.currentTimeMillis();
         id_range = IdProvider.getInstance().getNextId() - current_id - 1;
        
        System.out.println("");
        System.out.println("==> RESULTS FOR BUFFER SIZE 100");
        System.out.println("==> Number of ids: " + id_range);
        System.out.println("==> Total Time: " + time);
        System.out.println("");
        
        IdProvider.setNewBufferSize(1000);
        
        current_id = IdProvider.getInstance().getNextId();
        time = 0 - System.currentTimeMillis();
        inserter.insertFromFile(new File("D:/git/ssanalytics/testdata/snapshot1322584105.json"), "ID_TEST", System.currentTimeMillis());
        time += System.currentTimeMillis();
        id_range = IdProvider.getInstance().getNextId() - current_id - 1;
        
        System.out.println("");
        System.out.println("==> RESULTS FOR BUFFER SIZE 1000");
        System.out.println("==> Number of ids: " + id_range);
        System.out.println("==> Total Time: " + time);
        System.out.println("");
    }    
}
