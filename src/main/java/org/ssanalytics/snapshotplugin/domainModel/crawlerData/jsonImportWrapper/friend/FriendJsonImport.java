package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.friend;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractNamedDomainJsonImport;

public class FriendJsonImport extends AbstractNamedDomainJsonImport implements IFriend{

	public FriendJsonImport(JSONObject job) {
		super(job);
	}

}
