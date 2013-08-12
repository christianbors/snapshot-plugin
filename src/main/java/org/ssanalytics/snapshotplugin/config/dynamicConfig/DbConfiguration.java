/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.config.dynamicConfig;

import org.json.simple.JSONObject;

/**
 *
 * @author Robert
 */
public class DbConfiguration extends AbstractJsonWrapper{
    
    
    public DbConfiguration(){
        super(new JSONObject());
    }
    
    public DbConfiguration(JSONObject job){
        super(job);
    }    
    
    public DbConfiguration(String configName, DbMode dbMode, String dbName, String host, String port, String user, String pwd){
        super(new JSONObject());
        
        this.setConfigurationName(configName);
        this.setDbMode(dbMode);
        this.setDbName(dbName);
        this.setHost(host);
        this.setPort(port);
        this.setUser(user);
        this.setPwd(pwd);        
    }
    
    public final JSONObject getConfigJson(){
        return this.job;
    }        
    
    public final String getDbName(){
        return this.getString("dbName");
    }
    
    public final void setDbName(String dbName){
        this.job.put("dbName", dbName);
    }
    
    public final String getHost(){
        return this.getString("host");
    }
    
    public final void setHost(String host){
        this.job.put("host", host);
    }
    
    public final String getPort(){
        return this.getString("port");
    }
    
    public final void setPort(String port){
        this.job.put("port", port);
    }
    
    public final String getUser(){
        return this.getString("user");
    }
    
    public final void setUser(String user){
        this.job.put("user", user);
    }
    
    public final String getPwd(){
        return this.getString("pwd");
    }
    
    public final void setPwd(String pwd){
        this.job.put("pwd", pwd);
    }
    
    public final String getConfigurationName(){
        return this.getString("configName");
    }
    
    public final void setConfigurationName(String configName){
        this.job.put("configName", configName);
    }    
    
    public final DbMode getDbMode(){
        String dbMode = this.getString("dbMode");
        //System.out.println(dbMode);
        switch(dbMode) {
            case "POSTGRESQL":
                return DbMode.POSTGRESQL;
            case "MONGODB": 
                return DbMode.MONGODB;
            case "MYSQL":
                return DbMode.MYSQL;
            default:
                throw new java.lang.IllegalArgumentException("Db Mode " + dbMode + " is not supported!");
        }
    }
    
    public final void setDbMode(DbMode mode){
        
        String newMode;
        switch(mode){
            case POSTGRESQL: newMode = "POSTGRESQL";
                break;
            case MYSQL: newMode = "MYSQL";
                break;
            case MONGODB: newMode = "MONGODB";
                break;
            default: throw new java.lang.IllegalArgumentException("Db Mode " + mode + " is very invalid!");
        }
        
        this.job.put("dbMode", newMode);        
    }
    
    @Override
    public String toString() {
        return this.getConfigurationName();
    }
}
