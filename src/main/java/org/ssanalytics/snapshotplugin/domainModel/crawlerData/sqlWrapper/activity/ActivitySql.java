package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.activity;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.activity.IActivity;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractTimedDomainSql;

public class ActivitySql extends AbstractTimedDomainSql implements IActivity {

	private String category;
	
	public ActivitySql(String id, String name, long created_time, String category) {
		super(id, name, created_time);
		this.category = category;

	}

	public String getCategory() {
		return category;
	}

}
