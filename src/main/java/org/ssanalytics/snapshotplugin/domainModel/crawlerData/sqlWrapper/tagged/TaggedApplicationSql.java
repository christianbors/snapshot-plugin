package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.tagged;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged.ITaggedApplication;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractNamedDomainSql;

public class TaggedApplicationSql extends AbstractNamedDomainSql implements ITaggedApplication{

	private String namespace;
	
	public TaggedApplicationSql(String id, String name, String namespace) {
		super(id, name);
		
		this.namespace = namespace;
	}

	public String getNamespace() {
		return this.namespace;
	}

}
