package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.link;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPrivacy;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ITimedDomain;

public interface ILink extends ITimedDomain{
	
	public ISharedFrom getFrom();
	public String getIcon();
	public String getLink();
	public String getPicture();
	public String getDescription();
	public String getMessage();
	
	public ISharedComments getComments();
        public ISharedPrivacy getPrivacy();

}
