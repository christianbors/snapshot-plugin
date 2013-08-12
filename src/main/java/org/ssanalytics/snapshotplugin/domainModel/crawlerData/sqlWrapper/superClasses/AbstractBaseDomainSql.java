package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses;



import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IBaseDomain;

public class AbstractBaseDomainSql implements IBaseDomain{	
	
	protected Long timestamp;
	protected String snapshotId;
	protected String hash;
	protected String accountId;	
	
	public AbstractBaseDomainSql(){
	}


	public Long getTimestamp() {
		return timestamp;
	}


	public String getSnapshotId() {
		return snapshotId;
	}


	public String getChecksum() {
		return hash;
	}


	public String getAccountId() {
		return accountId;
	}


	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}


	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}


	public void setHash(String hash) {
		this.hash = hash;
	}


	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}	
}
