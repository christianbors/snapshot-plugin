/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.exportReport.contentGenerator.builder;

import org.ssanalytics.snapshotplugin.io.exportReport.contentGenerator.ExpandedArchiveExport;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ReportContentBuilder;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ReportContent;

/**
 *
 * @author Christian
 */
public class ExpandedArchiveExportBuilder implements ReportContentBuilder {

    @Override
    public String getName() {
        return "Expanded Archive Exporter";
    }

    @Override
    public Class<? extends ReportContent> getExportClass() {
        return ExpandedArchiveExport.class;
    }
    
}
