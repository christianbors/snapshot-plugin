package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.profile;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileHometown;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractNamedDomainSql;

public class ProfileHomeTownSql extends AbstractNamedDomainSql implements IProfileHometown{
	
	
	public ProfileHomeTownSql(String id, String name) {
		
		super(id, name);
	}

}
