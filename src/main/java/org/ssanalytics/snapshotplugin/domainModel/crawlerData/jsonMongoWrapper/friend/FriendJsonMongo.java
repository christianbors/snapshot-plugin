package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.friend;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractNamedDomainJsonMongo;

public class FriendJsonMongo extends AbstractNamedDomainJsonMongo implements IFriend{

	public FriendJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

}
