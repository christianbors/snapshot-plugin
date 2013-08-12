package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo;

import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ITimedDomain;

public interface IPhoto extends ITimedDomain {
	

	public String getLink();
	public String getName();
	public Long getUpdated_time();
	
	
	public Integer getHeight();
	public Integer getWidth();
	public Integer getPosition();
	public String getIcon();
	public String getPicture();
	public String getSource();	
	
	public List<IPhotoImage> getImageList();
	
	public IPhotoTag getPhotoTags();
	
	
	public ISharedFrom getFrom();
	public ISharedLike getLikes();
        public ISharedPlace getPlace();
        public ISharedComments getComments();
	

}
