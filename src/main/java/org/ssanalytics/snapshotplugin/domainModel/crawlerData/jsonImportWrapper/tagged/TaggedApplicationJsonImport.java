package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.tagged;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged.ITaggedApplication;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractNamedDomainJsonImport;

public class TaggedApplicationJsonImport extends AbstractNamedDomainJsonImport implements ITaggedApplication{

	public TaggedApplicationJsonImport(JSONObject job) {
		super(job);
	}

	public String getNamespace() {
		return getString("namespace");
	}

}
