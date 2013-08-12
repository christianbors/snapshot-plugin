package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses;


import com.mongodb.DBObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ITimedDomain;

public class AbstractTimedDomainJsonMongo extends AbstractNamedDomainJsonMongo implements ITimedDomain{

	public AbstractTimedDomainJsonMongo(DBObject job) {
		super(job);
	}

	public Long getCreated_time() {
		return this.getDataTime("created_time");
	}
	
	
	

}
