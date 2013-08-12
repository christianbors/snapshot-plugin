package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared;
import java.util.List;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IBaseDomain;

public interface ISharedTo extends IBaseDomain{
	
	public List<ISharedToData> getToDataList();	

}
