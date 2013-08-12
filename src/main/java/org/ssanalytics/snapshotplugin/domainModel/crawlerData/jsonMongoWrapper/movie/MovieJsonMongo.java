package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.movie;



import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.movie.IMovie;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractCategorizedDomainJsonMongo;

public class MovieJsonMongo extends AbstractCategorizedDomainJsonMongo implements IMovie {

	public MovieJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

}
