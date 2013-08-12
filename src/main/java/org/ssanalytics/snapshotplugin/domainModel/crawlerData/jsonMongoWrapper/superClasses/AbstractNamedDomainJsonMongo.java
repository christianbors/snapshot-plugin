package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses;


import com.mongodb.DBObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.INamedDomain;

public class AbstractNamedDomainJsonMongo extends AbstractIdentityDomainJsonMongo implements INamedDomain {

	public AbstractNamedDomainJsonMongo(DBObject job){
		super(job);
	}

	public String getName() {		
		return this.getDataString("name");
	}
}
