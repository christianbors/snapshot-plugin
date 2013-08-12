/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.mongo.outbox;

import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;

/**
 *
 * @author chw
 */
public class TestGetWordListFromOutboxMongo {
    
    public static void main(String args[]) throws Exception{
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        
        Map<String, Integer> map = DaoFactory.getOutboxDAO().getWordCountForOutboxOfRootAccountSnapshotLatestVersion("TEST");
        
        System.out.println(map.size());
        
        for(String s : map.keySet()){
        
            Integer i = map.get(s);
            System.out.println("Word: " + s + "   count: " + i.toString());
        }        
    }   
    
}
