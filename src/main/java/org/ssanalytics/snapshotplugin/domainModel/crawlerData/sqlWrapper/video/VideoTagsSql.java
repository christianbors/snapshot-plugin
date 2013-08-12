package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.video;

import java.util.List;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideoTagData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideoTags;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractBaseDomainSql;

public class VideoTagsSql extends AbstractBaseDomainSql implements IVideoTags{

	private List<IVideoTagData> dataList;
	
	public VideoTagsSql(List<IVideoTagData> dataList) {
		super();
		this.dataList = dataList;
	}

	public List<IVideoTagData> getDataList() {
		return this.dataList;
	}

}
