package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.profile;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileWork;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileWorkEmployer;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractNamedDomainJsonImport;

public class ProfileWorkJsonImport extends AbstractNamedDomainJsonImport implements IProfileWork{

	public ProfileWorkJsonImport(JSONObject job) {
		super(job);

	}

	public IProfileWorkEmployer getEmployer() {
		JSONObject temp = (JSONObject) this.job.get("employer");		
		
		return temp != null ? new ProfileWorkEmployerJsonImport(temp) : null;
	}

	@Override
	public String getStartDate() {
		return this.getString("start_date");
	}

	@Override
	public String getEndDate() {
		return this.getString("end_date");
	}	

}
