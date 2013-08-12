/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.timeflowExport;

/**
 *
 * @author Robert
 */
public class TimeflowHeaderField {
    
    private String name;
    private TimeflowDataType dataType;
    private TimeflowAlias alias;    
    
    public TimeflowHeaderField(String name, TimeflowDataType dataType, TimeflowAlias alias){
        this.name = name;
        this.dataType = dataType;
        this.alias = alias;
    }
    
    public String getName(){
        return this.name;
    }
    
    public TimeflowDataType getDataType(){
        return this.dataType;
    }
    
    public TimeflowAlias getAlias(){
        return this.alias;
    }
}
