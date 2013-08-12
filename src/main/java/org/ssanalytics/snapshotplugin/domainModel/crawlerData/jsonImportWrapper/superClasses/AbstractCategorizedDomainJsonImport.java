package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses;


import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ICategorizedDomain;

public abstract class AbstractCategorizedDomainJsonImport extends AbstractTimedDomainJsonImport implements ICategorizedDomain{
	
	public AbstractCategorizedDomainJsonImport(JSONObject job) {
		super(job);
	}

	public String getCategory() {
		return this.getString("category");
	}

}
