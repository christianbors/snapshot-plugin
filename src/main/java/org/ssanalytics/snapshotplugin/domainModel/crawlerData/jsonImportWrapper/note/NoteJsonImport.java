package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.note;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.note.INote;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedCommentsJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedFromJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractIdentityDomainJsonImport;

public class NoteJsonImport extends AbstractIdentityDomainJsonImport implements INote{

	public NoteJsonImport(JSONObject job) {
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

	public Long getCreated_time() {
		// TODO parse time
		return null;
	}

	public Long getUpdated_time() {
		// TODO parse time
		return null;
	}

	public String getIcon() {
		return getString("icon");
	}

	public String getSubject() {
		return getString("subject");
	}

	public String getMessage() {
		return getString("message");
	}

}
