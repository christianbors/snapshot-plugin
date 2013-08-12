package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.feed;

import java.util.List;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPrivacy;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IIdentityDomain;

public interface IFeed extends IIdentityDomain{
	
	public List<ISharedAction> getActionList();
	public ISharedComments getComments();
	public Long getCreated_time();
	public ISharedFrom getFrom();
	public String getMessage();
	public ISharedTo getTo();
	public String getType();
	public Long getUpdated_time();
        
        public ISharedPrivacy getPrivacy();

}
