package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLikeData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractNamedDomainJsonMongo;

public class SharedLikeDataJsonMongo extends AbstractNamedDomainJsonMongo implements ISharedLikeData{

	public SharedLikeDataJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

}
