/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.statistics.builder;

import org.gephi.statistics.spi.Statistics;
import org.gephi.statistics.spi.StatisticsBuilder;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ReportContentBuilder;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ReportContent;
import org.ssanalytics.snapshotplugin.io.statistics.AverageCommunication;

/**
 *
 * @author christian
 */
@ServiceProvider(service=StatisticsBuilder.class)
public class AverageCommunicationBuilder implements StatisticsBuilder, ReportContentBuilder {

    @Override
    public String getName() {
        return NbBundle.getMessage(AverageCommunicationBuilder.class, "AverageCommunication.name");
    }

    @Override
    public Statistics getStatistics() {
        return new AverageCommunication();
    }

    @Override
    public Class<? extends Statistics> getStatisticsClass() {
        return AverageCommunication.class;
    }

    @Override
    public Class<? extends ReportContent> getExportClass() {
        return AverageCommunication.class;
    }
    
}
