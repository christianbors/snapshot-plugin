/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.exportReport.spi;

import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.Content;
import javax.swing.JPanel;
import org.openide.util.NbBundle;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;

/**
 *
 * @author christian
 */
public interface ExportContentUI {

    public static final String SNAPSHOT_INFO = NbBundle.getMessage(ReportContent.class, "ExportContentUI.category.snapshotInfo");
    public static final String GRAPH_VIS = NbBundle.getMessage(ReportContent.class, "ExportContentUI.category.graphVis");
    public static final String COMMUNICATION_PROTOCOLS = NbBundle.getMessage(ReportContent.class, "ExportContentUI.category.communicationProtocols");
    public static final String STATISTIC_EVAL = NbBundle.getMessage(ReportContent.class, "ExportContentUI.category.statisticEval");
    public static final String STATISTIC_CHARTS = NbBundle.getMessage(ReportContent.class, "ExportContentUI.category.statisticCharts");

    public JPanel getSettingsPanel(ISnapshotInfo snapshot);
    
    public void setup(ReportContent content, ISnapshotInfo snapshot);
    
    public void generateContent();
    
    public void unsetup();
    
    public Class<? extends ReportContent> getExportContentClass();
    
    public String getDisplayName();
    
    public String getShortDescription();
    
    public Content[] getContent();
    
    public String getCategory();
    
    public int getPosition();
    
    public void setSnapshotProperty(ISnapshotInfo snapshot);
}
