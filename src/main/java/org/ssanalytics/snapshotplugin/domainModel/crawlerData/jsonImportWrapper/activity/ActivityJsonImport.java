package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.activity;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.activity.IActivity;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractCategorizedDomainJsonImport;

public class ActivityJsonImport extends AbstractCategorizedDomainJsonImport implements IActivity {

	public ActivityJsonImport(JSONObject job) {
		super(job);
	}

}
