/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.timeflowExporter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.timeflowExport.dataCells.ITimeflowDataCell;
import org.ssanalytics.snapshotplugin.io.timeflowExport.TimeflowAlias;
import org.ssanalytics.snapshotplugin.io.timeflowExport.dataCells.TimeflowDataCellDateTime;
import org.ssanalytics.snapshotplugin.io.timeflowExport.dataCells.TimeflowDataCellText;
import org.ssanalytics.snapshotplugin.io.timeflowExport.TimeflowDataType;
import org.ssanalytics.snapshotplugin.io.timeflowExport.TimeflowExporter;
import org.ssanalytics.snapshotplugin.io.timeflowExport.TimeflowHeaderField;

/**
 *
 * @author Robert
 */
public class TestTimeflowExporter {
    
    public static void main(String args[]){
        
        TimeflowExporter tfex = new TimeflowExporter();
        
        List<TimeflowHeaderField> headers = new LinkedList<>();
        
        headers.add(new TimeflowHeaderField("start time", TimeflowDataType.DateTime, TimeflowAlias.TIMEFLOW_START));
        headers.add(new TimeflowHeaderField("type", TimeflowDataType.Text, TimeflowAlias.TIMEFLOW_TRACK));
        headers.add(new TimeflowHeaderField("name", TimeflowDataType.Text, TimeflowAlias.TIMEFLOW_LABEL));

        tfex.startTimeLineExport(headers);
        
        List<ITimeflowDataCell> row = new LinkedList<>();
        
        row.add(new TimeflowDataCellDateTime(new Long("1339409320")));
        row.add(new TimeflowDataCellText("event"));
        row.add(new TimeflowDataCellText("testevent"));

        
        tfex.addEntry(row);
       
        row.clear();
        
        row.add(new TimeflowDataCellDateTime(new Long("1339739320")));
        row.add(new TimeflowDataCellText("post"));
        row.add(new TimeflowDataCellText("posttest"));

        tfex.addEntry(row);
        
        System.out.println(tfex.getBufferContent());
        
        try{
            tfex.finishExport("testexport.timeline");
        }catch(IOException ioex){
            ioex.printStackTrace();
        }
    }
}
