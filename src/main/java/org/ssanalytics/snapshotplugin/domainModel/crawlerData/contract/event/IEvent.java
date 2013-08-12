package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.event;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.INamedDomain;

public interface IEvent extends INamedDomain{
	
	public long getStartTime();
	public long getEndTime();
	public String getTimeZone();
	public String getLocation();
	public String getRsvp_status();
	

}
