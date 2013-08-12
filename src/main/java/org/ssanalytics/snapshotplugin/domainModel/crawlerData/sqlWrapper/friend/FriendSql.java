package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.friend;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractNamedDomainSql;

public class FriendSql extends AbstractNamedDomainSql implements IFriend{

	public FriendSql(String id, String name) {
		super(id, name);
	}

}
