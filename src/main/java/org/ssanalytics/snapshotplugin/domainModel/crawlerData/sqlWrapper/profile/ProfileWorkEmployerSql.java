package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.profile;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileWorkEmployer;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractNamedDomainSql;

public class ProfileWorkEmployerSql extends AbstractNamedDomainSql implements IProfileWorkEmployer{
	
	public ProfileWorkEmployerSql(String id, String name){
		
		super(id, name);		
	}
}
