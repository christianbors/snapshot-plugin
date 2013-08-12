/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.postgreSql.testConnection;

import org.ssanalytics.snapshotplugin.config.dynamicConfig.DbConfiguration;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.DbMode;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.ConnectionTestSql;

/**
 *
 * @author Robert
 */
public class TestConnectionTestSql {
    
    public static void main(String args[]){
        
        
        org.ssanalytics.snapshotplugin.config.dynamicConfig.DbConfiguration dbConf = new DbConfiguration();
        
        dbConf.setDbMode(DbMode.POSTGRESQL);
        dbConf.setConfigurationName("test@postgres");
        dbConf.setDbName("ssdb");
        dbConf.setHost("127.0.0.1");
        dbConf.setPort("6565");
        dbConf.setPwd("HarOyWetryn6");
        dbConf.setUser("postgres");
        
        //set db mode to mongo
        try{
            ConnectionTestSql test = new ConnectionTestSql();
            test.testNewConnection(dbConf);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
