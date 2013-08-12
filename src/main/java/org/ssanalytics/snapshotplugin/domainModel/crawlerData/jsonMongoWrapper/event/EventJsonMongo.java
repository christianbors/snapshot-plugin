package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.event;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.event.IEvent;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractNamedDomainJsonMongo;

public class EventJsonMongo extends AbstractNamedDomainJsonMongo implements IEvent {

	public EventJsonMongo(com.mongodb.DBObject job){
		super(job);
	}

	public long getStartTime() {
		return this.getDataTime("start_time");
	}

	public long getEndTime() {
		return this.getDataTime("end_time");
	}

	public String getTimeZone() {
		return this.getDataString("timezone");
	}

	public String getLocation() {
		return this.getDataString("location");
	}

	public String getRsvp_status() {
		return this.getDataString("rsvp_status");
	}

}
