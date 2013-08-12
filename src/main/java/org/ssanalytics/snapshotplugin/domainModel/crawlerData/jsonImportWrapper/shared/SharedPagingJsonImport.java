package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPaging;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractBaseDomainJsonImport;

public class SharedPagingJsonImport extends AbstractBaseDomainJsonImport implements ISharedPaging{

	public SharedPagingJsonImport(JSONObject job) {
		super(job);
	}

	public String getNext() {
		return this.getString("next");
	}

	public String getPrevious() {
		return this.getString("previous");
	}
	
	

}
