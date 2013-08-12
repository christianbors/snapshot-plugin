package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.profile;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileLanguage;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractNamedDomainJsonImport;

public class ProfileLanguageJsonImport extends AbstractNamedDomainJsonImport implements IProfileLanguage{

	public ProfileLanguageJsonImport(JSONObject job) {
		super(job);
	}

}
