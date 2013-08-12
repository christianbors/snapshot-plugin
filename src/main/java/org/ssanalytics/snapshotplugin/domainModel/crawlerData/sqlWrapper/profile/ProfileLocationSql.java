package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.profile;



import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileLocation;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractNamedDomainSql;

public class ProfileLocationSql extends AbstractNamedDomainSql implements IProfileLocation {

	public ProfileLocationSql(String id, String name) {
		
		super(id, name);
	}

}
