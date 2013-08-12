/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.postgreSql.music;

import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;

import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;

/**
 *
 * @author chw
 */
public class TestEvidenceCountMusicSql {
    
    
    public static void main(String args[]) throws Exception{
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        
        Map<CategoryItem, Integer> map = DaoFactory.getMusicDAO().getEvidenceCountForSnapshotLatestVersion("");
        
        System.out.println(map.size());
        
        for(CategoryItem ci : map.keySet()){
        
            Integer i = map.get(ci);
            System.out.println("Music name: " + ci.getName() + "   count: " + i.toString());
        }        
    }    
}
