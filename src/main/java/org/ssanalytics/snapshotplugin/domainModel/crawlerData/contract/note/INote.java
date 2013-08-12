package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.note;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IIdentityDomain;

public interface INote extends IIdentityDomain{

	public ISharedComments getComments();
	public ISharedFrom getFrom();
	
	public Long getCreated_time();
	public Long getUpdated_time();
	
	public String getIcon();
	public String getSubject();
	public String getMessage();	
}
