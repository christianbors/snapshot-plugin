package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.event;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.event.IEvent;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractNamedDomainJsonImport;

public class EventJsonImport extends AbstractNamedDomainJsonImport implements IEvent {

	public EventJsonImport(JSONObject job) {
		super(job);
	}

	public long getStartTime() {
		return this.getTime("start_time");
	}

	public long getEndTime() {
		return this.getTime("end_time");
	}

	public String getTimeZone() {
		return (String) this.job.get("timezone");
	}

	public String getLocation() {
		return (String) this.job.get("location");
	}

	public String getRsvp_status() {
		return (String) this.job.get("rsvp_status");
	}

}
