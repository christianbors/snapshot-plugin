package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.video;

import com.mongodb.DBObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideoFormat;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractBaseDomainJsonMongo;

public class VideoFormatJsonMongo extends AbstractBaseDomainJsonMongo implements IVideoFormat{

	public VideoFormatJsonMongo(DBObject job) {
		super(job);
	}

	public String getEmbed_html() {
		return this.getDataString("embed_html");
	}

	public String getFilter() {
		return this.getDataString("filter");
	}

	public Integer getHeight() {
		return this.getDataInteger("height");
	}

	public Integer getWidth() {
		return this.getDataInteger("width");
	}

	public String getPicture() {
		return this.getDataString("picture");
	}

	
}
