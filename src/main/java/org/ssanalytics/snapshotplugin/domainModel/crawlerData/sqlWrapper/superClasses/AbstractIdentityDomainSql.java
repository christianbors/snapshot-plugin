package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses;



import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IIdentityDomain;

public class AbstractIdentityDomainSql extends AbstractBaseDomainSql implements IIdentityDomain{

	protected String id;
	
	public AbstractIdentityDomainSql(String id){
		super();
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}

}
