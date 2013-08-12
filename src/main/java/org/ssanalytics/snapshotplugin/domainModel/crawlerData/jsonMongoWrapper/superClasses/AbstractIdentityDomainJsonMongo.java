package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses;


import com.mongodb.DBObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IIdentityDomain;

public abstract class AbstractIdentityDomainJsonMongo extends AbstractBaseDomainJsonMongo implements IIdentityDomain{

	public AbstractIdentityDomainJsonMongo(DBObject job) {
		super(job);
	}
	
	public String getId(){

		return this.getDataString("id");
	}
}
