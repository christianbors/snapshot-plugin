package org.ssanalytics.snapshotplugin.test.dao.mongo.event;

import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.EventDAOMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.event.IEvent;

public class TestGetEventListMongo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			List<IEvent> eList = EventDAOMongo.getInstance().getEventListForSnapshotLatestVersion("dd");
			System.out.println(eList.size());
			
			System.out.println("Element with index 0: ");
			System.out.println("Account ID: " + eList.get(0).getAccountId());
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
