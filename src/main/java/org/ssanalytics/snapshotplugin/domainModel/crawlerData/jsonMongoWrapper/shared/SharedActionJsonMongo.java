package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractBaseDomainJsonMongo;

public class SharedActionJsonMongo extends AbstractBaseDomainJsonMongo implements ISharedAction{

	public SharedActionJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

	public String getName() {
		return getDataString("name");
	}

	public String getLink() {
		return getDataString("link");
	}

}
