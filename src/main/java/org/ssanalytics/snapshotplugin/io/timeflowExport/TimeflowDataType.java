/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.timeflowExport;

/**
 *
 * @author Robert
 */
public enum TimeflowDataType {
    
    Text,
    DateTime{
        @Override
        public String toString(){
            return "Date/Time";
        }
    };
}
