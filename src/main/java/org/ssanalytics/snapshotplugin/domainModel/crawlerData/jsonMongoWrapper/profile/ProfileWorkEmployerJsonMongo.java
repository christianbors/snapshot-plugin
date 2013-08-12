package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.profile;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileWorkEmployer;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractNamedDomainJsonMongo;

public class ProfileWorkEmployerJsonMongo extends AbstractNamedDomainJsonMongo implements IProfileWorkEmployer {

	public ProfileWorkEmployerJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

}
