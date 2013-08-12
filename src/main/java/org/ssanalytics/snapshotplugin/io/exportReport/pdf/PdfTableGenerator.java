/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.exportReport.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.IOException;

/**
 *
 * @author christian
 */
public class PdfTableGenerator {

    public static int CAPTION_COUNT = 0;

    public static Element newTablePlusCaption(String elements[][], String caption, Float tableWidth, float[] columnWidths) throws DocumentException, IOException {
        Paragraph returnValue = new Paragraph();
        returnValue.add(newTable(elements, tableWidth, columnWidths));


        if (caption != null) {
            Font f = new Font(BaseFont.createFont(BaseFont.COURIER_BOLD, BaseFont.WINANSI, BaseFont.EMBEDDED), 8);

            Paragraph p = new Paragraph("Caption " + CAPTION_COUNT + ": " + caption, f);
            p.setSpacingBefore(0);
            p.setAlignment(PdfPTable.ALIGN_CENTER);

            returnValue.add(p);
        } else {
            returnValue.add("\n");
        }
        return returnValue;
    }

    private static PdfPTable newTable(String elements[][], Float tableWidth, float[] columnWidths) throws DocumentException {

        if ((elements == null) || (elements.length == 0)) {
            return null;
        }

        CAPTION_COUNT++;

        PdfPTable table = new PdfPTable(elements[0].length);
        for (String elementRow[] : elements) {
            for (String element : elementRow) {
                table.addCell(element);
            }
        }

        if (tableWidth != null) {
            table.setWidthPercentage(tableWidth);
        }

        if ((columnWidths != null) && (columnWidths.length == elements[0].length)) {
            table.setWidths(columnWidths);
        }

        return table;
    }
}
