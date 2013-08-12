package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.profile;

import java.util.List;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducation;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileHometown;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileLanguage;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileLocation;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileWork;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractIdentityDomainSql;

public class ProfileSql extends AbstractIdentityDomainSql implements IProfile{

	
	private String birthday;
	private String firstName;
	private String lastName;
	private String name;
	private String gender;
	private String link;
	private String locale;
	private String username;
	private Long updated_time;
	private IProfileLocation location;
	private IProfileHometown hometown;
	private List<IProfileLanguage> languageList;
	private List<IProfileWork> workList;
	private List<IProfileEducation> educationList;
	


	public ProfileSql(String id, String birthday, String firstName, String lastName, String name, String gender, String link, String locale, String username, Long updated_time, IProfileLocation location, IProfileHometown hometown, List<IProfileLanguage> languageList, List<IProfileWork> workList, List<IProfileEducation> educationList) {

		super(id);
		
		this.birthday = birthday;
		this.firstName = firstName;
		this.lastName = lastName;
		this.name = name;
		this.gender = gender;
		this.link = link;
		this.locale = locale;
		this.username = username;
		this.updated_time = updated_time;
		this.location = location;
		this.hometown = hometown;
		this.languageList = languageList;
		this.workList = workList;
		this.educationList = educationList;
				

	}

	

	@Override
	public List<IProfileWork> getWorkList() {
		return this.workList;
	}

	@Override
	public List<IProfileEducation> getEducationList() {
		return this.educationList;
	}



	@Override
	public String getBirthday() {
		return this.birthday;
	}



	@Override
	public String getFirstName() {
		return this.firstName;
	}



	@Override
	public String getLastName() {
		return this.lastName;
	}



	@Override
	public String getName() {
		return this.name;
	}



	@Override
	public String getGender() {
		return this.gender;
	}



	@Override
	public String getLink() {
		return this.link;
	}



	@Override
	public String getLocale() {
		return this.locale;
	}



	@Override
	public String getUsername() {
		return this.username;
	}



	@Override
	public long getUpdatedTime() {
		return this.updated_time;
	}



	@Override
	public IProfileLocation getLocation() {
		return this.location;
	}



	@Override
	public IProfileHometown getHometown() {
		return this.hometown;
	}



	@Override
	public List<IProfileLanguage> getLanguageList() {
		return this.languageList;
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
