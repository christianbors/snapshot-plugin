package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.profile;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationClass;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractNamedDomainSql;

public class ProfileEducationClassSql extends AbstractNamedDomainSql implements IProfileEducationClass{

	public ProfileEducationClassSql(String id, String name) {
		super(id, name);
	}

}
