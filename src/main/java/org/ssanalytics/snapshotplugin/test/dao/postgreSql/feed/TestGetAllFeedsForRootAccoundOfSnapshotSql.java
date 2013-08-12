/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.postgreSql.feed;


import org.ssanalytics.snapshotplugin.test.dao.mongo.feed.*;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.feed.IFeed;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;

/**
 *
 * @author chw
 */
public class TestGetAllFeedsForRootAccoundOfSnapshotSql {
    
        public static void main(String args[]) throws Exception{           
            
            ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
            
            List<IFeed> list = DaoFactory.getFeedDAO().getAllFeedsForRootAccountOfSnapshotLatestVersion("dd");
            for(IFeed item : list){
                System.out.println(item.getMessage());
            } 
    } 
}
