package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.book;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.book.IBook;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractTimedDomainJsonMongo;

public class BookJsonMongo extends AbstractTimedDomainJsonMongo implements IBook{
	
	public BookJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}
	
	public String getCategory() {

		return (String) dbo.get("category");
		
	}
}
