package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.video;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideoFormat;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractBaseDomainJsonImport;

public class VideoFormatJsonImport extends AbstractBaseDomainJsonImport implements IVideoFormat{

	public VideoFormatJsonImport(JSONObject job) {
		super(job);
	}

	public String getEmbed_html() {
		return this.job.get("embed_html").toString();
	}

	public String getFilter() {
		return this.job.get("filter").toString();
	}

	public Integer getHeight() {
		try{
			return new Integer(this.job.get("height").toString());
		}catch(Exception ex){
			return null;
		}
	}

	public Integer getWidth() {
		try{
			return new Integer(this.job.get("width").toString());
		}catch(Exception ex){
			return null;
		}
	}

	public String getPicture() {
		return this.job.get("picture").toString();
	}

	
}
