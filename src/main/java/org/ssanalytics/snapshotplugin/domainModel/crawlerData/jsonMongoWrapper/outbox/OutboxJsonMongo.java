package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.outbox;



import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.outbox.IOutbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedCommentsJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedFromJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedToJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractIdentityDomainJsonMongo;

public class OutboxJsonMongo extends AbstractIdentityDomainJsonMongo implements IOutbox {

	public OutboxJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

	public ISharedComments getComments() {
		com.mongodb.DBObject temp = this.getDataDbo("comments");
		
		return temp == null ? null : new SharedCommentsJsonMongo(temp);
	}

	public ISharedFrom getFrom() {
		com.mongodb.DBObject temp = this.getDataDbo("from");		
		
		return temp != null ? new SharedFromJsonMongo(temp) : null;
	}

	public String getMessage() {
		return this.getDataString("message");
	}

	public ISharedTo getTo() {
		com.mongodb.DBObject temp = this.getDataDbo("to");		
		
		return temp != null ? new SharedToJsonMongo(temp) : null;
	}

	public Integer getUnseen() {
		return this.getDataInteger("unseen");
	}

	public Integer getUnread() {
		return this.getDataInteger("unread");
	}

	public Long getUpdated_time() {
		return this.getDataTime("updated_time");
	}

}
