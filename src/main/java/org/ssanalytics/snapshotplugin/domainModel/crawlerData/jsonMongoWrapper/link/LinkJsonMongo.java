package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.link;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.link.ILink;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPrivacy;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedCommentsJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedFromJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedPrivacyJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractTimedDomainJsonMongo;

public class LinkJsonMongo extends AbstractTimedDomainJsonMongo implements ILink {

	public LinkJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

	public ISharedFrom getFrom() {
		
		com.mongodb.DBObject temp = this.getDataDbo("from");
		if(temp == null)
			return null;
		
		return new SharedFromJsonMongo(temp);
	}

	public String getIcon() {
		return this.getDataString("icon");
	}

	public String getLink() {
		return this.getDataString("link");
	}

	public String getPicture() {
		return this.getDataString("picture");
	}

	public String getDescription() {
		return this.getDataString("description");
	}

	public String getMessage() {
		return this.getDataString("message");
	}

	public ISharedComments getComments() {
		
		com.mongodb.DBObject temp = this.getDataDbo("comments");
		
		return temp == null ? null : new SharedCommentsJsonMongo(temp);
	}

    @Override
    public ISharedPrivacy getPrivacy() {
        return new SharedPrivacyJsonMongo(this.getDataDbo("privacy"));
    }
}
