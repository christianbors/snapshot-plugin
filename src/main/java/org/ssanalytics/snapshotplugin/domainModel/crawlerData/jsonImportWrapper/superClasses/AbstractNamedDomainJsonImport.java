package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses;

import org.json.simple.JSONObject;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.INamedDomain;

public class AbstractNamedDomainJsonImport extends AbstractIdentityDomainJsonImport implements INamedDomain {

	public AbstractNamedDomainJsonImport(JSONObject job) {
		super(job);
	}

	public String getName() {		
		return this.getString("name");
	}
}
