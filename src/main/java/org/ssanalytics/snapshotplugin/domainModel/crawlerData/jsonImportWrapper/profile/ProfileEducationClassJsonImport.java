package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.profile;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationClass;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractNamedDomainJsonImport;

public class ProfileEducationClassJsonImport extends AbstractNamedDomainJsonImport implements IProfileEducationClass{

	public ProfileEducationClassJsonImport(JSONObject job) {
		super(job);
	}

}
