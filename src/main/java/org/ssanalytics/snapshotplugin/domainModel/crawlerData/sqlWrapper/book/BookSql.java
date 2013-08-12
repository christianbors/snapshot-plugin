package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.book;





import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.book.IBook;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractTimedDomainSql;

public class BookSql extends AbstractTimedDomainSql implements IBook{
	
	public BookSql(String id, String name, long created_time, String category){
		
		super(id, name, created_time);
		this.category = category;
	}
	
	private String category;


	public String getCategory() {
		
		return category;
	}
}
