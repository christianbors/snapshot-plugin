package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedToData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractBaseDomainJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractNamedDomainJsonMongo;

public class SharedToDataJsonMongo extends AbstractBaseDomainJsonMongo implements ISharedToData {

	public SharedToDataJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}	

    @Override
    public String getName() {
        return this.getString("name");
    }

    @Override
    public String getId() {
        return this.getString("id");
    }

}
