package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo;
import java.util.List;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IBaseDomain;

public interface IPhotoTag extends IBaseDomain{
	
	public List<IPhotoTagData> getTagDataList(); 

}
