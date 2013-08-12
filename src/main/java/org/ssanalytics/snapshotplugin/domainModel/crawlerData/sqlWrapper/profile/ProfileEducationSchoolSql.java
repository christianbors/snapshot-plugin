package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.profile;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationSchool;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractNamedDomainSql;

public class ProfileEducationSchoolSql extends AbstractNamedDomainSql implements IProfileEducationSchool{
	
	public ProfileEducationSchoolSql(String id, String name) {
		
		super(id, name);
	}

}
