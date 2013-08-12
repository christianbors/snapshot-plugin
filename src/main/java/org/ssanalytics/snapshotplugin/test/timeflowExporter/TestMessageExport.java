/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.timeflowExporter;

import org.ssanalytics.snapshotplugin.io.dbConnection.timeflow.TimeflowEventExporter;
import org.ssanalytics.snapshotplugin.io.dbConnection.timeflow.TimeflowMessageExporter;

/**
 *
 * @author chw
 */
public class TestMessageExport {
    
    
    public static void main(String args[]) throws Exception {

        //Config.setDbMode(Config.DbMode.MONGODB);
        //TimeflowMessageExporter.getInstance().exportMessageTimelineForRootAccountOfSnapshotLatestVersion("export_messages_mongo.timeline", "dd");

        //Config.setDbMode(Config.DbMode.POSTGRESQL);
        //TimeflowEventExporter.getInstance().exportEventTimelineForProfileAndSnapshotLatestVersion("export_events_postgres.timeline", "dd", "100000123807828");

    }
}
