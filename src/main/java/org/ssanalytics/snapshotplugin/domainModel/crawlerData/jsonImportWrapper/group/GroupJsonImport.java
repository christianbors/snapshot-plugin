package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.group;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.group.IGroup;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractNamedDomainJsonImport;

public class GroupJsonImport extends AbstractNamedDomainJsonImport implements IGroup {

	public GroupJsonImport(JSONObject job) {
		super(job);
	}

	public Long getVersion() {
		return this.getLong("version");
	}

}
