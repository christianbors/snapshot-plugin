package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.feed;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.feed.IFeed;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPrivacy;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedActionJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedCommentsJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedFromJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedPrivacyJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedToJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractIdentityDomainJsonImport;

public class FeedJsonImport extends AbstractIdentityDomainJsonImport implements IFeed {

	public FeedJsonImport(JSONObject job) {
		super(job);
	}

	public List<ISharedAction> getActionList() {
		JSONArray jar = this.getJar("actions");
		
		List<ISharedAction> list = new ArrayList<ISharedAction>();
		
		if (jar != null) {
			for (Object o : jar) {
				list.add(new SharedActionJsonImport((JSONObject) o));
			}
		}
		
		return list;
	}

	public ISharedComments getComments() {
		JSONObject temp = (JSONObject) this.job.get("comments");
		return temp == null ? null : new SharedCommentsJsonImport(temp);
	}

	public Long getCreated_time() {
		return this.getTime("created_time");
	}

	public ISharedFrom getFrom() {
		JSONObject temp = (JSONObject) this.job.get("from");
		return temp == null ? null : new SharedFromJsonImport(temp);
	}

	public String getMessage() {
		return (String) this.job.get("message");
	}

	public ISharedTo getTo() {
		JSONObject temp = (JSONObject) this.job.get("to");
		return temp == null ? null : new SharedToJsonImport(temp);
	}

	public String getType() {
		return this.job.get("type").toString();
	}

	public Long getUpdated_time() {
		//TODO: parse time
		return null;
	}

    @Override
    public ISharedPrivacy getPrivacy() {
        return new SharedPrivacyJsonImport(this.getJob("privacy"));
    }

}
