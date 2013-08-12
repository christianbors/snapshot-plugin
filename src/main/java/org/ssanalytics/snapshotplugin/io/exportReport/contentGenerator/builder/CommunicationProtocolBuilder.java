/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.exportReport.contentGenerator.builder;

import org.ssanalytics.snapshotplugin.io.exportReport.contentGenerator.CommunicationProtocol;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ReportContentBuilder;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ReportContent;

/**
 *
 * @author christian
 */
public class CommunicationProtocolBuilder implements ReportContentBuilder {

    @Override
    public String getName() {
        return "Communication Protocol Document Generator";
    }

    @Override
    public Class<? extends ReportContent> getExportClass() {
        return CommunicationProtocol.class;
    }
    
}
