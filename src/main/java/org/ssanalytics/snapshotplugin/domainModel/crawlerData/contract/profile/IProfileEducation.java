package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile;

import java.util.List;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IBaseDomain;

public interface IProfileEducation extends IBaseDomain{
		
	public String getType();
	public IProfileEducationYear getYear();	
	public IProfileEducationSchool getSchool();

	public List<IProfileEducationConcentration> getConcentrationList();
	public List<IProfileEducationClass> getClasses();

}
