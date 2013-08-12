package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.snapshotInfo;

import java.util.List;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractBaseDomainSql;

public class SnapshotInfoSql extends AbstractBaseDomainSql implements ISnapshotInfo{

	
	
	public SnapshotInfoSql(String snapshotName, Long timestamp, String root, String version, List<String> friends) {
		super();
		
		this.value = snapshotName;
		this.timestamp = timestamp;
		this.root = root;
		this.version = version;
		this.friendList = friends;		
	}

	private String value;
	private Long timestamp;
	private String root;
	private String version;
	private List<String> friendList;
	
	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public Long getTimestamp() {
		return this.timestamp;
	}

	@Override
	public String getRoot() {
		return this.root;
	}

	@Override
	public String version() {
		return this.version;
	}

	@Override
	public List<String> getFriends() {
		return this.friendList;
	}	

	@Override
	public String toString() {
		return this.getValue() + " " + this.getRoot();
	}
}
