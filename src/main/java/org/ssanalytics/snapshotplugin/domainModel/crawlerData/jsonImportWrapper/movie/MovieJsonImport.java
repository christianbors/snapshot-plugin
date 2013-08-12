package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.movie;

import org.json.simple.JSONObject;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.movie.IMovie;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractCategorizedDomainJsonImport;

public class MovieJsonImport extends AbstractCategorizedDomainJsonImport implements IMovie {

	public MovieJsonImport(JSONObject job) {
		super(job);
	}

}
