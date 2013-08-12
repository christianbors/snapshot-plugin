/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.mongo.like;


import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;

/**
 *
 * @author chw
 */
public class TestEvidenceCountLikeMongo {
    
    
    public static void main(String args[]) throws Exception{
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        
        Map<CategoryItem, Integer> map = DaoFactory.getLikeDAO().getEvidenceCountForSnapshotLatestVersion("");
        
        System.out.println(map.size());
        
        for(CategoryItem ci : map.keySet()){
        
            Integer i = map.get(ci);
            System.out.println("Like name: " + ci.getName() + "   count: " + i.toString());
        }        
    }    
}
