package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.tagged;



import com.mongodb.DBObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged.ITaggedApplication;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractNamedDomainJsonMongo;

public class TaggedApplicationJsonMongo extends AbstractNamedDomainJsonMongo implements ITaggedApplication{

	public TaggedApplicationJsonMongo(DBObject job) {
		super(job);
	}

	public String getNamespace() {
		return getDataString("namespace");
	}

}
