/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.timeflow;

import java.util.ArrayList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.event.IEvent;
import org.ssanalytics.snapshotplugin.io.timeflowExport.TimeflowAlias;
import org.ssanalytics.snapshotplugin.io.timeflowExport.TimeflowDataType;
import org.ssanalytics.snapshotplugin.io.timeflowExport.TimeflowExporter;
import org.ssanalytics.snapshotplugin.io.timeflowExport.TimeflowHeaderField;
import org.ssanalytics.snapshotplugin.io.timeflowExport.dataCells.ITimeflowDataCell;
import org.ssanalytics.snapshotplugin.io.timeflowExport.dataCells.TimeflowDataCellDateTime;
import org.ssanalytics.snapshotplugin.io.timeflowExport.dataCells.TimeflowDataCellText;

/**
 *
 * @author Robert
 */
public class TimeflowEventExporter {
    
    private TimeflowEventExporter()  {
	}
	
	private static TimeflowEventExporter instance = null;
	
    public static TimeflowEventExporter getInstance(){
        if (instance == null) {
            instance = new TimeflowEventExporter();
        }
        return instance;
    }
    
    public void exportEventTimelineForProfileAndSnapshotSpecificVersion(String filename, String snapshotInfoName, String version, String profile_fb_id) throws Exception {
                
        org.ssanalytics.snapshotplugin.io.timeflowExport.ITimeflowExporter tfex = new TimeflowExporter();
        
        List<IEvent> events = DaoFactory.getEventDAO().getEventListForProfileAndSnapshotSpecificVersion(snapshotInfoName, profile_fb_id, version);
        
        List<TimeflowHeaderField> headers = new ArrayList<>(5);
        headers.add(new TimeflowHeaderField("start time", TimeflowDataType.DateTime, TimeflowAlias.TIMEFLOW_START));
        headers.add(new TimeflowHeaderField("end time", TimeflowDataType.DateTime, TimeflowAlias.TIMEFLOW_END));
        headers.add(new TimeflowHeaderField("name", TimeflowDataType.Text, TimeflowAlias.TIMEFLOW_LABEL));
        headers.add(new TimeflowHeaderField("rsvp_status", TimeflowDataType.Text, TimeflowAlias.TIMEFLOW_TRACK));
        headers.add(new TimeflowHeaderField("location", TimeflowDataType.Text, TimeflowAlias.TIMEFLOW_TRACK));
        
        tfex.startTimeLineExport(headers);
        
        List<ITimeflowDataCell> row = new ArrayList<>(5);
        for(IEvent event : events){
            
            row.add(new TimeflowDataCellDateTime(event.getStartTime()));
            row.add(new TimeflowDataCellDateTime(event.getEndTime()));
            row.add(new TimeflowDataCellText(event.getName()));
            row.add(new TimeflowDataCellText(event.getRsvp_status()));
            row.add(new TimeflowDataCellText(event.getLocation()));
            tfex.addEntry(row);
            row.clear();
        }
        
        tfex.finishExport(filename);        
    }
    
    public void exportEventTimelineForProfileAndSnapshotLatestVersion(String filename, String snapshotInfoName, String profile_fb_id) throws Exception {
        
        String highestVersion = DaoFactory.getSnapshotInfoDAO().findHighestVersion(snapshotInfoName);
	this.exportEventTimelineForProfileAndSnapshotSpecificVersion(filename, snapshotInfoName, highestVersion, profile_fb_id);      
    }
    
}
