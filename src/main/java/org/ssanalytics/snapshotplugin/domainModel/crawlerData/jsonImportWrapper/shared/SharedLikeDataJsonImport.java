package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLikeData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractNamedDomainJsonImport;

public class SharedLikeDataJsonImport extends AbstractNamedDomainJsonImport implements ISharedLikeData{

	public SharedLikeDataJsonImport(JSONObject job) {
		super(job);
	}

}
