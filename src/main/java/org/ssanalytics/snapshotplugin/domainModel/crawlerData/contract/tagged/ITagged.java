package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged;

import java.util.List;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ITimedDomain;

public interface ITagged extends ITimedDomain{

	public List<ISharedAction> getActions();
        public List<ITaggedProperties> getProperties();
	public ITaggedApplication getApplication();
	public String getCaption();
	public ISharedComments getComments();
	public ISharedFrom getFrom();
	public String getIcon();
	public String getLink();
	public String getPicture();
	public ISharedTo getTo();
	public String getType();
	public Long getUpdated_time();
	public String getDescription();
	public String getMessage();	
}
