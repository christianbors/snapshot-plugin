package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.status;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.status.IStatus;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedCommentsJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedFromJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedLikeJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedPlaceJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractIdentityDomainJsonImport;

public class StatusJsonImport extends AbstractIdentityDomainJsonImport implements IStatus{

	public StatusJsonImport(JSONObject job) {
		super(job);
	}

	public ISharedComments getComments() {
		JSONObject temp = getJob("comments");
		return temp == null ? null : new SharedCommentsJsonImport(temp);
	}

	public ISharedFrom getFrom() {
		JSONObject temp = getJob("from");
		return temp == null ? null : new SharedFromJsonImport(temp);
	}

	public ISharedLike getLikes() {
		JSONObject temp = getJob("likes");
		return temp == null ? null : new SharedLikeJsonImport(temp);
	}

	public String getMessage() {
		return getString("message");
	}

	public Long getUpdated_time() {
		return this.getTime("updated_time");
	}

    @Override
    public ISharedPlace getPlace() {
        return new SharedPlaceJsonImport(this.getJob("place"));
    }
	
	

}
