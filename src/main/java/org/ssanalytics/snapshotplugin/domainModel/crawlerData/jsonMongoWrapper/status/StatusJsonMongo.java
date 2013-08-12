package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.status;

import com.mongodb.DBObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.status.IStatus;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedCommentsJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedFromJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedLikeJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedPlaceJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractIdentityDomainJsonMongo;

public class StatusJsonMongo extends AbstractIdentityDomainJsonMongo implements IStatus{

	public StatusJsonMongo(DBObject job) {
		super(job);
	}

	public ISharedComments getComments() {
		com.mongodb.DBObject temp = getDataDbo("comments");
		return temp == null ? null : new SharedCommentsJsonMongo(temp);
	}

	public ISharedFrom getFrom() {
		com.mongodb.DBObject temp = getDataDbo("from");
		return temp == null ? null : new SharedFromJsonMongo(temp);
	}

	public ISharedLike getLikes() {
		com.mongodb.DBObject temp = getDataDbo("likes");
		return temp == null ? null : new SharedLikeJsonMongo(temp);
	}

	public String getMessage() {
		return getDataString("message");
	}

	public Long getUpdated_time() {
		// TODO parse time
		return null;
	}

    @Override
    public ISharedPlace getPlace() {
        DBObject temp = this.getDataDbo("place");
        if(temp != null)
            return new SharedPlaceJsonMongo(temp);
        else
            return null;
    }
	
	

}
