/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.exportReport.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author christian
 */
public class PdfReportCreator {

    protected Document document;
    public static PdfReportCreator instance;
    protected PdfWriter writer = null;

    public PdfReportCreator() {
        document = new Document();
    }

    public String getName() {
        return "PDF Creator";
    }
    
    public static PdfReportCreator getInstance() {
        if (instance == null) {
            instance = new PdfReportCreator();
        }
        return instance;
    }

    public void createReport(String filename, Element elements[]) throws FileNotFoundException, DocumentException {
        document = new Document(PageSize.A4);
        if (writer == null) {
            writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
        }
        document.open();

        for (Element e : elements) {
            document.add(e);
        }

        document.close();
    }

    public void createFullReport(String filename, String snapshotName, String profileId) throws Exception {
        writer = PdfWriter.getInstance(document, new FileOutputStream(filename));

//        List<Element> elementList = new LinkedList<>();
//        elementList.add(PhotoStatistics.getInstance().getTaggedPhotoCount(snapshotName));
//        elementList.add(PostStatistics.getInstance().getPostCountPerWeek(snapshotName));
//        if (profileId != null) {
//            elementList.add(PostStatistics.getInstance().getPostLikeCount(snapshotName, filename));
//        }
//        elementList.add(Image.getInstance(writer, EvidenceStatistics.getInstance().getEvidenceStatisticsForLastYear(snapshotName).createBufferedImage(800, 600), 1.0f));
//
//        createReport(filename, (Element[]) elementList.toArray());
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
