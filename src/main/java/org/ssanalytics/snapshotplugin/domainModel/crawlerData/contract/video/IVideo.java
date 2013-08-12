package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video;

import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IIdentityDomain;

public interface IVideo extends IIdentityDomain{
	
	public long getUpdated_time();
	public IVideoTags getTags();
	
	public String getSource();
	public String getPicture();
	public String getIcon();
	
	public ISharedFrom getFrom();
	
	public String getEmbed_html();
	public String getDescription();
	public long getCreated_time();
	
	public ISharedComments getComments();
        
        public List<IVideoFormat> getFormatList();
	
}
