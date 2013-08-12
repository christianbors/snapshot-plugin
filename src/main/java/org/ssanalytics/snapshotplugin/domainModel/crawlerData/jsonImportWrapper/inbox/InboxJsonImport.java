package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.inbox;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.inbox.IInbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedCommentsJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedFromJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedToJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractIdentityDomainJsonImport;

public class InboxJsonImport extends AbstractIdentityDomainJsonImport implements IInbox {

	public InboxJsonImport(JSONObject job) {
		super(job);
	}

	public ISharedComments getComments() {
		JSONObject temp = this.getJob("comments");
		return temp == null ? null : new SharedCommentsJsonImport(temp);
	}

	public ISharedFrom getFrom() {
		JSONObject temp = this.getJob("from");
		return temp == null ? null : new SharedFromJsonImport(temp);
	}

	public ISharedTo getTo() {
		JSONObject temp = this.getJob("to");
		return temp == null ? null : new SharedToJsonImport(temp);
	}

	public Integer getUnseen() {
		return this.getInteger("unseen");
	}

	public Integer getUnread() {
		return this.getInteger("unread");
	}

	public long getUpdated_time() {
		return this.getTime("updated_time");
	}

    @Override
    public String getMessage() {
        return this.getString("message");
    }

}
