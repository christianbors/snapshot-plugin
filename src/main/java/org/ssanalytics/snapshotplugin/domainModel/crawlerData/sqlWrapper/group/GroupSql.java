package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.group;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.group.IGroup;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractNamedDomainSql;

public class GroupSql extends AbstractNamedDomainSql implements IGroup{

	
	private Long version;
	
	public GroupSql(String id, String name, Long version) {
		super(id, name);
		this.version = version;
	}

	public Long getVersion() {
		return this.version;
	}

}
