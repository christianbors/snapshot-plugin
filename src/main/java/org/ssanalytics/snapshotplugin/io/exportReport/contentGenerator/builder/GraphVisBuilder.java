/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.exportReport.contentGenerator.builder;

import org.ssanalytics.snapshotplugin.io.exportReport.contentGenerator.GraphVis;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ReportContent;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ReportContentBuilder;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ReportExporterBuilder;

/**
 *
 * @author christian
 */
public class GraphVisBuilder implements ReportContentBuilder {

    @Override
    public String getName() {
        return "Graph Visualization Document Generator";
    }

    @Override
    public Class<? extends ReportContent> getExportClass() {
        return GraphVis.class;
    }
    
}
