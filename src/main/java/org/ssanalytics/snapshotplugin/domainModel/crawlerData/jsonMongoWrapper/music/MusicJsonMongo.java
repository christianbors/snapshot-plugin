package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.music;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.music.IMusic;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractCategorizedDomainJsonMongo;

public class MusicJsonMongo extends AbstractCategorizedDomainJsonMongo implements IMusic{

	public MusicJsonMongo(com.mongodb.DBObject job) {
		super(job);	
		}
}
