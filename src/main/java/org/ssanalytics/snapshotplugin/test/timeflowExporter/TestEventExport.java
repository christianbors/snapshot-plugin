/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.timeflowExporter;

import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.timeflow.TimeflowEventExporter;

/**
 *
 * @author Robert
 */
public class TestEventExport {
    
    
    public static void main(String args[]) throws Exception{
        
       //Config.setDbMode(Config.DbMode.MONGODB);
       //TimeflowEventExporter.getInstance().exportEventTimelineForProfileAndSnapshotLatestVersion("export_events_mongo.timeline", "dd", "100000123807828");
       
       //Config.setDbMode(Config.DbMode.POSTGRESQL);
       ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
       ConfigurationFileManager.readConfigFromFile();
       TimeflowEventExporter.getInstance().exportEventTimelineForProfileAndSnapshotLatestVersion("export_events_postgres.timeline", "dd", "100000123807828");
        
    }
    
}
