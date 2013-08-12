/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.mongo.interest;

import org.ssanalytics.snapshotplugin.test.dao.mongo.activity.*;
import java.util.List;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.interest.IInterest;



/**
 *
 * @author chw
 */
public class TestGetInterestListForProfileSnapshotMongo {
    
    public static void main(String args[]) throws Exception{
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        
        List<IInterest> list = DaoFactory.getInterestDAO().getInterestListForProfileInSnapshotLatestVersion("100003497662754", "dd");
        
        System.out.println(list.size());
        
        for(IInterest a : list){
        
            System.out.println("Interest name: " + a.getName() + "   id: " + a.getId());
        }        
    }   
    
}
