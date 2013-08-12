/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.postgreSql.activity;

import org.ssanalytics.snapshotplugin.test.dao.mongo.activity.*;
import org.ssanalytics.snapshotplugin.test.dao.mongo.book.*;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.ActivityDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.BookDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.BookDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.MovieDAOSql;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;

/**
 *
 * @author chw
 */
public class TestGetEvidenceCountTimeSpanActivitySql {
    
    
    public static void main(String args[]) throws Exception{
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        
        long twoYears = 1000;
        twoYears *= 60;
        twoYears *= 60;
        twoYears *= 24;
        twoYears *= 365;
        twoYears *= 2;
        
        long twoYearAgo = System.currentTimeMillis() - twoYears;
        
        Map<CategoryItem, Integer> map = DaoFactory.getActivityDAO().getEvidenceCountForSnapshotLatestVersion("dd", twoYears, System.currentTimeMillis());
        
        System.out.println(map.size());

        
        for(CategoryItem ci : map.keySet()){
        
            Integer i = map.get(ci);
            System.out.println("Activity name: " + ci.getName() + "   count: " + i.toString());
        }        
    }    
}
