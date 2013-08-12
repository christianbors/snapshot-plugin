/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.mongo.testConnection;

import org.ssanalytics.snapshotplugin.config.dynamicConfig.DbConfiguration;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.DbMode;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.ConnectionTestMongo;

/**
 *
 * @author Robert
 */
public class TestConnectionTestMongo {
    
    
    public static void main(String args[]){
        
        org.ssanalytics.snapshotplugin.config.dynamicConfig.DbConfiguration dbConf = new DbConfiguration();
        
        dbConf.setDbMode(DbMode.MONGODB);
        dbConf.setConfigurationName("test@mongo");
        dbConf.setDbName("test");
        dbConf.setHost("127.0.0.1");
        dbConf.setPort("18966");

        try{
            ConnectionTestMongo test = new ConnectionTestMongo();
            test.testNewConnection(dbConf);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
