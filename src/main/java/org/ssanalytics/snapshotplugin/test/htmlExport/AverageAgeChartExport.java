/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.htmlExport;

import freemarker.template.TemplateException;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.ssanalytics.snapshotplugin.test.export.AverageAgeChartExporter;

/**
 *
 * @author christian
 */
public class AverageAgeChartExport {
    
    public static void main(String args[]) throws FileNotFoundException, TemplateException, IOException {
        AverageAgeChartExporter export = new AverageAgeChartExporter("Bob");
        export.exportDataToFile("test.html", false);
    }
}
