package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedCommentData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractIdentityDomainJsonImport;

public class SharedCommentDataJsonImport extends AbstractIdentityDomainJsonImport implements ISharedCommentData{

	public SharedCommentDataJsonImport(JSONObject job) {
		super(job);
	}

	public ISharedFrom getFrom() {
		JSONObject temp = (JSONObject) this.job.get("from");		
		
		return temp != null ? new SharedFromJsonImport(temp) : null;
	}

	public String getMessage() {
		return this.getString("message");
	}

	public long getCreatedTime() {
		return this.getTime("created_time");
	}

	public Integer getLikes() {
		return this.getInteger("likes");
	}
	

}
