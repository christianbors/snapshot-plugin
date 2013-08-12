package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.likes;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.likes.ILike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractTimedDomainSql;


public class LikeSql extends AbstractTimedDomainSql implements ILike {

	private String category;
	
	public LikeSql(String id, String name, long created_time, String category) {
		super(id, name, created_time);
	}


	public String getCategory() {
		return this.category;
	}
	
	

}
