package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.profile;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileWorkEmployer;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractNamedDomainJsonImport;

public class ProfileWorkEmployerJsonImport extends AbstractNamedDomainJsonImport implements IProfileWorkEmployer {

	public ProfileWorkEmployerJsonImport(JSONObject job) {
		super(job);
	}

}
