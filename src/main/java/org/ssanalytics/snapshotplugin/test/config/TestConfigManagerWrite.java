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
 * @author Robert
 */
public class TestConfigManagerWrite {
 
    
    public static void main(String args[]) throws IOException{
        ConfigurationFileManager manager = new ConfigurationFileManager();
        
        DbConfiguration dbConfig = new DbConfiguration(new JSONObject());
        
        dbConfig.setConfigurationName("Testconfiguration the very first one");
        dbConfig.setDbMode(DbMode.MONGODB);
        dbConfig.setDbName("test");
        dbConfig.setHost("127.0.0.1");
        dbConfig.setPort("18966");
        dbConfig.setPwd("");
        dbConfig.setUser("des is wurscht wos do steht.");
        
        List<DbConfiguration> conflist = new ArrayList<>(1);
        conflist.add(dbConfig);
        
        Configuration config = new Configuration(new JSONObject());
        
        config.setActiveDbConfiguration("Testconfiguration the very first one");
        config.setDbConfigurations(conflist);
        
        config.setOutputLevel(Configuration.OUTPUT_LEVEL_INFO);
        
        System.out.println(config.getActiveDbConfiguration().toString());
        
        manager.saveConfigToFile(config);
    }
}
