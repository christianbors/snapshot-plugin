package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.profile;


import java.util.List;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducation;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationClass;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationConcentration;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationSchool;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationYear;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractBaseDomainSql;

public class ProfileEducationSql extends AbstractBaseDomainSql implements IProfileEducation {

	
	private String type;
	private IProfileEducationYear year;	
	private IProfileEducationSchool school;
	
	private List<IProfileEducationClass> classList;
	private List<IProfileEducationConcentration> concentrationlist;
	
	public ProfileEducationSql(String type, IProfileEducationYear year, IProfileEducationSchool school, List<IProfileEducationClass> classList, List<IProfileEducationConcentration> concentrationList) {
		
		super();
		
		this.type = type;
		this.year = year;
		this.school = school;
		this.classList = classList;
		this.concentrationlist = concentrationList;
	}


	public String getType() {
		return this.type;
	}


	public IProfileEducationYear getYear() {
		return this.year;
	}


	public List<IProfileEducationConcentration> getConcentrationList() {
		return this.concentrationlist;
	}


	public IProfileEducationSchool getSchool() {
		return this.school;
	}


	@Override
	public List<IProfileEducationClass> getClasses() {
		return this.classList;
	}
}
