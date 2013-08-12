package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.profile;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileHometown;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractNamedDomainJsonMongo;

public class ProfileHometownJsonMongo extends AbstractNamedDomainJsonMongo implements IProfileHometown{

	public ProfileHometownJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

}
