package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IBaseDomain;

public interface IProfileWork extends IBaseDomain{

	public IProfileWorkEmployer getEmployer();
	public String getStartDate();
	public String getEndDate();
}
