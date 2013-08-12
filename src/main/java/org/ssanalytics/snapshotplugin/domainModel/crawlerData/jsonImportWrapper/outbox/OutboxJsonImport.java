package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.outbox;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.outbox.IOutbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedCommentsJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedFromJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedToJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractIdentityDomainJsonImport;

public class OutboxJsonImport extends AbstractIdentityDomainJsonImport implements IOutbox {

	public OutboxJsonImport(JSONObject job) {
		super(job);
	}

	public ISharedComments getComments() {
		JSONObject temp = (JSONObject) this.job.get("comments");
		
		return temp == null ? null : new SharedCommentsJsonImport(temp);
	}

	public ISharedFrom getFrom() {
		JSONObject temp = (JSONObject) this.job.get("from");		
		
		return temp != null ? new SharedFromJsonImport(temp) : null;
	}

	public String getMessage() {
		return this.getString("message");
	}

	public ISharedTo getTo() {
		JSONObject temp = this.getJob("to");
                return temp == null ? null : new SharedToJsonImport(temp);
	}

	public Integer getUnseen() {
		try{
			return new Integer(this.job.get("unseen").toString());
		} catch(Exception ex){
			return null;
		}
	}

	public Integer getUnread() {
		try{
			return new Integer(this.job.get("unread").toString());
		} catch(Exception ex){
			return null;
		}
	}

	public Long getUpdated_time() {
		return this.getTime("updated_time");
	}

}
