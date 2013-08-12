package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.interest;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.interest.IInterest;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractCategorizedDomainJsonImport;

public class InterestJsonImport extends AbstractCategorizedDomainJsonImport implements IInterest {

	public InterestJsonImport(JSONObject job) {
		super(job);
	}
}
