/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.mongo.outbox;

import org.ssanalytics.snapshotplugin.test.dao.mongo.inbox.*;
import org.ssanalytics.snapshotplugin.test.dao.mongo.interest.*;
import java.util.List;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.InterestDAOMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.inbox.IInbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.outbox.IOutbox;

/**
 *
 * @author chw
 */
public class TestGetOutboxListForRootAccountMongo {
    
    public static void main(String args[]) throws Exception{
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        
        List<IOutbox> list = DaoFactory.getOutboxDAO().getOutboxListForRootAccountOfSnapshotLatestVersion("dd");
        
        System.out.println(list.size());
        
        for(IOutbox outbox : list){
        
            System.out.println(outbox.getMessage());
            System.out.println(outbox.getComments().getDataList().size());
            System.out.println(outbox.getTo().getToDataList().size());
            System.out.println(outbox.getFrom().getName());
            System.out.println(outbox.getUpdated_time());
            System.out.println();
        }   
    } 
}
