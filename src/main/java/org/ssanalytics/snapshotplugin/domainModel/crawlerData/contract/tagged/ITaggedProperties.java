package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IBaseDomain;

public interface ITaggedProperties extends IBaseDomain{
	
	public String getHref();
	public String getName();
	public String getText();

}
