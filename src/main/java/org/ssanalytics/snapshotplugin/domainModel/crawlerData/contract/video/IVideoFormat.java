package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IBaseDomain;

public interface IVideoFormat extends IBaseDomain {
	
	public String getEmbed_html();
	public String getFilter();
	public Integer getHeight();
	public Integer getWidth();
	public String getPicture();

}
