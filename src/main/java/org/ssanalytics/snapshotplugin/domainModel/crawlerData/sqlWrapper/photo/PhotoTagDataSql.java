package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.photo;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoTagData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractTimedDomainSql;

public class PhotoTagDataSql extends AbstractTimedDomainSql implements IPhotoTagData{

	
	private Double x;
	private Double y;
	
	public PhotoTagDataSql(String id, String name, Long created_time, Double x, Double y) {
		super(id, name, created_time);

		this.x = x;
		this.y = y;
	}

	public Double getX() {
		return this.x;
	}

	public Double getY() {
		return this.y;
	}

}
