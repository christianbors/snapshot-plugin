package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ITimedDomain;

public class AbstractTimedDomainSql extends AbstractNamedDomainSql implements ITimedDomain {	
	
	protected Long createdTime;


	public Long getCreated_time() {
		return this.createdTime;
	}
	
	public AbstractTimedDomainSql(String id, String name, Long created_time){
		super(id, name);
		this.createdTime = created_time;
	}
	
	

}
