package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.feed;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.feed.IFeed;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPrivacy;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedActionJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedCommentsJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedFromJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedPrivacyJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedToJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractIdentityDomainJsonMongo;

public class FeedJsonMongo extends AbstractIdentityDomainJsonMongo implements IFeed {

	public FeedJsonMongo(com.mongodb.DBObject job){
		super(job);
	}

	public List<ISharedAction> getActionList() {
		BasicDBList resultSet = this.getDataList("actions");
		
		List<ISharedAction> list = new ArrayList<ISharedAction>();
		
		if(resultSet != null){
			for (Object o : resultSet){
				list.add(new SharedActionJsonMongo((DBObject) o));
			}
		}
		
		return list;
	}

	public ISharedComments getComments() {
		com.mongodb.DBObject temp = this.getDataDbo("comments");
		return temp == null ? null : new SharedCommentsJsonMongo(temp);
	}

	public Long getCreated_time() {
		return this.getDataTime("created_time");
	}

	public ISharedFrom getFrom() {
		com.mongodb.DBObject temp = this.getDataDbo("from");
		return temp == null ? null : new SharedFromJsonMongo(temp);
	}

	public String getMessage() {
		return this.getDataString("message");
	}

	public ISharedTo getTo() {
		com.mongodb.DBObject temp = this.getDataDbo("to");
		return temp == null ? null : new SharedToJsonMongo(temp);
	}

	public String getType() {
		return this.getDataString("type");
	}

	public Long getUpdated_time() {
		//TODO: parse time
		return null;
	}

    @Override
    public ISharedPrivacy getPrivacy() {
        return new SharedPrivacyJsonMongo(this.getDataDbo("privacy"));
    }

}
