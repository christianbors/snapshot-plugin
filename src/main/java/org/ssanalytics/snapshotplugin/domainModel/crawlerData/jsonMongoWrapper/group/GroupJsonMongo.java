package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.group;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.group.IGroup;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractNamedDomainJsonMongo;

public class GroupJsonMongo extends AbstractNamedDomainJsonMongo implements IGroup {

	public GroupJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

	public Long getVersion() {
		return this.getDataLong("version");
	}

}
