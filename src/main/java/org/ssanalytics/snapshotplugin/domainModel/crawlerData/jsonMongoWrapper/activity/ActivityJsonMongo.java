package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.activity;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.activity.IActivity;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractCategorizedDomainJsonMongo;

public class ActivityJsonMongo extends AbstractCategorizedDomainJsonMongo implements IActivity {

	public ActivityJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

}
