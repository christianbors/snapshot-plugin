/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.statistics;

import javax.swing.JPanel;
import org.gephi.statistics.spi.Statistics;
import org.gephi.statistics.spi.StatisticsUI;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.Content;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ExportContentUI;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ReportContent;
import org.ssanalytics.snapshotplugin.io.statistics.AverageCommunication;

/**
 *
 * @author christian
 */
@ServiceProvider(service = StatisticsUI.class)
public class AverageCommunicationUI implements StatisticsUI, ExportContentUI {

    private AverageCommunicationPanel panel;
    private AverageCommunication avgCommunication;

    @Override
    public JPanel getSettingsPanel(ISnapshotInfo snapshot) {
        panel = new AverageCommunicationPanel(snapshot);
        return panel;
    }

    @Override
    public JPanel getSettingsPanel() {
        panel = new AverageCommunicationPanel(null);
        return panel;
    }

    @Override
    public void setup(Statistics statistics) {
        this.avgCommunication = (AverageCommunication) statistics;
        if (panel != null) {
            // initialization phase for panel properties
        }
    }

    @Override
    public void setup(ReportContent content, ISnapshotInfo snapshot) {
        this.avgCommunication = (AverageCommunication) content;
        this.avgCommunication.setSnapshot(snapshot);
    }

    @Override
    public void generateContent() {
        if (panel.getSelectedSnapshot() != null) {
            avgCommunication.generate(panel.getSelectedSnapshot());
        }
    }

    @Override
    public void unsetup() {
        if (panel != null) {
            avgCommunication.setSnapshot(panel.getSelectedSnapshot());
        }
        panel = null;
    }

    @Override
    public Class<? extends Statistics> getStatisticsClass() {
        return AverageCommunication.class;
    }

    @Override
    public String getValue() {
        return Float.toString(avgCommunication.getValue());
    }

    @Override
    public String getDisplayName() {
        return NbBundle.getMessage(getClass(), "AverageCommunicationUI.name");
    }

    @Override
    public String getShortDescription() {
        return NbBundle.getMessage(getClass(), "AverageCommunicationPanel.header.description");
    }

    @Override
    public String getCategory() {
        return ExportContentUI.STATISTIC_EVAL;
    }

    @Override
    public int getPosition() {
        return 1000;
    }

    @Override
    public Class<? extends ReportContent> getExportContentClass() {
        return AverageCommunication.class;
    }

    @Override
    public Content[] getContent() {
        return avgCommunication.getContent();
    }

    @Override
    public void setSnapshotProperty(ISnapshotInfo snapshot) {
        panel.setSelectedSnapshot(snapshot);
    }
}
