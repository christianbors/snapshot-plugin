package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.profile;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationConcentration;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractNamedDomainJsonMongo;

public class ProfileEducationConcentrationJsonMongo extends AbstractNamedDomainJsonMongo implements IProfileEducationConcentration{

	public ProfileEducationConcentrationJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

}
