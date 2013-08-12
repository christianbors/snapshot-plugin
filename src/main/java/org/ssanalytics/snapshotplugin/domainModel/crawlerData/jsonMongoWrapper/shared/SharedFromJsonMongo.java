package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractBaseDomainJsonMongo;

public class SharedFromJsonMongo extends AbstractBaseDomainJsonMongo implements ISharedFrom{

	public SharedFromJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

    @Override
    public String getName() {
        return (String) this.dbo.get("name");
    }

    @Override
    public String getId() {
        return (String) this.dbo.get("id");
    }
}
