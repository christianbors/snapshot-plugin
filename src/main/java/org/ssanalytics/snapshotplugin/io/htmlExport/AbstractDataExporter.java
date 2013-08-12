/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.htmlExport;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 *
 * @author christian
 */
public abstract class AbstractDataExporter {

    protected String templatePath = "./src/main/html/templates/";
    protected Configuration config;
    protected Template template;

    public AbstractDataExporter() {
        config = new Configuration();
        this.template = null;
    }

    public void exportDataToWriter(OutputStreamWriter outWriter) throws TemplateException, IOException {
        if (this.template == null) {
            this.template = getTemplate();
        }

        SimpleHash data = getDataForTemplate();

        if (outWriter == null) {
            outWriter = new OutputStreamWriter(System.out);
        }

        this.template.process(data, outWriter);

        outWriter.flush();
        outWriter.close();
    }

    public void exportDataToFile(String filename, boolean append) throws FileNotFoundException, TemplateException, IOException {
        exportDataToWriter(new OutputStreamWriter(new FileOutputStream(filename, append)));
    }

    protected abstract Template getTemplate();

    protected abstract SimpleHash getDataForTemplate();
}
