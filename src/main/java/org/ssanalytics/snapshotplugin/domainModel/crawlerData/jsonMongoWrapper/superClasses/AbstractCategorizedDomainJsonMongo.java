package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses;



import com.mongodb.DBObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ICategorizedDomain;

public abstract class AbstractCategorizedDomainJsonMongo extends AbstractTimedDomainJsonMongo implements ICategorizedDomain{
	
	public AbstractCategorizedDomainJsonMongo(DBObject job) {
		super(job);
	}

	public String getCategory() {
		return this.getDataString("category");
	}

}
