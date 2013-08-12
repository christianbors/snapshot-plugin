/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.performanceEvaluator.exportEvaluators.mySQL;

import org.ssanalytics.snapshotplugin.ui.performanceEvaluator.exportEvaluators.dao.*;
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
        
        //set DB Mode
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMySQL.json";
        ConfigurationFileManager.readConfigFromFile();
        
        
        //medium shot
        long time = 0 - System.currentTimeMillis();
        List<IPhoto> photoList = DaoFactory.getPhotoDAO().getPhotoListSnapshotLatestVersion("EVAL_MYSQL_SMALL");
        time += System.currentTimeMillis();
        System.out.println("Finished fetching photos from MuSQL/MEDIUM!");
        System.out.println("Fetched " + photoList.size() + " elements in " + time + " ms");                
        

        
        
        System.out.println("----------------------------------------");
        System.out.println("| EVALUATION: GET ALL PHOTOS           |");
        System.out.println("|--------------------------------------|");
        System.out.println("| TOTAL TIME: " + time + "    |");
        System.out.println("----------------------------------------");
    }
}
