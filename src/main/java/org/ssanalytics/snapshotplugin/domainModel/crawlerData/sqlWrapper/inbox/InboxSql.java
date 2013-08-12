package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.inbox;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.inbox.IInbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractIdentityDomainSql;

public class InboxSql extends AbstractIdentityDomainSql implements IInbox{

	
	private ISharedComments comments;
	private ISharedFrom from;
	private ISharedTo to;
	private Integer unseen;
	private Integer unread;
	private Long updated_time;
        private String message;
	
	public InboxSql(String id, ISharedComments comments, ISharedFrom from, ISharedTo to, Integer unseen, Integer unread, Long updated_time, String message) {
		super(id);
		
		this.comments = comments;
		this.from = from;
		this.to = to;
		this.unseen = unseen;
		this.unread = unread;
		this.updated_time = updated_time;
                this.message = message;
	}

	public ISharedComments getComments() {
		return this.comments;
	}

	public ISharedFrom getFrom() {
		return this.from;
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

	public long getUpdated_time() {
		return this.updated_time;
	}

    @Override
    public String getMessage() {
        return this.message;
    }

}
