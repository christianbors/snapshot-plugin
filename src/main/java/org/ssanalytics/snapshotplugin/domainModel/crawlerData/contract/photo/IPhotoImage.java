package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IBaseDomain;

public interface IPhotoImage extends IBaseDomain{
	
	public Integer getHeight();
	public Integer getWidth();
	public String getSource();

}
