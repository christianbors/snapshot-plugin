/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.timeflowExport.dataCells;

import java.util.Date;

/**
 *
 * @author Robert
 */
public class TimeflowDataCellDateTime implements ITimeflowDataCell{

    private Long value;
    
    public TimeflowDataCellDateTime(Long value){
        this.value = value;
    }
    
    @Override
    public String getStringValue() {
        Date d = new Date(this.value);
        return (d.getMonth() + 1) + "/" + d.getDate() + "/" + (1900 + d.getYear() + " " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds());        
    }
    
}
