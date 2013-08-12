/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.exportReport.pdf;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.filechooser.FileFilter;
import org.gephi.io.exporter.spi.ByteExporter;
import org.gephi.preview.api.PDFTarget;
import org.gephi.project.api.Workspace;
import org.gephi.utils.longtask.spi.LongTask;
import org.gephi.utils.progress.Progress;
import org.gephi.utils.progress.ProgressTicket;
import org.openide.util.Exceptions;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.Content;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ReportExporter;

/**
 *
 * @author christian
 */
public class PdfReportExporter implements ByteExporter, ReportExporter, LongTask {

    private OutputStream stream;
    private boolean cancel = false;
    private Workspace workspace;
    private ISnapshotInfo snapshot;
    private IProfile profile;
    private PDFTarget target;
    private ProgressTicket progress;
    // document font settings
    public static Font CH_TITLE = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLDITALIC);
    public static Font SEC_TITLE = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD);
    public static Font SUB_SEC_TITLE = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
    public static Font NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 12);
    public static Font CAPTION = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);
    private Content[][] contentList;
    private Rectangle pageSize = PageSize.A4;

    public PdfReportExporter() {
    }

    @Override
    public boolean execute() {
        Progress.start(progress);

        Document document = new Document(pageSize);
        PdfWriter pdfWriter = null;
        try {
            pdfWriter = PdfWriter.getInstance(document, stream);
            pdfWriter.setPdfVersion(PdfWriter.PDF_VERSION_1_5);
            pdfWriter.setFullCompression();

        } catch (DocumentException ex) {
            Exceptions.printStackTrace(ex);
        }
        document.open();

        try {
            Paragraph title0 = new Paragraph();
            title0.add(new Paragraph(" "));
            title0.add(new Paragraph("Forensic Report", FontFactory.getFont(FontFactory.HELVETICA, 22, Font.BOLD)));
            document.add(title0);

            List<Element> elementList = new LinkedList<>();
            Paragraph intro = new Paragraph("In this section we will find general information about the snapshot owner.\n");
            intro.add("Snapshot name: " + snapshot.getValue()
                    + "\nSnapshot owner: " + profile.getName()
                    + "\nSnapshot Facebook id: " + snapshot.getRoot());
            elementList.add(intro);
            elementList.add(new Paragraph());

            Paragraph title1 = new Paragraph("Snapshot Information", CH_TITLE);
            Chapter chapterIntro = new Chapter(title1, 1);
            chapterIntro.addAll(Arrays.asList(elementList.toArray(new Element[0])));
            document.add(chapterIntro);

        } catch (DocumentException ex) {
            Exceptions.printStackTrace(ex);
        }

        PdfContentGenerator contentGenerator = new PdfContentGenerator(document);

        for (Content[] contents : contentList) {
            for (Content content : contents) {
                content.sendContent(contentGenerator);
            }
        }

        document.close();
        Progress.finish(progress);

        return !cancel;
    }

    @Override
    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    @Override
    public Workspace getWorkspace() {
        return workspace;
    }

    @Override
    public void setOutputStream(OutputStream stream) {
        this.stream = stream;
    }

    @Override
    public boolean cancel() {
        cancel = true;
        if (target instanceof LongTask) {
            ((LongTask) target).cancel();
        }
        return true;
    }

    @Override
    public void setProgressTicket(ProgressTicket progressTicket) {
        this.progress = progressTicket;
    }

    /**
     * @param snapshot the snapshot to set
     */
    @Override
    public void setSnapshot(ISnapshotInfo snapshot) {
        this.snapshot = snapshot;

        try {
            this.profile = DaoFactory.getProfileDAO().getProfileForAccountIdInSnapshotLatestVersion(snapshot.getRoot(), snapshot.getValue());
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public void setContentList(Content[][] contentList) {
        this.contentList = contentList;
    }

    @Override
    public FileFilter getFileFilter() {
        return new PdfFilter();
    }

    @Override
    public String getFileEnding() {
        return ".pdf";
    }

    class PdfFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            String filename = f.getName();

            if (f.isDirectory()) {
                return true;
            }

            return filename.endsWith(".pdf");
        }

        @Override
        public String getDescription() {
            return "*.pdf";
        }
    }
}
