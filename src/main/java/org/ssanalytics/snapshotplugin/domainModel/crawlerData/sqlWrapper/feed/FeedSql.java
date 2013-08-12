package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.feed;

import java.util.List;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.feed.IFeed;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPrivacy;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractIdentityDomainSql;

public class FeedSql extends AbstractIdentityDomainSql implements IFeed{

	
	private List<ISharedAction> actionList;
	private ISharedComments comments;
	private Long created_time;
	private ISharedFrom from;
	private String message;
	private ISharedTo to;
	private String type;
	private Long updated_time;
        private ISharedPrivacy privacy;
	
	
	public FeedSql(String id, List<ISharedAction> actionList, ISharedComments comments, Long created_time, ISharedFrom from, String message, ISharedTo to, String type, Long updated_time, ISharedPrivacy privacy) {
		super(id);
		
		this.actionList = actionList;
		this.comments = comments;
		this.created_time = created_time;
		this.from = from;
		this.message = message;
		this.to = to;
		this.type = type;
		this.updated_time = updated_time;
                this.privacy = privacy;
	}


	public List<ISharedAction> getActionList() {
		return actionList;
	}


	public ISharedComments getComments() {
		return comments;
	}


	public Long getCreated_time() {
		return created_time;
	}


	public ISharedFrom getFrom() {
		return from;
	}


	public String getMessage() {
		return message;
	}


	public ISharedTo getTo() {
		return to;
	}


	public String getType() {
		return type;
	}


	public Long getUpdated_time() {
		return updated_time;
	}

    @Override
    public ISharedPrivacy getPrivacy() {
        return this.privacy;
    }
}
