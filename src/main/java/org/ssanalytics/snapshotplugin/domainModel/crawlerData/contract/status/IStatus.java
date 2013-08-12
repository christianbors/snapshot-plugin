package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.status;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IIdentityDomain;

public interface IStatus extends IIdentityDomain{

	public ISharedComments getComments();
	public ISharedFrom getFrom();
	
	public ISharedLike getLikes();
	
	public String getMessage();
	public Long getUpdated_time();
        
        public ISharedPlace getPlace();
}
