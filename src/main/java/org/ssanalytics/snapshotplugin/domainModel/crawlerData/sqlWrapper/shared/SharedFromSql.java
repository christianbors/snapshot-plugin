package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractNamedDomainSql;

public class SharedFromSql extends AbstractNamedDomainSql implements ISharedFrom{

	public SharedFromSql(String id, String name) {
		super(id, name);
	}

}
