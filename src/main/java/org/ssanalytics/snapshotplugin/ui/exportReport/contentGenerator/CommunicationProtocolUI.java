/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.exportReport.contentGenerator;

import javax.swing.JPanel;
import org.openide.util.lookup.ServiceProvider;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;
import org.ssanalytics.snapshotplugin.io.exportReport.contentGenerator.CommunicationProtocol;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ExportContentUI;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ReportContent;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.Content;

/**
 *
 * @author christian
 */
@ServiceProvider(service = ExportContentUI.class)
public class CommunicationProtocolUI implements ExportContentUI{

    private CommunicationProtocol commProtocol;
    private CommunicationProtocolPanel panel;
    private ISnapshotInfo snapshot;
    
    @Override
    public JPanel getSettingsPanel(ISnapshotInfo snapshot) {
        this.snapshot = snapshot;
        this.panel = new CommunicationProtocolPanel(this.snapshot);
        return panel;
    }

    @Override
    public void setup(ReportContent content, ISnapshotInfo snapshot) {
        this.commProtocol = (CommunicationProtocol) content;
        this.snapshot = snapshot;
    }

    @Override
    public void generateContent() {
        commProtocol.includePrivateCommunication(panel.getChckbxPrivateSelected());
        commProtocol.includeWallCommunication(panel.getChckbxWallSelected());
        commProtocol.generate(snapshot);
    }

    @Override
    public void unsetup() {
        if(panel != null) {
            commProtocol.includePrivateCommunication(panel.getChckbxPrivateSelected());
            commProtocol.includeWallCommunication(panel.getChckbxWallSelected());
        }
        commProtocol = null;
    }

    @Override
    public Class<? extends ReportContent> getExportContentClass() {
        return CommunicationProtocol.class;
    }

    @Override
    public String getDisplayName() {
        return "Communication Protocol Document Generator";
    }

    @Override
    public String getShortDescription() {
        return "Export communication protocols for different aspects of the snapshot, including private communication.";
    }

    @Override
    public Content[] getContent() {
        return commProtocol.getContent();
    }

    @Override
    public String getCategory() {
        return ExportContentUI.COMMUNICATION_PROTOCOLS;
    }

    @Override
    public int getPosition() {
        return 100;
    }

    @Override
    public void setSnapshotProperty(ISnapshotInfo snapshot) {
        panel.setSnapshot(snapshot);
    }
    
}
