package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.tagged;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged.ITaggedProperties;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractBaseDomainSql;

public class TaggedPropertiesSql extends AbstractBaseDomainSql implements ITaggedProperties{

	
	private String href;
	private String name;
	private String text;
	
	public TaggedPropertiesSql(String href, String name, String text) {
		super();
		
		this.href = href;
		this.name = name;
		this.text = text;
	}

	public String getHref() {
		return this.href;
	}

	public String getName() {
		return this.name;
	}

	public String getText() {
		return this.text;
	}

}
