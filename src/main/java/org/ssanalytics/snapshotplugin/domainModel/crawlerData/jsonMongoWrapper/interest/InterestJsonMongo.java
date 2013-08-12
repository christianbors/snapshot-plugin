package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.interest;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.interest.IInterest;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractCategorizedDomainJsonMongo;

public class InterestJsonMongo extends AbstractCategorizedDomainJsonMongo implements IInterest {

	public InterestJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}
}
