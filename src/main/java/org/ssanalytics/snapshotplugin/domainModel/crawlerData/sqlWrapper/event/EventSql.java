package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.event;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.event.IEvent;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractNamedDomainSql;

public class EventSql extends AbstractNamedDomainSql implements IEvent {

	private long startTime;
	private long endTime;
	private String timeZone;
	private String location;
	private String rsvp_status;
	
	
	public EventSql(String id, String name, long startTime, long endTime, String timeZone, String location, String rsvp_status) {
		super(id, name);
		this.startTime = startTime;
		this.endTime = endTime;
		this.timeZone = timeZone;
		this.location = location;
		this.rsvp_status = rsvp_status;
	}


	public long getStartTime() {
		return this.startTime;
	}

	public long getEndTime() {
		return this.endTime;
	}


	public String getTimeZone() {
		return this.timeZone;
	}


	public String getLocation() {
		return this.location;
	}


	public String getRsvp_status() {
		return this.rsvp_status;
	}

}
