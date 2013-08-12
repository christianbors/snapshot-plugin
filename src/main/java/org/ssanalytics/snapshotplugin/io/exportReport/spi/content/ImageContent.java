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
public class ImageContent implements Content{

    private String imagePath;
    private String caption;
    
    public ImageContent(String imagePath) {
        this(imagePath, "");
    }
    
    public ImageContent(String imagePath, String caption) {
        this.imagePath = imagePath;
        this.caption = caption;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getCaption() {
        return caption;
    }
    
//    @Override
//    public Class<? extends Content> getContentClass() {
//        return ImageContent.class;
//    }

    @Override
    public void sendContent(ContentGenerator generator) {
        generator.generateContent(this);
    }
    
}
