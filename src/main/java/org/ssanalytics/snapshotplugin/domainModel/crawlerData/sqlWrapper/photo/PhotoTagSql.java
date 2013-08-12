package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.photo;

import java.util.List;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoTag;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoTagData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractBaseDomainSql;

public class PhotoTagSql extends AbstractBaseDomainSql implements IPhotoTag{

	
	private List<IPhotoTagData> tagsList;
	
	
	public PhotoTagSql(List<IPhotoTagData> tagsList) {
		super();
		this.tagsList = tagsList;
	}


	public List<IPhotoTagData> getTagDataList() {
		return this.tagsList;
	}
	
	

}
