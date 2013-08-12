package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IIdentityDomain;

public interface ISharedCommentData extends IIdentityDomain{

	public ISharedFrom getFrom();
	public String getMessage();
	public long getCreatedTime();
	public Integer getLikes();
}
