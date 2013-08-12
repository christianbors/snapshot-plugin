package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.profile;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationSchool;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractNamedDomainJsonMongo;

public class ProfileEducationSchoolJsonMongo extends AbstractNamedDomainJsonMongo implements IProfileEducationSchool{

	public ProfileEducationSchoolJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

}
