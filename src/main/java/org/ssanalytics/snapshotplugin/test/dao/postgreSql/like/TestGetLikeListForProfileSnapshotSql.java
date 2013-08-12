/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.postgreSql.like;

import org.ssanalytics.snapshotplugin.test.dao.mongo.like.*;
import org.ssanalytics.snapshotplugin.test.dao.mongo.activity.*;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.ActivityDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.EventDAOMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.activity.IActivity;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.event.IEvent;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.likes.ILike;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;


/**
 *
 * @author chw
 */
public class TestGetLikeListForProfileSnapshotSql {
    
    public static void main(String args[]) throws Exception{
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        
        List<ILike> list = DaoFactory.getLikeDAO().getLikeListForProfileInSnapshotLatestVersion("100000123807828", "dd");
        
        System.out.println(list.size());
        
        for(ILike l : list){
        
            System.out.println("Like name: " + l.getName() + "   id: " + l.getId());
        }        
    }   
    
}
