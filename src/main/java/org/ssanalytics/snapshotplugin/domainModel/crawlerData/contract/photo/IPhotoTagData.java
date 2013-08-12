package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ITimedDomain;

public interface IPhotoTagData extends ITimedDomain{
	
	public Double getX();
	public Double getY();

}
