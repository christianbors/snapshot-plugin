package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IBaseDomain;

public interface ISharedAction extends IBaseDomain{
	
	public String getName();
	public String getLink();

}
