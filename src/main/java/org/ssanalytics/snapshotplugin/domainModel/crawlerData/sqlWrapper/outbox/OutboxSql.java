package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.outbox;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.outbox.IOutbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractIdentityDomainSql;

public class OutboxSql extends AbstractIdentityDomainSql implements IOutbox{
	
	private ISharedComments comments;
	private ISharedFrom from;
	private String message;
	private ISharedTo to;
	private Integer unseen;
	private Integer unread;
	private Long updated_time;
	
	public OutboxSql(String id, ISharedComments comments, ISharedFrom from, ISharedTo to, String message, Integer unseen, Integer unread, Long updated_time) {
		super(id);
		
		this.comments = comments;
		this.from = from;
		this.to = to;
		this.message = message;
		this.unseen = unseen;
		this.unread = unread;
		this.updated_time = updated_time;
		
	}

	public ISharedComments getComments() {
		return this.comments;
	}

	public ISharedFrom getFrom() {
		return this.from;
	}

	public String getMessage() {
		return this.message;
	}

	public ISharedTo getTo() {
		return this.to;
	}

	public Integer getUnseen() {
		return this.unseen;
	}

	public Integer getUnread() {
		return this.unread;
	}

	public Long getUpdated_time() {
		return this.updated_time;
	}

}
