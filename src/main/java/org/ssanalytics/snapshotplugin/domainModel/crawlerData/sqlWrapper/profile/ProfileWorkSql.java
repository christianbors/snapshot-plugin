package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.profile;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileWork;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileWorkEmployer;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractBaseDomainSql;

public class ProfileWorkSql extends AbstractBaseDomainSql implements IProfileWork{

	IProfileWorkEmployer employer;
	String start_date;
	String end_date;
	
	public ProfileWorkSql(IProfileWorkEmployer employer, String start_date, String end_date){
		
		super();
		this.employer = employer;
		this.start_date = start_date;
		this.end_date = end_date;
	}
	

	public IProfileWorkEmployer getEmployer() {
		
		return this.employer;
	}


	@Override
	public String getStartDate() {
		return this.start_date;
	}


	@Override
	public String getEndDate() {
		return this.end_date;
	}
	

}
