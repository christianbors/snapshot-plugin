package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.tagged;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged.ITaggedProperties;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractBaseDomainJsonImport;

public class TaggedPropertiesJsonImport extends AbstractBaseDomainJsonImport implements ITaggedProperties{

	public TaggedPropertiesJsonImport(JSONObject job) {
		super(job);
	}

	public String getHref() {
		return getString("href");
	}

	public String getName() {
		return getString("name");
	}

	public String getText() {
		return getString("text");
	}

}
