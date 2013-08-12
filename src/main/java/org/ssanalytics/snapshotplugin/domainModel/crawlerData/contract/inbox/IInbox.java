package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.inbox;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IIdentityDomain;

public interface IInbox extends IIdentityDomain{

	public ISharedComments getComments();
	public ISharedFrom getFrom();
	
	public ISharedTo getTo();
	
	public Integer getUnseen();
	public Integer getUnread();
	public long getUpdated_time();
        
        public String getMessage();
}
