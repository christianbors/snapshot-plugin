package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.profile;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileHometown;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractNamedDomainJsonImport;

public class ProfileHometownJsonImport extends AbstractNamedDomainJsonImport implements IProfileHometown{

	public ProfileHometownJsonImport(JSONObject job) {
		super(job);
	}

}
