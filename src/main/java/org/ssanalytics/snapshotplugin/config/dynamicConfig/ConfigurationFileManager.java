/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.config.dynamicConfig;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Robert
 */
public class ConfigurationFileManager {
    
    
    private static Configuration configInstance = null;
    
    public static String CONFIG_FILE_NAME = "../../config.json";
//    public static String CONFIG_FILE_NAME = "localPostgre.json";
    
    public static void saveConfigToFile(Configuration toSave) throws IOException{
        File f = new File(CONFIG_FILE_NAME);
        FileWriter fw = new FileWriter(f);
        
        fw.write(toSave.getJsonString());
        fw.close();
    }
    
    public static Configuration readConfigFromFile(String fileName) throws IOException, ParseException {
        CONFIG_FILE_NAME = fileName;
        return readConfigFromFile();
    }
    
    public static Configuration readConfigFromFile() throws IOException, ParseException{
        
        FileReader fr = new FileReader(new File(CONFIG_FILE_NAME));
                
        JSONParser jp = new JSONParser();
        
        configInstance = new Configuration((JSONObject) jp.parse(fr));  
        
        fr.close();       
        
        return configInstance;
    }
    
    public static Configuration getCurrentConfig(){
        
        if(configInstance == null){
            try{
                readConfigFromFile();
            }
            catch(IOException | ParseException ex){
                System.err.println("Could not read config from file: " + CONFIG_FILE_NAME);
                System.err.println("Absolute filename trying to read was: " + new File(CONFIG_FILE_NAME).getAbsolutePath());
                System.err.println("--- RUN CreateInitialConfigForLocalhostDb.java TO CREATE AN INITIAL CONFIG FILE ---");
                ex.printStackTrace();
            }
        }        
        
        return configInstance;
    }   
    
}
