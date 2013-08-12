package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.television;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.television.ITelevision;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractTimedDomainJsonImport;

public class TelevisionJsonImport extends AbstractTimedDomainJsonImport implements ITelevision {

	public TelevisionJsonImport(JSONObject job) {
		super(job);
	}

	public String getCategory() {
		return this.job.get("category").toString();
	}


}
