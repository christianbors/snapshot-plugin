package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.post;

import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPrivacy;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.INamedDomain;

public interface IPost extends INamedDomain{
	
	public List<ISharedAction> getActionList();
	public ISharedComments getComments();
	public ISharedFrom getFrom();
	public String getIcon();
	public Long getCreated_time();
	public Long getUpdated_time();
	public ISharedLike getLikes();
        
        public ISharedPlace getPlace();
        public ISharedPrivacy getPrivacy();
	
	public String getLink();
	public String getObject_id();
	public String getPicture();
	public String getType();
	public String getStory();
        public String getCaption();
        public String getDescription();
        public String getSource();
        
        public IPostApplication getApplication();

}
