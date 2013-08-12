package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ICategorizedDomain;

public abstract class AbstractCategorizedDomainSql extends AbstractTimedDomainSql implements ICategorizedDomain{

	private String category;
	
	public AbstractCategorizedDomainSql(String id, String name,
			long created_time, String category) {
		super(id, name, created_time);
		 this.category = category;
	}

	public String getCategory() {
		return this.category;
	}

}
