package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.profile;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileLanguage;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractNamedDomainSql;

public class ProfileLanguageSql extends AbstractNamedDomainSql implements IProfileLanguage{
	
	public ProfileLanguageSql(String id, String name) {
		
		super(id, name);
	}

}
