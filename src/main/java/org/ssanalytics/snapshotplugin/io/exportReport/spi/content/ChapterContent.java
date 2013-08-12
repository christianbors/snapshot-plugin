/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.exportReport.spi.content;

import org.ssanalytics.snapshotplugin.io.exportReport.spi.ContentGenerator;

/**
 *
 * @author christian
 */
public class ChapterContent implements Content {

    private String chapter;
    
    public ChapterContent(String chapter) {
        this.chapter = chapter;
    }
    
    public String getChapter() {
        return chapter;
    }

//    @Override
//    public Class<? extends Content> getContentClass() {
//        return ChapterContent.class;
//    }

    @Override
    public void sendContent(ContentGenerator generator) {
        generator.generateContent(this);
    }
}
