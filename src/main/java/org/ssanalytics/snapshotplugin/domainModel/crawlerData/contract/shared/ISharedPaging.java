package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IBaseDomain;

public interface ISharedPaging extends IBaseDomain{

	public String getNext();
	public String getPrevious();
}
