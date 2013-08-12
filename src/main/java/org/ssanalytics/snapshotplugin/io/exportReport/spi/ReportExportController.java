/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.exportReport.spi;

import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.Content;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import org.gephi.io.exporter.spi.Exporter;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;
import org.ssanalytics.snapshotplugin.io.exportReport.pdf.PdfReportExporter;

/**
 *
 * @author christian
 */
public class ReportExportController {

    private Exporter exporter;
    private ISnapshotInfo snapshot;

    public ReportExportController(Exporter exporter, ISnapshotInfo snapshot) {
        this.exporter = exporter;
        this.snapshot = snapshot;
    }

    public void exportReport(File file, Content[][] contentList) throws IOException {
        exportReport(file, this.exporter, contentList);
    }

    public void exportReport(File file, Exporter exporter, Content[][] contentList) throws IOException {
        ((ReportExporter) exporter).setSnapshot(snapshot);
        ((ReportExporter) exporter).setContentList(contentList);
        if (exporter.getWorkspace() == null) {
            ProjectController projectController = Lookup.getDefault().lookup(ProjectController.class);
            Workspace workspace = projectController.getCurrentWorkspace();
            exporter.setWorkspace(workspace);
        }
        if (exporter instanceof PdfReportExporter) {
            OutputStream stream = new FileOutputStream(file);
            ((PdfReportExporter) exporter).setOutputStream(stream);
            try {
                exporter.execute();
            } catch (Exception ex) {
                try {
                    stream.flush();
                    stream.close();
                } catch (IOException exe) {
                }
                if (ex instanceof RuntimeException) {
                    throw (RuntimeException) ex;
                }
                throw new RuntimeException(ex);
            }
            try {
                stream.flush();
                stream.close();
            } catch (IOException ex) {
            }
        }
    }

    public ReportExportController(Exporter exporter) {
        this.exporter = (ReportExporter) exporter;
    }

    /**
     * @param snapshot the snapshot to set
     */
    public void setSnapshot(ISnapshotInfo snapshot) {
        this.snapshot = snapshot;
    }
    
    public FileFilter getExporterFileFilter() {
        return ((ReportExporter) this.exporter).getFileFilter();
    }
    
    public String getExporterFileEnding() {
        return ((ReportExporter) this.exporter).getFileEnding();
    }
}
