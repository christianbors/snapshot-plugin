package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.profile;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducation;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileHometown;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileLanguage;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileLocation;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileWork;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractIdentityDomainJsonImport;

public class ProfileJsonImport extends AbstractIdentityDomainJsonImport implements IProfile {

	public ProfileJsonImport(JSONObject job) {
		
		super(job);		
	}

	public String getBirthday() {

		return (String) this.job.get("birthday");
	}

	public String getFirstName() {
		
		return (String) this.job.get("first_name");
	}

	public String getLastName() {
		
		return (String) this.job.get("last_name");
	}

	public String getName() {
		
		return (String) this.job.get("name");
	}

	public String getGender() {
		
		return (String) this.job.get("gender");
	}

	public String getLink() {
		
		return (String) this.job.get("link");
	}

	public String getLocale() {
		
		return (String) this.job.get("locale");
	}

	public String getUsername() {
		
		return (String) this.job.get("username");
	}

	public long getUpdatedTime() {
		
		return this.getTime("updated_time");
	}

	public IProfileLocation getLocation() {
		
		JSONObject temp = (JSONObject) this.job.get("location");		
		
		return temp != null ? new ProfileLocationJsonImport(temp) : null;
	}



	public IProfileHometown getHometown() {
		
		JSONObject temp = (JSONObject) this.job.get("hometown");		
		
		return temp != null ? new ProfileHometownJsonImport(temp) : null;
	}
	
	public List<IProfileEducation> getEducationList() {
		
		JSONArray jar = (JSONArray) this.job.get("education");
		
		List<IProfileEducation> list = new ArrayList<IProfileEducation>();
		if (jar != null) {
			for (Object o : jar) {

				list.add(new ProfileEducationJsonImport((JSONObject) o));
			}
		}
		
		return list;
	}

	public List<IProfileLanguage> getLanguageList() {
		JSONArray jar = (JSONArray) this.job.get("languages");
		
		List<IProfileLanguage> list = new ArrayList<IProfileLanguage>();
		
		if (jar != null) {
			for (Object o : jar) {

				list.add(new ProfileLanguageJsonImport((JSONObject) o));
			}
		}
		
		return list;
	}

	public List<IProfileWork> getWorkList() {
		
		JSONArray jar = (JSONArray) this.job.get("languages");
		
		List<IProfileWork> list = new ArrayList<IProfileWork>();
		
		if (jar != null) {

			for (Object o : jar) {

				list.add(new ProfileWorkJsonImport((JSONObject) o));
			}
		}
		
		return list;
		
	}

}
