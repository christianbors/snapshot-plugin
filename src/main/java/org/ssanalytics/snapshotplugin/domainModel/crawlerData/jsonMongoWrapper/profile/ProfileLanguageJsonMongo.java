package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.profile;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileLanguage;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractNamedDomainJsonMongo;

public class ProfileLanguageJsonMongo extends AbstractNamedDomainJsonMongo implements IProfileLanguage{

	public ProfileLanguageJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

}
