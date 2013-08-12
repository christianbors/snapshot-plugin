package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.home;

import java.util.ArrayList;
import java.util.List;



import com.mongodb.BasicDBList;
import com.mongodb.DBObject;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.home.IHome;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPrivacy;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedActionJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedCommentsJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedFromJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedPrivacyJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractIdentityDomainJsonMongo;

public class HomeJsonMongo extends AbstractIdentityDomainJsonMongo implements IHome {

	public HomeJsonMongo(com.mongodb.DBObject job) {
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
		com.mongodb.DBObject temp = getDataDbo("comments");
		return temp == null ? null : new SharedCommentsJsonMongo(temp);
	}

	public Long getCreated_time() {
		// TODO: parse long
		return null;
	}

	public ISharedFrom getFrom() {
		com.mongodb.DBObject temp = getDataDbo("from");
		return temp == null ? null : new SharedFromJsonMongo(temp);
	}

	public String getMessage() {
		return getDataString("message");
	}

	public String getType() {
		return getDataString("type");
	}

	public Long getUpdated_time() {
		return null;
	}

    @Override
    public ISharedPrivacy getPricacy() {
        DBObject temp = this.getDataDbo("privacy");
        return temp == null ? null : new SharedPrivacyJsonMongo(dbo);
    }

}
