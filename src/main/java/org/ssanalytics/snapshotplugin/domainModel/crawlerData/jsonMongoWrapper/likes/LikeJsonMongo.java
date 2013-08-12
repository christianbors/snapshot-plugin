package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.likes;



import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.likes.ILike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractCategorizedDomainJsonMongo;


public class LikeJsonMongo extends AbstractCategorizedDomainJsonMongo implements ILike{

	public LikeJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}	

}
