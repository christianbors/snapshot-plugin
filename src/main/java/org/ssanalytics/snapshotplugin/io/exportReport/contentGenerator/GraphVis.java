/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.exportReport.contentGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.io.exporter.api.ExportController;
import org.gephi.io.exporter.preview.PNGExporter;
import org.gephi.project.api.Workspace;
import org.gephi.project.api.WorkspaceInformation;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.Content;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.ImageContent;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ReportContent;

/**
 *
 * @author christian
 */
@ServiceProvider(service = ReportContent.class)
public class GraphVis implements ReportContent {

    private Workspace[] workspaces;
    private Map<Workspace, String> graphPathMap;

    @Override
    public void generate(ISnapshotInfo snapshot) {
        ExportController ec = Lookup.getDefault().lookup(ExportController.class);

        PNGExporter exporter = (PNGExporter) ec.getExporter("png");
        graphPathMap = new LinkedHashMap<>();

        for (Workspace workspace : workspaces) {

            String filename;
            if (workspace.getLookup().lookup(WorkspaceInformation.class).getSource().equals("")) {
                filename = "/tmp/" + workspace.getLookup().lookup(WorkspaceInformation.class).getName().toLowerCase().replace(' ', '_') + ".png";
            } else {
                filename = "/tmp/" + workspace.getLookup().lookup(WorkspaceInformation.class).getSource().toLowerCase().replace(' ', '_') + ".png";
            }
            exporter.setHeight(960);
            exporter.setWidth(1280);
            exporter.setWorkspace(workspace);
            try {
                new File("/tmp").mkdir();
                FileOutputStream outStream = new FileOutputStream(filename);
                exporter.setOutputStream(outStream);
            } catch (FileNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
            exporter.execute();
            graphPathMap.put(workspace, filename);
        }
    }

    public void setWorkspaces(Workspace[] workspaces) {
        this.workspaces = workspaces;
    }

    @Override
    public Content[] getContent() {
        List<Content> content = new LinkedList();
        for (Map.Entry<Workspace, String> entry : graphPathMap.entrySet()) {
            GraphController gc = Lookup.getDefault().lookup(GraphController.class);
            GraphModel model = gc.getModel(entry.getKey());
            String caption = entry.getKey().getLookup().lookup(WorkspaceInformation.class).getSource()
                    + " Number of nodes:  " + model.getGraph().getNodeCount()
                    + " Number of edges: " + model.getGraph().getEdgeCount();
            content.add(new ImageContent(entry.getValue(), caption));
        }
        return content.toArray(new Content[0]);
    }
}
