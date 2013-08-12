package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.profile;


import com.mongodb.DBObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationClass;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractNamedDomainJsonMongo;

public class ProfileEducationClassJsonMongo extends AbstractNamedDomainJsonMongo implements IProfileEducationClass{

	public ProfileEducationClassJsonMongo(DBObject job) {
		super(job);

	}

}
