package org.ssanalytics.snapshotplugin.ui.standalone.controller;

import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;

public class SnapshotSummaryController {
	

	public List<IFriend> getFriendsList(ISnapshotInfo snapshot) throws Exception {
		return DaoFactory.getFriendDAO().getFriendListSpecificVersion(snapshot.getValue(), snapshot.version());
	}
}
