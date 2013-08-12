package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.inbox;



import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.inbox.IInbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedCommentsJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedFromJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedToJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractIdentityDomainJsonMongo;

public class InboxJsonMongo extends AbstractIdentityDomainJsonMongo implements IInbox {

	public InboxJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

	public ISharedComments getComments() {
		com.mongodb.DBObject temp = this.getDataDbo("comments");
		return temp == null ? null : new SharedCommentsJsonMongo(temp);
	}

	public ISharedFrom getFrom() {
		com.mongodb.DBObject temp = this.getDataDbo("from");
		return temp == null ? null : new SharedFromJsonMongo(temp);
	}

	public ISharedTo getTo() {
		com.mongodb.DBObject temp = this.getDataDbo("to");
		return temp == null ? null : new SharedToJsonMongo(temp);
	}

	public Integer getUnseen() {
		return this.getDataInteger("unseen");
	}

	public Integer getUnread() {
		return this.getDataInteger("unread");
	}

	public long getUpdated_time() {
		return this.getDataTime("updated_time");
	}

    @Override
    public String getMessage() {
       return this.getDataString("message");
    }

}
