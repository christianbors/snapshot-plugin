/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.timeflow;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.inbox.IInbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.outbox.IOutbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedToData;
import org.ssanalytics.snapshotplugin.io.timeflowExport.TimeflowAlias;
import org.ssanalytics.snapshotplugin.io.timeflowExport.TimeflowDataType;
import org.ssanalytics.snapshotplugin.io.timeflowExport.TimeflowExporter;
import org.ssanalytics.snapshotplugin.io.timeflowExport.TimeflowHeaderField;
import org.ssanalytics.snapshotplugin.io.timeflowExport.dataCells.ITimeflowDataCell;
import org.ssanalytics.snapshotplugin.io.timeflowExport.dataCells.TimeflowDataCellDateTime;
import org.ssanalytics.snapshotplugin.io.timeflowExport.dataCells.TimeflowDataCellText;

/**
 *
 * @author chw
 */
public class TimeflowMessageExporter {
        
    private TimeflowMessageExporter()  {
	}
	
    private static TimeflowMessageExporter instance = null;
	
    public static TimeflowMessageExporter getInstance(){
        if (instance == null) {
            instance = new TimeflowMessageExporter();
        }
        return instance;
    }
    
    
    public void exportMessageTimelineForRootAccountOfSnapshotSpecificVersion(String filename, String snapshotInfo, String version) throws Exception{
        
        TimeflowExporter tiflex = new TimeflowExporter();
        
        List<TimeflowHeaderField> headers = new ArrayList<>(4);
        
        headers.add(new TimeflowHeaderField("updated time", TimeflowDataType.DateTime, TimeflowAlias.TIMEFLOW_START));
        headers.add(new TimeflowHeaderField("category", TimeflowDataType.Text, TimeflowAlias.TIMEFLOW_TRACK));
        headers.add(new TimeflowHeaderField("to/from", TimeflowDataType.Text, TimeflowAlias.TIMEFLOW_LABEL));
        headers.add(new TimeflowHeaderField("message", TimeflowDataType.Text, TimeflowAlias.TIMEFLOW_TRACK));
        
        tiflex.startTimeLineExport(headers);
        

        TimeflowDataCellText category = new TimeflowDataCellText("incoming message");
        for(IInbox inbox : DaoFactory.getInboxDAO().getInboxListForRootAccountOfSnapshotSpecificVersion(snapshotInfo, version)){
            
            List<ITimeflowDataCell> row = new ArrayList<>(4);
            
            row.add(new TimeflowDataCellDateTime(inbox.getUpdated_time()));
            
            row.add(category);
            
            if(inbox.getFrom() != null)
                row.add(new TimeflowDataCellText(inbox.getFrom().getName()));
            else
                row.add(new TimeflowDataCellText(""));

            
            row.add(new TimeflowDataCellText(inbox.getMessage()));            
            
            tiflex.addEntry(row);
        }
        

        category = new TimeflowDataCellText("outgoing message");
        for(IOutbox outbox : DaoFactory.getOutboxDAO().getOutboxListForRootAccountOfSnapshotSpecificVersion(snapshotInfo, version)){
            
            List<ITimeflowDataCell> row = new ArrayList<>(4);
            row.add(new TimeflowDataCellDateTime(outbox.getUpdated_time()));
            
            row.add(category);
            
            String to = " ";
            if(outbox.getTo() != null)
                if(outbox.getTo().getToDataList() != null)
                    for(ISharedToData toData : outbox.getTo().getToDataList()){
                        to += toData.getName();
                        to += "; ";
                    }
            
            if(to.length() >=2)
                to = to.substring(0, to.length() - 2);
            
            row.add(new TimeflowDataCellText(to));
            
            row.add(new TimeflowDataCellText(outbox.getMessage()));   
            
            tiflex.addEntry(row);
        }
        
        tiflex.finishExport(filename);
    }
    
    public void exportMessageTimelineForRootAccountOfSnapshotLatestVersion(String filename, String snapshotInfo) throws SQLException, UnknownHostException, Exception{
        this.exportMessageTimelineForRootAccountOfSnapshotSpecificVersion(filename, snapshotInfo, DaoFactory.getSnapshotInfoDAO().findHighestVersion(snapshotInfo));
    }
}
