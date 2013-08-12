package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.video;


import com.mongodb.DBObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideoTagData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractTimedDomainJsonMongo;

public class VideoTagDataJsonMongo extends AbstractTimedDomainJsonMongo implements IVideoTagData{

	public VideoTagDataJsonMongo(DBObject job) {
		super(job);
	}

}
