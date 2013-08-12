package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video;

import java.util.List;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IBaseDomain;

public interface IVideoTags extends IBaseDomain{

	public List<IVideoTagData> getDataList();
}
