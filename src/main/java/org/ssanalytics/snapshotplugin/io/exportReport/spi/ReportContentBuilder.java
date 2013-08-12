/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.exportReport.spi;

/**
 *
 * @author christian
 */
public interface ReportContentBuilder {
        
    public String getName();

    public Class<? extends ReportContent> getExportClass();
    
}
