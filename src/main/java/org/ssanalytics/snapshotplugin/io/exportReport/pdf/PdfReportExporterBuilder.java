/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.exportReport.pdf;

import org.gephi.io.exporter.api.FileType;
import org.gephi.io.exporter.spi.Exporter;
import org.gephi.io.exporter.spi.FileExporterBuilder;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ReportExporterBuilder;

/**
 *
 * @author christian
 */
@ServiceProvider(service = ReportExporterBuilder.class)
public class PdfReportExporterBuilder implements ReportExporterBuilder {

    @Override
    public Exporter buildExporter() {
        return new PdfReportExporter();
    }

    @Override
    public FileType[] getFileTypes() {
        FileType ft = new FileType(".pdf", NbBundle.getMessage(PdfReportExporterBuilder.class, "fileType_PDF_Name"));
        return new FileType[]{ft};
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(PdfReportExporterBuilder.class, "ReportExporterPDF_name");
    }
    
    @Override
    public String toString() {
        return NbBundle.getMessage(PdfReportExporterBuilder.class, "ReportExporterPDF_name");
    }
}
