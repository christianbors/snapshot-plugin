/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.postgreSql.television;

import org.ssanalytics.snapshotplugin.test.dao.mongo.television.*;
import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;

import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;

/**
 *
 * @author chw
 */
public class TestGetEvidenceCountTimeSpanTelevisionSql {
    
    
    public static void main(String args[]) throws Exception{
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        
        long oneYear = 1000;
        oneYear *= 60;
        oneYear *= 60;
        oneYear *= 24;
        oneYear *= 365;
        
        long oneYearAgo = System.currentTimeMillis() - oneYear;
        
        Map<CategoryItem, Integer> map = DaoFactory.getTelevisionDAO().getEvidenceCountForSnapshotLatestVersion("dd", oneYearAgo, System.currentTimeMillis());
        
        System.out.println(map.size());

        
        for(CategoryItem ci : map.keySet()){
        
            Integer i = map.get(ci);
            System.out.println("Television name: " + ci.getName() + "   count: " + i.toString());
        }        
    }    
}
