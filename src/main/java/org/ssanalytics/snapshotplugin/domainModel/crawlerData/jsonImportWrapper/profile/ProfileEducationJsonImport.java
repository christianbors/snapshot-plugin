package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.profile;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducation;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationClass;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationConcentration;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationSchool;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationYear;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractBaseDomainJsonImport;

public class ProfileEducationJsonImport extends AbstractBaseDomainJsonImport implements IProfileEducation{

	public ProfileEducationJsonImport(JSONObject job) {
		super(job);
	}

	public String getType() {
		
		return this.job.get("type").toString();
	}

	public IProfileEducationYear getYear() {
		
		JSONObject temp = (JSONObject) this.job.get("year");		
		
		return temp != null ? new ProfileEducationYearJsonImport(temp) : null;
	}

	public List<IProfileEducationConcentration> getConcentrationList() {
		
		JSONArray jar = (JSONArray) this.job.get("concentration");
		
		List<IProfileEducationConcentration> list = new ArrayList<IProfileEducationConcentration>();
		
		if(jar != null){
			
			for(Object o : jar){
				
				list.add(new ProfileEducationConcentrationJsonImport((JSONObject) o));
			}
		}
		
		return list;
	}

	public IProfileEducationSchool getSchool() {
		
		JSONObject temp = (JSONObject) this.job.get("school");		
		
		return temp != null ? new ProfileEducationSchoolJsonImport(temp) : null;
	}

	@Override
	public List<IProfileEducationClass> getClasses() {
		JSONArray jar = (JSONArray) this.job.get("classes");
		
		List<IProfileEducationClass> list = new ArrayList<IProfileEducationClass>();
		
		if(jar != null){
			
			for(Object o : jar){
				
				list.add(new ProfileEducationClassJsonImport((JSONObject) o));
			}
		}
		
		return list;
	}
	
	

}
