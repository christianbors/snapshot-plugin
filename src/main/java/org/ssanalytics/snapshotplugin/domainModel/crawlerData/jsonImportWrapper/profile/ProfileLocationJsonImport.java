package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.profile;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileLocation;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractNamedDomainJsonImport;

public class ProfileLocationJsonImport extends AbstractNamedDomainJsonImport implements IProfileLocation{

	public ProfileLocationJsonImport(JSONObject job) {
		super(job);
	}

}
