/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.performanceEvaluator.exportEvaluators.dao;

import java.util.List;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhoto;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.ui.performanceEvaluator.helpers.Padder;

/**
 *
 * @author chw
 */
public class EvaluateGetAllPhotos {
    
    public static void main(String args[]) throws Exception{
        
        //MongoDB
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        ConfigurationFileManager.readConfigFromFile();
        
        
        //small shot
        long time_mongo_small = 0 - System.currentTimeMillis();
        List<IPhoto> photoList = DaoFactory.getPhotoDAO().getPhotoListSnapshotLatestVersion("EVAL_SMALL");
        time_mongo_small += System.currentTimeMillis();
        System.out.println("Finished fetching photos from MongoDB/SMALL!");
        System.out.println("Fetched " + photoList.size() + " elements in " + time_mongo_small + " ms");
        
        //medium shot
        long time_mongo_medium = 0 - System.currentTimeMillis();
        photoList = DaoFactory.getPhotoDAO().getPhotoListSnapshotLatestVersion("EVAL_MEDIUM");
        time_mongo_medium += System.currentTimeMillis();
        System.out.println("Finished fetching photos from MongoDB/MEDIUM!");
        System.out.println("Fetched " + photoList.size() + " elements in " + time_mongo_medium + " ms");
        
        //large shot
        long time_mongo_large = 0 - System.currentTimeMillis();
        photoList = DaoFactory.getPhotoDAO().getPhotoListSnapshotLatestVersion("EVAL_LARGE");
        time_mongo_large += System.currentTimeMillis();
        System.out.println("Finished fetching photos from MongoDB/LARGE!");
        System.out.println("Fetched " + photoList.size() + " elements in " + time_mongo_large + " ms");
        
        
        //PostgreSQL
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        ConfigurationFileManager.readConfigFromFile();
        
        //small shot
        long time_postgre_small = 0 - System.currentTimeMillis();
        photoList = DaoFactory.getPhotoDAO().getPhotoListSnapshotLatestVersion("EVAL_SMALL");
        time_postgre_small += System.currentTimeMillis();
        System.out.println("Finished fetching photos from PostgreSQL/SMALL!");
        System.out.println("Fetched " + photoList.size() + " elements in " + time_postgre_small + " ms");
        
        //medium shot
        long time_postgre_medium = 0 - System.currentTimeMillis();
        photoList = DaoFactory.getPhotoDAO().getPhotoListSnapshotLatestVersion("EVAL_MEDIUM");
        time_postgre_medium += System.currentTimeMillis();
        System.out.println("Finished fetching photos from PostgreSQL/MEDIUM!");
        System.out.println("Fetched " + photoList.size() + " elements in " + time_postgre_medium + " ms");
        
        //large shot
        long time_postgre_large = 0 - System.currentTimeMillis();
        photoList = DaoFactory.getPhotoDAO().getPhotoListSnapshotLatestVersion("EVAL_LARGE");
        time_postgre_large += System.currentTimeMillis();
        System.out.println("Finished fetching photos from PostgreSQL/LARGE!");
        System.out.println("Fetched " + photoList.size() + " elements in " + time_postgre_large + " ms");
        
        
        //MySQL
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMySQL.json";
        ConfigurationFileManager.readConfigFromFile();
        
        //small shot
        long time_mysql_small = 0 - System.currentTimeMillis();
        //photoList = DaoFactory.getPhotoDAO().getPhotoListSnapshotLatestVersion("EVAL_SMALL");
        time_mysql_small += System.currentTimeMillis();
        //System.out.println("Finished fetching photos from MySQL/SMALL!");
        //System.out.println("Fetched " + photoList.size() + " elements in " + time_mysql_small + " ms");
        
        //medium shot
        long time_mysql_medium = 0 - System.currentTimeMillis();
        //photoList = DaoFactory.getPhotoDAO().getPhotoListSnapshotLatestVersion("EVAL_SMALL");
        time_mysql_medium += System.currentTimeMillis();
        //System.out.println("Finished fetching photos from MySQL/MEDIUM!");
        //System.out.println("Fetched " + photoList.size() + " elements in " + time_mysql_medium + " ms");
        
        //large shot
        long time_mysql_large = 0 - System.currentTimeMillis();
        //photoList = DaoFactory.getPhotoDAO().getPhotoListSnapshotLatestVersion("EVAL_SMALL");
        time_mysql_large += System.currentTimeMillis();
        //System.out.println("Finished fetching photos from MySQL/LARGE!");
        //System.out.println("Fetched " + photoList.size() + " elements in " + time_mysql_large + " ms");        
        
        
        System.out.println("----------------------------------------");
        System.out.println("| EVALUATION: GET ALL PHOTOS           |");
        System.out.println("|--------------------------------------|");
        System.out.println("|  SHOT  |  MONGO  | POSTGRE |  MySQL  |");
        System.out.println("|  SMALL | " + Padder.padLeft(String.valueOf(time_mongo_small), 8) + "| " + Padder.padLeft(String.valueOf(time_postgre_small), 8) + "| " + Padder.padLeft(String.valueOf(time_mysql_small), 8) + "|");
        System.out.println("| MEDIUM | " + Padder.padLeft(String.valueOf(time_mongo_medium), 8) + "| " + Padder.padLeft(String.valueOf(time_postgre_medium), 8) + "| " + Padder.padLeft(String.valueOf(time_mysql_medium), 8) + "|");
        System.out.println("|  LARGE | " + Padder.padLeft(String.valueOf(time_mongo_large), 8) + "| " + Padder.padLeft(String.valueOf(time_postgre_large), 8) + "| " + Padder.padLeft(String.valueOf(time_mysql_large), 8) + "|");
        System.out.println("----------------------------------------");
    }
}
