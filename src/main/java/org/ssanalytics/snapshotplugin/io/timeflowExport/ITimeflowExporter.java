/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.timeflowExport;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.timeflowExport.dataCells.ITimeflowDataCell;

/**
 *
 * @author Robert
 */
public interface ITimeflowExporter {
    
    public void startTimeLineExport(List<TimeflowHeaderField> headerFields);
    public void addEntry(List<ITimeflowDataCell> row);
    public void finishExport(String filename) throws IOException;
    public void finishExport(File file) throws IOException;
    public void resetExport();
}
