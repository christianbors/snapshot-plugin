/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.exportReport.spi.content;

import com.itextpdf.text.Font;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ContentGenerator;

/**
 *
 * @author christian
 */
public class TextContent implements Content {

    private String[] listText;
    
    public TextContent(String text) {
        listText = new String[1];
        listText[0] = text;
    }
    
    public TextContent(String[] listText) {
        this.listText = listText;
    }

    public String[] getListText() {
        return listText;
    }

//    @Override
//    public Class<? extends Content> getContentClass() {
//        return TextContent.class;
//    }
    
    @Override
    public void sendContent(ContentGenerator generator) {
        generator.generateContent(this);
    }

}
