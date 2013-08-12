package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.tagged;


import com.mongodb.DBObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged.ITaggedProperties;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractBaseDomainJsonMongo;

public class TaggedPropertiesJsonMongo extends AbstractBaseDomainJsonMongo implements ITaggedProperties{

	public TaggedPropertiesJsonMongo(DBObject job) {
		super(job);
	}

	public String getHref() {
		return getDataString("href");
	}

	public String getName() {
		return getDataString("name");
	}

	public String getText() {
		return getDataString("text");
	}

}
