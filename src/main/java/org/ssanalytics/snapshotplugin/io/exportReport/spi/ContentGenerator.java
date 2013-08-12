/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.exportReport.spi;

import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.ChapterContent;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.ImageContent;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.TableContent;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.TextContent;

/**
 *
 * @author christian
 */
public interface ContentGenerator {

    public void generateContent(ChapterContent content);

    public void generateContent(ImageContent content);

    public void generateContent(TableContent content);

    public void generateContent(TextContent content);
}
