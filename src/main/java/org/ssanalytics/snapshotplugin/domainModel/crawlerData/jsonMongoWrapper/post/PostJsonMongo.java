package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.post;

import java.util.ArrayList;
import java.util.List;


import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.post.IPost;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.post.IPostApplication;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPrivacy;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedActionJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedCommentsJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedFromJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedLikeJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedPlaceJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedPrivacyJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractNamedDomainJsonMongo;

public class PostJsonMongo extends AbstractNamedDomainJsonMongo implements IPost{

	public PostJsonMongo(com.mongodb.DBObject job) {
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
		DBObject temp = this.getDataDbo("comments");
		return temp == null ? null : new SharedCommentsJsonMongo(temp);
	}

	public ISharedFrom getFrom() {
		DBObject temp = this.getDataDbo("from");
		return temp == null ? null : new SharedFromJsonMongo(temp);
	}

	public String getIcon() {
		return getDataString("icon");
	}

	public Long getCreated_time() {
		return this.getDataTime("created_time");
	}

	public Long getUpdated_time() {
		// TODO parse time
		return null;
	}

	public ISharedLike getLikes() {
		DBObject temp = this.getDataDbo("likes");
		return temp == null ? null : new SharedLikeJsonMongo(temp);
	}

	public String getLink() {
		return getDataString("link");
	}

	public String getObject_id() {
		return getDataString("object_id");
	}

	public String getPicture() {
		return getDataString("picture");
	}

	public String getType() {
		return getDataString("type");
	}

	public String getStory() {
		return getDataString("story");
	}

    @Override
    public ISharedPlace getPlace() {
        return new SharedPlaceJsonMongo(this.getDataDbo("place"));
    }

    @Override
    public ISharedPrivacy getPrivacy() {
        return new SharedPrivacyJsonMongo(this.getDataDbo("privacy"));
    }

    @Override
    public String getCaption() {
        return this.getString("caption");
    }

    @Override
    public String getDescription() {
        return this.getString("description");
    }

    @Override
    public String getSource() {
        return this.getString("source");
    }

    @Override
    public IPostApplication getApplication() {
        DBObject temp = this.getDataDbo("application");
        return temp == null ? null : new PostApplicationJsonMongo(dbo);
    }

}
