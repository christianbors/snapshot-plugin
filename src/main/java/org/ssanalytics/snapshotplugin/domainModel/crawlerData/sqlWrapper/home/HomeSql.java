package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.home;

import java.util.List;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.home.IHome;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPrivacy;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractIdentityDomainSql;

public class HomeSql extends AbstractIdentityDomainSql implements IHome {

	private List<ISharedAction> actionList;
	private ISharedComments comments;
	private Long created_time;
	private Long updated_time;
	private String type;
	private ISharedFrom from;
	private String message;
        private ISharedPrivacy privacy;
	
	public HomeSql(String id, List<ISharedAction> actionList, ISharedComments comments, Long created_time, ISharedFrom from, String message, String type, Long updated_time, ISharedPrivacy privacy) {
		super(id);
		
		this.actionList = actionList;
		this.comments = comments;
		this.created_time = created_time;
		this.from = from;
		this.message = message;
		this.type = type;
		this.updated_time = updated_time;
                this.privacy = privacy;
	}

	public List<ISharedAction> getActionList() {
		return this.actionList;
	}

	public ISharedComments getComments() {
		return this.comments;
	}

	public Long getCreated_time() {
		return this.created_time;
	}

	public ISharedFrom getFrom() {
		return this.from;
	}

	public String getMessage() {
		return this.message;
	}

	public String getType() {
		return this.type;
	}

	public Long getUpdated_time() {
		return this.updated_time;
	}

    @Override
    public ISharedPrivacy getPricacy() {
        return this.privacy;
    }

}
