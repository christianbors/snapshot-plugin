package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.profile;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationConcentration;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractNamedDomainJsonImport;

public class ProfileEducationConcentrationJsonImport extends AbstractNamedDomainJsonImport implements IProfileEducationConcentration{

	public ProfileEducationConcentrationJsonImport(JSONObject job) {
		super(job);
	}

}
