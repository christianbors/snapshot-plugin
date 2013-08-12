package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPaging;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractBaseDomainJsonMongo;

public class SharedPagingJsonMongo extends AbstractBaseDomainJsonMongo implements ISharedPaging{

	public SharedPagingJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

	public String getNext() {
		return getDataString("next");
	}

	public String getPrevious() {
		return getDataString("previous");
	}
	
	

}
