package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.profile;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationYear;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractNamedDomainJsonImport;

public class ProfileEducationYearJsonImport extends AbstractNamedDomainJsonImport implements IProfileEducationYear{

	public ProfileEducationYearJsonImport(JSONObject job) {
		super(job);
	}

}
