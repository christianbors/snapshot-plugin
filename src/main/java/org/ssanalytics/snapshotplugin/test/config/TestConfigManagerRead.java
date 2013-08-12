/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.config;

import java.io.IOException;
import org.json.simple.parser.ParseException;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.Configuration;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;

/**
 *
 * @author Robert
 */
public class TestConfigManagerRead {
    
    public static void main(String args[]) throws IOException, ParseException{
        
        ConfigurationFileManager man = new ConfigurationFileManager();
        Configuration conf = man.readConfigFromFile();
        
        System.out.println("Output Level: " + conf.getOutputLevel());
        System.out.println("Active db conf: " + conf.getActiveDbConfiguration().getConfigurationName());
    }
    
}
