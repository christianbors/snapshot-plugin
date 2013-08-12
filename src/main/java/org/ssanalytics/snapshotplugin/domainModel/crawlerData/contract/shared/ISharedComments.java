package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared;

import java.util.List;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IBaseDomain;

public interface ISharedComments extends IBaseDomain{

	public Integer getCount();
	public List<ISharedCommentData> getDataList();
	public ISharedPaging getPaging();
}
