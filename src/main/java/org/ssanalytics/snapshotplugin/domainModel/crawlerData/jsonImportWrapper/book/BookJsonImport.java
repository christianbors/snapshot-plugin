package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.book;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.book.IBook;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractTimedDomainJsonImport;

public class BookJsonImport extends AbstractTimedDomainJsonImport implements IBook{
	
	public BookJsonImport(JSONObject job){
		super(job);
	}
	
	public String getCategory() {

		return (String) job.get("category");
		
	}
}
