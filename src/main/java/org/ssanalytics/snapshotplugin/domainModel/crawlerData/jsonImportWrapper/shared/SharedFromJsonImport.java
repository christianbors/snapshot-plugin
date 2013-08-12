package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractNamedDomainJsonImport;

public class SharedFromJsonImport extends AbstractNamedDomainJsonImport implements ISharedFrom{

	public SharedFromJsonImport(JSONObject job) {
		super(job);
	}
}
