package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.interest;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.interest.IInterest;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractCategorizedDomainSql;

public class InterestSql extends AbstractCategorizedDomainSql implements IInterest{

	public InterestSql(String id, String name, long created_time,
			String category) {
		super(id, name, created_time, category);
	}

}
