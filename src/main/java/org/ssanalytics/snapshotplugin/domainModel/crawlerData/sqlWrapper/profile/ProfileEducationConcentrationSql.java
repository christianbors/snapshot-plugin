package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.profile;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationConcentration;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractNamedDomainSql;

public class ProfileEducationConcentrationSql extends AbstractNamedDomainSql implements IProfileEducationConcentration{
	
	public ProfileEducationConcentrationSql(String id, String name) {
		
		super(id, name);

	}
}
