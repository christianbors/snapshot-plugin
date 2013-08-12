package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.note;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.note.INote;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractIdentityDomainSql;

public class NoteSql extends AbstractIdentityDomainSql implements INote{

	
	private ISharedComments comments;
	private ISharedFrom from;
	private Long created_time;
	private Long updated_time;
	private String icon;
	private String subject;
	private String message;
	
	public NoteSql(String id, ISharedComments comments, ISharedFrom from, Long created_time, Long updated_time, String icon, String subject, String message) {
		super(id);
		this.comments = comments;
		this.from = from;
		this.updated_time = updated_time;
		this.created_time = created_time;
		this.icon = icon;
		this.subject = subject;
		this.message = message;
	}

	public ISharedComments getComments() {
		return comments;
	}

	public ISharedFrom getFrom() {
		return from;
	}

	public Long getCreated_time() {
		return created_time;
	}

	public Long getUpdated_time() {
		return updated_time;
	}

	public String getIcon() {
		return icon;
	}

	public String getSubject() {
		return subject;
	}

	public String getMessage() {
		return message;
	}


}
