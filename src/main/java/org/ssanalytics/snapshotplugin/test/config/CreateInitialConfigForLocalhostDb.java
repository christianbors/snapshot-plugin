/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.Configuration;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.DbConfiguration;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.DbMode;

/**
 *
 * @author chw
 */
public class CreateInitialConfigForLocalhostDb {
    
    
    public static DbConfiguration getMongoDbConfigLocalHost(){
         DbConfiguration dbConfigMongo = new DbConfiguration(new JSONObject());
        
        dbConfigMongo.setConfigurationName("test@localhost:18966 [MongoDB]");
        dbConfigMongo.setDbMode(DbMode.MONGODB);
        dbConfigMongo.setDbName("test");
        dbConfigMongo.setHost("127.0.0.1");
        dbConfigMongo.setPort("18966");
        dbConfigMongo.setPwd("");
        dbConfigMongo.setUser("");
        
        return dbConfigMongo;
    }
    
    public static DbConfiguration getPostgreConfigLocalHost(){
        
        DbConfiguration dbConfigSql = new DbConfiguration(new JSONObject());               
        
        dbConfigSql.setConfigurationName("ssdb@localhost:6565 [PostgreSql]");
        dbConfigSql.setDbMode(DbMode.POSTGRESQL);
        dbConfigSql.setDbName("ssdb");
        dbConfigSql.setHost("127.0.0.1");
        dbConfigSql.setPort("6565");
        dbConfigSql.setPwd("HarOyWetryn6");
        dbConfigSql.setUser("postgres");
        
        return dbConfigSql;
    }
    
    public static void main(String args[]) throws IOException{
        
        DbConfiguration dbConfigMongo =  getMongoDbConfigLocalHost();       
        
        List<DbConfiguration> conflist = new ArrayList<>(2);
        conflist.add(dbConfigMongo);
        

        DbConfiguration dbConfigSql = getPostgreConfigLocalHost();
        
        conflist.add(dbConfigSql);        
        
        Configuration config = new Configuration(new JSONObject());
        
        config.setActiveDbConfiguration("ssdb@localhost:6565 [PostgreSql]");
        config.setDbConfigurations(conflist);
        
        config.setOutputLevel(Configuration.OUTPUT_LEVEL_INFO);
        
        System.out.println(config.getActiveDbConfiguration().toString());
        
        ConfigurationFileManager.saveConfigToFile(config);      
        
        System.out.println(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getConfigurationName());
    }    
}
