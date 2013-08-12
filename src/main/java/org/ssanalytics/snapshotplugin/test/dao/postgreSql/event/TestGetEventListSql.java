package org.ssanalytics.snapshotplugin.test.dao.postgreSql.event;

import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.EventDAOSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.event.IEvent;

public class TestGetEventListSql {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		List<IEvent> events;
                //set db mode to sql
                
		try {		
			events = EventDAOSql.getInstance().getEventListForSnapshotLatestVersion("");
			System.out.println("found " + events.size() + " events");
			System.out.println("Event with index 0 has account-fb_id: " + events.get(0).getAccountId());
			System.out.println("The event itself has the id: " + events.get(0).getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
		
		}
                
                try{
                    String snapshot = "";
                    String fb_id = "100003555467835";
                    events = DaoFactory.getEventDAO().getEventListForProfileAndSnapshotLatestVersion(snapshot, fb_id);
                    	System.out.println("found " + events.size() + " events for snapshot " + snapshot + " and facebook id " + fb_id);
			System.out.println("Event with index 0 has account-fb_id: " + events.get(0).getAccountId());
			System.out.println("The event itself has the id: " + events.get(0).getId());
                } catch (Exception e){
                    	e.printStackTrace();
                }
	}
}
