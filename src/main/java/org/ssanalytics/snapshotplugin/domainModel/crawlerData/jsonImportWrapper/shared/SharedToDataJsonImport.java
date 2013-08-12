package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedToData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractNamedDomainJsonImport;

public class SharedToDataJsonImport extends AbstractNamedDomainJsonImport implements ISharedToData {

	public SharedToDataJsonImport(JSONObject job) {
		super(job);
	}	

}
