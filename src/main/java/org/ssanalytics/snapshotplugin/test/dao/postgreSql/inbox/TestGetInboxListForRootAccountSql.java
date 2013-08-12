/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.postgreSql.inbox;

import org.ssanalytics.snapshotplugin.test.dao.mongo.inbox.*;
import org.ssanalytics.snapshotplugin.test.dao.mongo.interest.*;
import java.util.List;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.InterestDAOMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.inbox.IInbox;

/**
 *
 * @author chw
 */
public class TestGetInboxListForRootAccountSql {
    
    public static void main(String args[]) throws Exception{
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        
        List<IInbox> list = DaoFactory.getInboxDAO().getInboxListForRootAccountOfSnapshotLatestVersion("dd");
        
        System.out.println(list.size());
        
        for(IInbox inbox : list){
        
            System.out.println(inbox.getMessage());
            System.out.println(inbox.getTo().getToDataList().size());
            System.out.println(inbox.getFrom().getName());
            System.out.println();
        }   
    } 
}
