package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.profile;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationYear;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractNamedDomainJsonMongo;

public class ProfileEducationYearJsonMongo extends AbstractNamedDomainJsonMongo implements IProfileEducationYear{

	public ProfileEducationYearJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

}
