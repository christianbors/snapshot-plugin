package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ITimedDomain;

public class AbstractTimedDomainJsonImport extends AbstractNamedDomainJsonImport implements ITimedDomain{

	public AbstractTimedDomainJsonImport(JSONObject job) {
		super(job);
	}

	public Long getCreated_time() {
		return this.getTime("created_time");
	}
	
	
	

}
