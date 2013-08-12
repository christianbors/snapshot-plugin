package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.profile;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationYear;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractNamedDomainSql;

public class ProfileEducationYearSql extends AbstractNamedDomainSql implements IProfileEducationYear{
	
	public ProfileEducationYearSql(String id, String name) {
		
		super(id, name);
	}

}
