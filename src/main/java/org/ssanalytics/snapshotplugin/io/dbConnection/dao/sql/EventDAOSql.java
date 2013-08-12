package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IEventDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.event.IEvent;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.event.EventSql;
import org.ssanalytics.snapshotplugin.io.timeflowExport.TimeflowExporter;

public class EventDAOSql extends AbstractSuperDAOSql implements IEventDAO {

    private static EventDAOSql instance = null;

    protected EventDAOSql() throws SQLException {
        super();
        this.table_name = "event";
    }

    public static EventDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new EventDAOSql();
        }
        return instance;
    }

    public void saveEventList(List<IEvent> toSave, long account_id)
            throws SQLException {

        if (toSave == null) {
            return;
        }

        PreparedStatement pst = this.prepareStatement("INSERT INTO " + this.table_name + " (id, account_id, fb_id, fb_name, start_time, end_time, timezone, location, rsvp_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        pst.clearBatch();

        for (IEvent event : toSave) {

            this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
            this.setLongOrNull(2, account_id, pst);
            this.setStringOrNull(3, event.getId(), pst);
            this.setStringOrNull(4, event.getName(), pst);
            this.setLongOrNull(5, event.getStartTime(), pst);
            this.setLongOrNull(6, event.getEndTime(), pst);
            this.setStringOrNull(7, event.getTimeZone(), pst);
            this.setStringOrNull(8, event.getLocation(), pst);
            this.setStringOrNull(9, event.getRsvp_status(), pst);

            pst.addBatch();
        }
        pst.executeBatch();

    }

    public void saveEvent(IEvent toSave, long account_id) throws SQLException {
        List<IEvent> list = new ArrayList<>(1);
        list.add(toSave);
        this.saveEventList(list, account_id);
    }

    @Override
    public List<IEvent> getEventListForSnapshotLatestVersion(String snapshotInfoName) throws Exception {
        String highestVersion = this.getHighestVersion(snapshotInfoName);
        return this.getEventListForSnapshotSpecificVersion(snapshotInfoName, highestVersion);
    }

    @Override
    public List<IEvent> getEventListForSnapshotSpecificVersion(String snapshotInfoName, String version) throws SQLException {

        List<IEvent> eventList = new ArrayList<>();


        /*
         PreparedStatement event_pst = this.prepareStatement("SELECT id, account_id, fb_id, name, start_time, end_time, timezone, location, rsvp_status FROM event WHERE account_id = (SELECT id FROM account WHERE snapshotinfo_id = (SELECT id FROM snapshotinfo WHERE snapshotname = ? AND version = ?)))");
         ResultSet ers = event_pst.executeQuery();
		
         ResultSet account_rs;
         PreparedStatement account_pst = this.prepareStatement("SELECT fb_id FROM account WHERE id = ?");
         while(ers.next()){
         EventSql event = new EventSql(ers.getLong("id"), ers.getString("fb_id"), ers.getString("name"), ers.getLong("start_time"), ers.getLong("end_time"), ers.getString("timezone"), ers.getString("location"), ers.getString("rsvp_status"));
			 
         //set snapshotname
         event.setSnapshotId(snapshotInfoName);
			 
         //set account fb id
         account_this.setLongOrNull(1, ers.getLong("account_id"), pst);
         account_rs = account_pst.executeQuery();
         if(account_rs.next())
         event.setAccountId(ers.getString("fb_id"));	
			 
         eventList.add(event);
         }		
         */

        //probably faster:

        PreparedStatement account_pst = this.prepareStatement("SELECT id, fb_id FROM account WHERE snapshotinfo_id = (SELECT id from snapshotinfo WHERE snapshotname = ? AND version = ?)");
        this.setStringOrNull(1, snapshotInfoName, account_pst);
        this.setStringOrNull(2, version, account_pst);

        ResultSet account_rs = account_pst.executeQuery();

        PreparedStatement event_pst = this.prepareStatement("SELECT id, account_id, fb_id, fb_name, start_time, end_time, timezone, location, rsvp_status FROM event WHERE account_id = ? ");
        ResultSet event_rs;

        while (account_rs.next()) {
            long account_id = account_rs.getLong("id");
            String account_fb_id = account_rs.getString("fb_id");

            this.setLongOrNull(1, account_id, event_pst);

            event_rs = event_pst.executeQuery();

            while (event_rs.next()) {
                EventSql event = new EventSql(event_rs.getString("fb_id"), event_rs.getString("fb_name"), event_rs.getLong("start_time"), event_rs.getLong("end_time"), event_rs.getString("timezone"), event_rs.getString("location"), event_rs.getString("rsvp_status"));

                //set snapshotname
                event.setSnapshotId(snapshotInfoName);

                //set account fb id
                event.setAccountId(account_fb_id);

                eventList.add(event);
            }
        }

        return eventList;
    }

    @Override
    public List<IEvent> getEventListForProfileAndSnapshotSpecificVersion(String snapshotInfoname, String profile_fb_id, String version) throws Exception {

        PreparedStatement pst = this.prepareStatement("SELECT id, fb_id, fb_name, start_time, end_time, timezone, location, rsvp_status FROM " + this.table_name + " WHERE account_id = (SELECT id FROM account WHERE fb_id = ? AND snapshotinfo_id in (SELECT id FROM snapshotinfo WHERE snapshotName = ? AND version = ?))");
        this.setStringOrNull(1, profile_fb_id, pst);
        this.setStringOrNull(2, snapshotInfoname, pst);
        this.setStringOrNull(3, version, pst);

        ResultSet rs = pst.executeQuery();

        List<IEvent> eventList = new ArrayList<>();

        while (rs.next()) {
            EventSql event = new EventSql(rs.getString("fb_id"), rs.getString("fb_name"), rs.getLong("start_time"), rs.getLong("end_time"), rs.getString("timezone"), rs.getString("location"), rs.getString("rsvp_status"));
            event.setAccountId(profile_fb_id);
            event.setSnapshotId(snapshotInfoname);
            //todo: set timestamp
            eventList.add(event);
        }

        return eventList;
    }

    @Override
    public List<IEvent> getEventListForProfileAndSnapshotLatestVersion(String snapshotInfoname, String profile_fb_id) throws Exception {
        String hV = this.getHighestVersion(snapshotInfoname);
        return this.getEventListForProfileAndSnapshotSpecificVersion(snapshotInfoname, profile_fb_id, hV);
    }
}
