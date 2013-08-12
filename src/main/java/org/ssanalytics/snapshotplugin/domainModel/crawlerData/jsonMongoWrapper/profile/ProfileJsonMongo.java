package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.profile;

import java.util.ArrayList;
import java.util.List;


import com.mongodb.BasicDBList;
import com.mongodb.DBObject;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducation;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileHometown;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileLanguage;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileLocation;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileWork;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractNamedDomainJsonMongo;

public class ProfileJsonMongo extends AbstractNamedDomainJsonMongo implements IProfile {

	public ProfileJsonMongo(com.mongodb.DBObject job) {
		
		super(job);		
	}

	public String getBirthday() {

		return this.getDataString("birthday");
	}

	public String getFirstName() {
		
		return this.getDataString("first_name");
	}

	public String getLastName() {
		
		return this.getDataString("last_name");
	}

	public String getGender() {
		
		return this.getDataString("gender");
	}

	public String getLink() {
		
		return this.getDataString("link");
	}

	public String getLocale() {
		
		return this.getDataString("locale");
	}

	public String getUsername() {
		
		return this.getDataString("username");
	}

	public long getUpdatedTime() {
		
		return this.getDataTime("updated_time");
	}

	public IProfileLocation getLocation() {
		
		com.mongodb.DBObject temp = getDataDbo("location");		
		
		return temp != null ? new ProfileLocationJsonMongo(temp) : null;
	}



	public IProfileHometown getHometown() {
		
		com.mongodb.DBObject temp = getDataDbo("hometown");		
		
		return temp != null ? new ProfileHometownJsonMongo(temp) : null;
	}
	
	public List<IProfileEducation> getEducationList() {
		
		BasicDBList resultSet = this.getDataList("education");
		
		List<IProfileEducation> list = new ArrayList<IProfileEducation>();
		
		if(resultSet != null){
			for (Object o : resultSet){
				list.add(new ProfileEducationJsonMongo((DBObject) o));
			}
		}
		
		return list;
	}

	public List<IProfileLanguage> getLanguageList() {
		BasicDBList resultSet = this.getDataList("languages");
		
		List<IProfileLanguage> list = new ArrayList<IProfileLanguage>();
		
		if(resultSet != null){
			for (Object o : resultSet){
				list.add(new ProfileLanguageJsonMongo((DBObject) o));
			}
		}
		
		return list;
	}

	public List<IProfileWork> getWorkList() {
		
		BasicDBList resultSet = this.getDataList("works");
		
		List<IProfileWork> list = new ArrayList<IProfileWork>();
		
		if(resultSet != null){
			for (Object o : resultSet){
				list.add(new ProfileWorkJsonMongo((DBObject) o));
			}
		}
		
		return list;		
	}

	public String toString() {
		return this.getName();
	}
}
