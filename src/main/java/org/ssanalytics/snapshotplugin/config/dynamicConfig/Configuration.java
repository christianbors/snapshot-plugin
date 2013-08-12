/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.config.dynamicConfig;

import java.util.LinkedList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractBaseDomainJsonImport;

/**
 *
 * @author Robert
 */
public class Configuration extends AbstractJsonWrapper {
    
    public static final int OUTPUT_LEVEL_NONE = 0;
    public static final int OUTPUT_LEVEL_ERRORS_ONLY = 1;
    public static final int OUTPUT_LEVEL_INFO = 2;
    public static final int OUTPUT_LEVEL_VERBOSE = 3;
    
    
    public Configuration(JSONObject job){
        super(job);
    }
    
    public String getJsonString(){
        return this.job.toJSONString();
    }
    
    public List<DbConfiguration> getDbConfigurations(){
        
        JSONArray configJar = this.getJar("dbConfigurations");
        
        List<DbConfiguration> configList = new LinkedList<>();
        
        if(configJar != null){
            for(Object o : configJar){
                configList.add(new DbConfiguration((JSONObject) o));            
            }
        }
        
        return configList;
    }
    
    public void setDbConfigurations(List<DbConfiguration> configList){
        
        JSONArray jarray = new JSONArray();
        
        if(configList != null){
            for(DbConfiguration dbc : configList){
                jarray.add(dbc.getConfigJson());
            }
        }
        
        this.job.put("dbConfigurations", jarray);
    }
    
    public DbConfiguration findDbConfiguration(String configurationName) {
        List<DbConfiguration> list = this.getDbConfigurations();
        for(DbConfiguration temp : list) {
            if(temp.getConfigurationName().equals(configurationName))
                return temp;
        }
        return null;
    }
    
    public int getOutputLevel(){
        return Integer.parseInt(this.getString("outputLevel"));
    }
    
    public void setOutputLevel(int outputLevel){
        this.job.put("outputLevel", String.valueOf(outputLevel));
    }
    
    public DbConfiguration getActiveDbConfiguration(){
        String confName = this.getString("activeDbConfig");
        List<DbConfiguration> configList = this.getDbConfigurations();
        
        for(DbConfiguration c : configList){
            if(c.getConfigurationName().equals(confName))
                return c;
        }
        
        return null;
    }
    
    public void setActiveDbConfiguration(String configName){
        this.job.put("activeDbConfig", configName);
    }
    
}
