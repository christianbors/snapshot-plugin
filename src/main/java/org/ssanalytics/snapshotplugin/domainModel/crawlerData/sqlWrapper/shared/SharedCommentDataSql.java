package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedCommentData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractIdentityDomainSql;

public class SharedCommentDataSql extends AbstractIdentityDomainSql implements ISharedCommentData {

	private String message;
	private ISharedFrom from;
	private long created_time;
	private Integer likes;
	
	public SharedCommentDataSql(String id, long created_time, String message, ISharedFrom from, Integer likes) {
		super(id);
		this.message = message;
		this.from = from;
		this.created_time = created_time;
		this.likes = likes;
	}



	public ISharedFrom getFrom() {
		return this.from;
	}



	public String getMessage() {
		return this.message;
	}



	public long getCreatedTime() {
		return this.created_time;
	}



	public Integer getLikes() {
		return this.likes;
	}

}
