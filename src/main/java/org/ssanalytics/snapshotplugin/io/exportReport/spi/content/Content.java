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
public interface Content {
        
    public void sendContent(ContentGenerator generator);
        
//    public Class<? extends Content> getContentClass();
    
}
