package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.profile;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationSchool;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractNamedDomainJsonImport;

public class ProfileEducationSchoolJsonImport extends AbstractNamedDomainJsonImport implements IProfileEducationSchool{

	public ProfileEducationSchoolJsonImport(JSONObject job) {
		super(job);
	}

}
