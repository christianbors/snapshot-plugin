package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.television;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.television.ITelevision;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractTimedDomainSql;

public class TelevisionSql extends AbstractTimedDomainSql implements ITelevision{

	private String category;
	
	public TelevisionSql(String id, String name, long created_time, String category) {
		super(id, name, created_time);
		
		this.category = category;
	}

	public String getCategory() {
		return this.category;
	}

}
