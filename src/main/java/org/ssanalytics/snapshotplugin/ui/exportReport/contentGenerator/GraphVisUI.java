/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.exportReport.contentGenerator;

import javax.swing.JPanel;
import org.gephi.project.api.Workspace;
import org.gephi.project.api.WorkspaceInformation;
import org.openide.util.lookup.ServiceProvider;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;
import org.ssanalytics.snapshotplugin.io.exportReport.contentGenerator.GraphVis;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.Content;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ReportContent;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ExportContentUI;

/**
 *
 * @author christian
 */
@ServiceProvider(service = ExportContentUI.class)
public class GraphVisUI implements ExportContentUI {

    private GraphVis graphVis;
    private ISnapshotInfo snapshot;
    private Workspace workspace;
    private GraphVisPanel panel;

    @Override
    public JPanel getSettingsPanel(ISnapshotInfo snapshot) {
        this.panel = new GraphVisPanel();
        return panel;
    }

    @Override
    public void setup(ReportContent content, ISnapshotInfo snapshot) {
        this.graphVis = (GraphVis) content;
        this.snapshot = snapshot;
    }

    @Override
    public void generateContent() {
        Workspace[] workspaceArray = panel.getSelectedWorkspaces();
        if (workspaceArray.length > 0) {
            graphVis.setWorkspaces(workspaceArray);
            graphVis.generate(snapshot);
        }
    }

    @Override
    public void unsetup() {
        // teardown
        graphVis = null;
    }

    @Override
    public Class<? extends ReportContent> getExportContentClass() {
        return GraphVis.class;
    }

    @Override
    public String getDisplayName() {
        if (workspace != null) {
            return workspace.getLookup().lookup(WorkspaceInformation.class).getName() + " Document Generator";
        }
        return "Graph Visualization Document Generator";
    }

    @Override
    public String getShortDescription() {
        return "Graph Visualization Document Generator";
    }

    @Override
    public Content[] getContent() {
        return graphVis.getContent();
    }

    @Override
    public String getCategory() {
        return ExportContentUI.GRAPH_VIS;
    }

    @Override
    public int getPosition() {
        return 100;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    @Override
    public void setSnapshotProperty(ISnapshotInfo snapshot) {
    }
}
