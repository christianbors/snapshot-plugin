/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.exportReport.contentGenerator;

import javax.swing.JPanel;
import org.openide.util.lookup.ServiceProvider;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;
import org.ssanalytics.snapshotplugin.io.exportReport.contentGenerator.ExpandedArchiveExport;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ExportContentUI;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ReportContent;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.Content;

/**
 *
 * @author Christian
 */
@ServiceProvider(service = ExportContentUI.class)
public class ExpandedArchiveExportUI implements ExportContentUI {

    private ExpandedArchiveExportPanel panel;
    private ExpandedArchiveExport export;

    @Override
    public JPanel getSettingsPanel(ISnapshotInfo snapshot) {
        this.panel = new ExpandedArchiveExportPanel();
        return panel;
    }

    @Override
    public void setup(ReportContent content, ISnapshotInfo snapshot) {
        this.export = (ExpandedArchiveExport) content;
    }

    @Override
    public void generateContent() {
        export.setArchiveFilePath(panel.getFilePath());
        export.setNameExport(panel.getChckbxNameSelected());
        export.setAddressExport(panel.getChckbxAddressSelected());
        export.generate(null);
    }

    @Override
    public void unsetup() {
        panel = null;
        export = null;
    }

    @Override
    public Class<? extends ReportContent> getExportContentClass() {
        return ExpandedArchiveExport.class;
    }

    @Override
    public String getDisplayName() {
        return "Communication Protocol Document Generator";
    }

    @Override
    public String getShortDescription() {
        return "Export properties which are stored in a separate archive called the Expanded Archive, this is provided directly by Facebook and can be downloaded for every user. Export possibilities are numerous, for the moment the following are available.";
    }

    @Override
    public Content[] getContent() {
        return export.getContent();
    }

    @Override
    public String getCategory() {
        return ExportContentUI.SNAPSHOT_INFO;
    }

    @Override
    public int getPosition() {
        return 200;
    }

    @Override
    public void setSnapshotProperty(ISnapshotInfo snapshot) {
    }
}
