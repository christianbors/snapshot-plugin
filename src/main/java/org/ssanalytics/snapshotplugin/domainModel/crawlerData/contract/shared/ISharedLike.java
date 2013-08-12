package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared;

import java.util.List;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IBaseDomain;

public interface ISharedLike extends IBaseDomain{

	public Integer getCount();
	public List<ISharedLikeData> getDataList();
	public ISharedPaging getPaging();
}
