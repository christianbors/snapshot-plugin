package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPaging;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractBaseDomainSql;

public class SharedPagingSql extends AbstractBaseDomainSql implements ISharedPaging {

	
	private String next;
	private String previous;
	
	public SharedPagingSql(String next, String previous) {
		super();
		this.next = next;
		this.previous = previous;
	}


	public String getNext() {
		return this.next;
	}


	public String getPrevious() {
		return this.previous;
	}
	
	

}
