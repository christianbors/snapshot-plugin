package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.profile;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileLocation;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractNamedDomainJsonMongo;

public class ProfileLocationJsonMongo extends AbstractNamedDomainJsonMongo implements IProfileLocation{

	public ProfileLocationJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

}
