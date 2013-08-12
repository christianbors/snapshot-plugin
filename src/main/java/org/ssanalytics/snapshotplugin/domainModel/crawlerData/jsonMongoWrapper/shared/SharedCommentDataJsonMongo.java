package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedCommentData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractIdentityDomainJsonMongo;

public class SharedCommentDataJsonMongo extends AbstractIdentityDomainJsonMongo implements ISharedCommentData{

	public SharedCommentDataJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

	public ISharedFrom getFrom() {
		com.mongodb.DBObject temp = getDbo("from");
		
		return temp != null ? new SharedFromJsonMongo(temp) : null;
	}

	public String getMessage() {
		return getString("message");
	}

	public long getCreatedTime() {
		return this.getTime("created_time");
	}

	public Integer getLikes() {
		return getInteger("likes");
	}
	

}
