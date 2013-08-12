package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo;

import java.util.List;

public interface ISnapshotInfo {
	
	public String getValue();
	
	public Long getTimestamp();
	public String getRoot();
	public String version();
	public List<String> getFriends();

}
