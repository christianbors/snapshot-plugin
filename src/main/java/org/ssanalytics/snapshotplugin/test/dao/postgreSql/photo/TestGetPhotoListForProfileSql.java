/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.postgreSql.photo;

import java.util.List;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhoto;

/**
 *
 * @author chw
 */
public class TestGetPhotoListForProfileSql {
    
        public static void main(String args[]) throws Exception{
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        
        List<IPhoto> list = DaoFactory.getPhotoDAO().getPhotoListForProfileInSnapshotLatestVersion("", "773956213");
        
        System.out.println(list.size());
        
        for(IPhoto photo : list){
            System.out.println("photo name: " + photo.getName());
            System.out.println("tags:       " + photo.getPhotoTags().getTagDataList().size());
        }
    }    
}
