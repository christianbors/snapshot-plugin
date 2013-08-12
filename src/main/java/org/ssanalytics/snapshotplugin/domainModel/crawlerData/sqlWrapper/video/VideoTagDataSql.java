package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.video;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideoTagData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractTimedDomainSql;

public class VideoTagDataSql extends AbstractTimedDomainSql implements IVideoTagData{

	public VideoTagDataSql(String id, String name, long created_time) {
		super(id, name, created_time);
	}


	
}
