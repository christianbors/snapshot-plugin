package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.video;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideoFormat;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractBaseDomainSql;

public class VideoFormatSql extends AbstractBaseDomainSql implements IVideoFormat{

	
	private String embed_html;
	private String filter;
	private Integer height;
	private Integer width;
	private String picture;
	
	public VideoFormatSql(String embed_html, String filter, String picture, Integer height, Integer width) {
		super();
		this.embed_html = embed_html;
		this.filter = filter;
		this.height = height;
		this.width = width;
		this.picture = picture;
	}


	public String getEmbed_html() {
		return this.embed_html;
	}


	public String getFilter() {
		return this.filter;
	}


	public Integer getHeight() {
		return this.height;
	}


	public Integer getWidth() {
		return this.width;
	}


	public String getPicture() {
		return this.picture;
	}

}
