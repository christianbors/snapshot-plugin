package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile;

import java.util.List;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IIdentityDomain;

public interface IProfile extends IIdentityDomain {
	
	public String getBirthday();
	public String getFirstName();
	public String getLastName();
	public String getName();
	public String getGender();
	public String getLink();
	public String getLocale();
	public String getUsername();
	public long getUpdatedTime();
	
	
	public IProfileLocation getLocation();	
	public IProfileHometown getHometown();
	
	public List<IProfileLanguage> getLanguageList();
	public List<IProfileWork> getWorkList();
	public List<IProfileEducation> getEducationList();
	
	

}
