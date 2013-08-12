/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.performanceEvaluator.importEvaluators;

import java.io.File;
import java.io.FileWriter;
import java.sql.SQLException;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.jsonSnapshotInsert.contract.IJsonSnapshotInserter;
import org.ssanalytics.snapshotplugin.io.jsonSnapshotInsert.mongodb.JsonSnapshotInserterMongo;
import org.ssanalytics.snapshotplugin.io.jsonSnapshotInsert.sql.JsonSnapshotInserterSql;
import org.ssanalytics.snapshotplugin.ui.performanceEvaluator.helpers.Padder;

/**
 *
 * @author chw
 */
public class EvaluateMultipleInsertMediumShot {
    
    

    public static void main(String args[]) throws Exception{
        

        
        int numberOfInserts = 20;
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        ConfigurationFileManager.readConfigFromFile();
        
        IJsonSnapshotInserter inserter = new JsonSnapshotInserterSql();
        long [] timeSql = new long[numberOfInserts];
        
        for(int i = 0; i < numberOfInserts; i++){
            timeSql[i] = 0 - System.currentTimeMillis();
            inserter.insertFromFile(new File("D:/git/ssanalytics/testdata/evalshot_medium.json"), "EVAL_MEDIUM", System.currentTimeMillis());
            timeSql[i] += System.currentTimeMillis();
            FileWriter fw = new FileWriter(new File("D:/git/ssanalytics/results/storage/consecutive2.txt"), true);
            fw.write("SQL RUN " + (i+1) + " time taken: " + timeSql[i] + "\n");
            fw.close();
        }
        

        
        inserter = new JsonSnapshotInserterMongo();
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        ConfigurationFileManager.readConfigFromFile();
        
        long [] timeMongo = new long[numberOfInserts];
        
        for(int i = 0; i < numberOfInserts; i++){
            timeMongo[i] = 0-System.currentTimeMillis();
            inserter.insertFromFile(new File("D:/git/ssanalytics/testdata/evalshot_medium.json"), "EVAL_MEDIUM", System.currentTimeMillis());
            timeMongo[i] += System.currentTimeMillis(); 
            FileWriter fw = new FileWriter(new File("D:/git/ssanalytics/results/storage/consecutive2.txt"), true);
            fw.write("MONGO RUN " + (i+1) + " time taken: " + timeMongo[i] + "\n");
            fw.close();
        }
        
        
        System.out.println("---------------------------");
        System.out.println("| RUN |   SQL   |  MONGO  |");
        
        for(int i = 0; i < numberOfInserts; i++){
            System.out.print("|");
            System.out.print(Padder.padLeft(String.valueOf(i), 4));
            System.out.print(" |");
            System.out.print(Padder.padLeft(String.valueOf(timeSql[i]), 8));
            System.out.print(" |");
            System.out.print(Padder.padLeft(String.valueOf(timeMongo[i]), 8));
            System.out.println(" |");
        }       
        System.out.println("---------------------------");

    }
    
}
