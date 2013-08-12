package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.home;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.home.IHome;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPrivacy;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedActionJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedCommentsJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedFromJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedPrivacyJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractIdentityDomainJsonImport;

public class HomeJsonImport extends AbstractIdentityDomainJsonImport implements IHome {

	public HomeJsonImport(JSONObject job) {
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
		JSONObject temp = getJob("comments");
		return temp == null ? null : new SharedCommentsJsonImport(temp);
	}

	public Long getCreated_time() {
		// TODO: parse long
		return null;
	}

	public ISharedFrom getFrom() {
		JSONObject temp = getJob("from");
		return temp == null ? null : new SharedFromJsonImport(temp);
	}

	public String getMessage() {
		return getString("message");
	}

	public String getType() {
		return getString("type");
	}

	public Long getUpdated_time() {
		// TODO: parse long
		return null;
	}

    @Override
    public ISharedPrivacy getPricacy() {
        JSONObject temp = this.getJob("pricacy");
        return temp == null ? null : new SharedPrivacyJsonImport(job);
    }

}
