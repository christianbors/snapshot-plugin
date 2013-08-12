package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.television;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.television.ITelevision;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractCategorizedDomainJsonMongo;

public class TelevisionJsonMongo extends AbstractCategorizedDomainJsonMongo implements ITelevision {

	public TelevisionJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}



}
