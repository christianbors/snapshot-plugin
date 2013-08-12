/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.exportReport.spi;

import javax.swing.filechooser.FileFilter;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.Content;
import org.gephi.io.exporter.spi.Exporter;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;

/**
 *
 * @author christian
 */
public interface ReportExporter extends Exporter {
    
    public void setSnapshot(ISnapshotInfo snapshot);
    
    public void setContentList(Content[][] contentList);
    
    public FileFilter getFileFilter();
    
    public String getFileEnding();
}
