package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses;



import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.INamedDomain;

public class AbstractNamedDomainSql extends AbstractIdentityDomainSql implements INamedDomain{

	protected String name;
	
	public AbstractNamedDomainSql(String id, String name){
		super(id);
		this.name = name;
	}


	public String getName() {
		return this.name;
	}
	

	
	
}
