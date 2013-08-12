package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.likes;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.likes.ILike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractTimedDomainJsonImport;

public class LikeJsonImport extends AbstractTimedDomainJsonImport implements ILike{

	public LikeJsonImport(JSONObject job) {
		super(job);
	}

	public String getCategory() {
		return this.job.get("category").toString();
	}
	

}
