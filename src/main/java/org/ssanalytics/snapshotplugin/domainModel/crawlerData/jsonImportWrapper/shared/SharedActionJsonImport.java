package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractBaseDomainJsonImport;

public class SharedActionJsonImport extends AbstractBaseDomainJsonImport implements ISharedAction{

	public SharedActionJsonImport(JSONObject job) {
		super(job);
	}

	public String getName() {
		return this.job.get("name").toString();
	}

	public String getLink() {
		return this.job.get("link").toString();
	}

}
