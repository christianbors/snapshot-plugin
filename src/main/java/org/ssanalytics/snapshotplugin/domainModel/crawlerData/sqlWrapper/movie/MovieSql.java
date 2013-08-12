package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.movie;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.movie.IMovie;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractCategorizedDomainSql;

public class MovieSql extends AbstractCategorizedDomainSql implements IMovie{

	public MovieSql(String id, String name, long created_time,
			String category) {
		super(id, name, created_time, category);
	}

}
