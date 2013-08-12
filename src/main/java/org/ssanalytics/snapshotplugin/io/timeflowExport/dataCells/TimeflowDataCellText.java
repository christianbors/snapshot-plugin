/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.timeflowExport.dataCells;

/**
 *
 * @author Robert
 */
public class TimeflowDataCellText implements ITimeflowDataCell{

    private String value;
    
    public TimeflowDataCellText(String value){
        this.value = value;
    }
    
    @Override
    public String getStringValue() {
        return this.value;
    }
    
}
