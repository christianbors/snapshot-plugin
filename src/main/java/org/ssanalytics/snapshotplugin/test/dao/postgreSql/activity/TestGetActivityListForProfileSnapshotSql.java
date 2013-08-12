/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.postgreSql.activity;

import java.util.List;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.activity.IActivity;

/**
 *
 * @author chw
 */
public class TestGetActivityListForProfileSnapshotSql {
    
        public static void main(String args[]) throws Exception{
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        
        List<IActivity> list = DaoFactory.getActivityDAO().getActivityListForProfileInSnapshotLatestVersion("100000123807828", "");
        
        System.out.println(list.size());
        
        for(IActivity a : list){
        
            System.out.println("Activity name: " + a.getName() + "   id: " + a.getId());
        }        
    }   
    
}
