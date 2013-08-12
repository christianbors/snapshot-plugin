/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.exportReport.pdf;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import org.openide.util.Exceptions;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ContentGenerator;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.ChapterContent;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.ImageContent;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.TableContent;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.TextContent;

/**
 *
 * @author christian
 */
public class PdfContentGenerator implements ContentGenerator {

    private Document document;
    private int chapterCount;
    private int imageCount;
    private int tableCount;

    public PdfContentGenerator(Document document) {
        this(document, 0);
    }

    public PdfContentGenerator(Document document, int chapterCount) {
        this.document = document;
        this.chapterCount = chapterCount;
        this.imageCount = 0;
        this.tableCount = 0;
    }

    @Override
    public void generateContent(ChapterContent content) {
        ++chapterCount;
        String chapter = content.getChapter();
        try {
            Chapter chapterCurrent = new Chapter(new Paragraph(chapter, PdfReportExporter.CH_TITLE), chapterCount);
            document.add(chapterCurrent);
        } catch (DocumentException ex) {
            Exceptions.printStackTrace(ex);
        }
        imageCount = 0;
        tableCount = 0;
    }

    @Override
    public void generateContent(ImageContent content) {
        try {
            String imagePath = content.getImagePath();
            String caption = content.getCaption();
            Image img = Image.getInstance(imagePath);
            img.scaleToFit(document.getPageSize().getWidth(), document.getPageSize().getHeight());
            document.add(img);
            String imgFooter = "Image " + imageCount;
            if (!caption.isEmpty()) {
                imgFooter += ": " + caption;
            }
            Paragraph parCaption = new Paragraph(imgFooter);
            parCaption.setAlignment(Element.ALIGN_CENTER);
            document.add(parCaption);
            document.add(new Paragraph(""));
        } catch (BadElementException ex) {
            Exceptions.printStackTrace(ex);
        } catch (MalformedURLException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (DocumentException ex) {
            Exceptions.printStackTrace(ex);
        }
        ++imageCount;
    }

    @Override
    public void generateContent(TableContent content) {
        PdfPTable table = new PdfPTable(2);
        if (!content.getTable().isEmpty()) {
            for (Map.Entry<String, String> entry : content.getTable().entrySet()) {
                table.addCell(entry.getKey());
                table.addCell(entry.getValue());
            }
        } else {
            for (String entry : content.getTableList()) {
                table.addCell(entry);
                table.addCell("");
            }
        }
        try {
            document.add(table);
        } catch (DocumentException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public void generateContent(TextContent content) {
        String[] textArray = (String[]) content.getListText();
        for (String text : textArray) {
            try {
                document.add(new Paragraph(text));
            } catch (DocumentException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

    }
}
