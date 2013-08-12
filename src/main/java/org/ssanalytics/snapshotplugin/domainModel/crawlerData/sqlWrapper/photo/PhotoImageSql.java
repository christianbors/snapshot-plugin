package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.photo;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoImage;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractBaseDomainSql;

public class PhotoImageSql extends AbstractBaseDomainSql implements IPhotoImage {

	public Integer height;
	public Integer width;
	public String source;
	
	public PhotoImageSql(Integer height, Integer width, String source) {
		super();
		
		this.height = height;
		this.width = width;
		this.source = source;
		
	}

	public Integer getHeight() {
		return this.height;
	}

	public Integer getWidth() {
		return this.width;
	}

	public String getSource() {
		return this.source;
	}

}
