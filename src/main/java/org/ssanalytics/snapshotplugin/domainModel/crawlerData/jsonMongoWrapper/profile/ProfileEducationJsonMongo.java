package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.profile;


import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducation;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationClass;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationConcentration;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationSchool;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationYear;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractBaseDomainJsonMongo;

public class ProfileEducationJsonMongo extends AbstractBaseDomainJsonMongo implements IProfileEducation{

	public ProfileEducationJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

	public String getType() {
		
		return getDataString("type");
	}

	public IProfileEducationYear getYear() {
		
		com.mongodb.DBObject temp = getDataDbo("year");		
		
		return temp != null ? new ProfileEducationYearJsonMongo(temp) : null;
	}

	public List<IProfileEducationConcentration> getConcentrationList() {
		
		BasicDBList resultSet = this.getDataList("concentration");
		
		List<IProfileEducationConcentration> list = new ArrayList<IProfileEducationConcentration>();
		
		
		if(resultSet != null){
			for (Object o : resultSet){
				list.add(new ProfileEducationConcentrationJsonMongo((DBObject) o));
			}
		}
		
		return list;
	}

	public IProfileEducationSchool getSchool() {
		
		com.mongodb.DBObject temp = getDataDbo("school");		
		
		return temp != null ? new ProfileEducationSchoolJsonMongo(temp) : null;
	}

	@Override
	public List<IProfileEducationClass> getClasses() {
		BasicDBList resultSet = this.getDataList("classes");
		
		List<IProfileEducationClass> list = new ArrayList<IProfileEducationClass>();
		
		
		if(resultSet != null){
			for (Object o : resultSet){
				list.add(new ProfileEducationClassJsonMongo((DBObject) o));
			}
		}
		
		return list;
	}
	
	

}
