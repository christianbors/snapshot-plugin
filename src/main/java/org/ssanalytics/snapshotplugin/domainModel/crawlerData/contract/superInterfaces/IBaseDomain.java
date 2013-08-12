package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces;

public interface IBaseDomain {
	
//	public Long getTimestamp();
	public String getSnapshotId();
	public String getChecksum();
	public String getAccountId();

}
