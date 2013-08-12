package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.profile;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileWork;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileWorkEmployer;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractNamedDomainJsonMongo;

public class ProfileWorkJsonMongo extends AbstractNamedDomainJsonMongo implements IProfileWork{

	public ProfileWorkJsonMongo(com.mongodb.DBObject job) {
		super(job);

	}

	public IProfileWorkEmployer getEmployer() {
		com.mongodb.DBObject temp = getDataDbo("employer");	
		
		return temp != null ? new ProfileWorkEmployerJsonMongo(temp) : null;
	}

	@Override
	public String getStartDate() {
		return this.getDataString("start_date");
	}

	@Override
	public String getEndDate() {
		return this.getDataString("start_date");
	}	

}
