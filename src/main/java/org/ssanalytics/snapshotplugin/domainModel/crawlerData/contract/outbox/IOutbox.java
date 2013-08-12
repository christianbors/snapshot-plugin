package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.outbox;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IIdentityDomain;

public interface IOutbox extends IIdentityDomain{

	public ISharedComments getComments();
	public ISharedFrom getFrom();
	public String getMessage();
	
	public ISharedTo getTo();
	
	public Integer getUnseen();
	public Integer getUnread();
	public Long getUpdated_time();
}
