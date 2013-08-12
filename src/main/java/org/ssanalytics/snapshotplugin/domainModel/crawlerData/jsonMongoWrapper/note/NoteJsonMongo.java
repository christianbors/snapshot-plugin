package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.note;



import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.note.INote;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedCommentsJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedFromJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractIdentityDomainJsonMongo;

public class NoteJsonMongo extends AbstractIdentityDomainJsonMongo implements INote{

	public NoteJsonMongo(com.mongodb.DBObject job) {
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

	public Long getCreated_time() {
		// TODO parse time
		return null;
	}

	public Long getUpdated_time() {
		// TODO parse time
		return null;
	}

	public String getIcon() {
		return getDataString("icon");
	}

	public String getSubject() {
		return getDataString("subject");
	}

	public String getMessage() {
		return getDataString("message");
	}

}
