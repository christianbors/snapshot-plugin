package org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract;

import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;

public interface IRootAccountDAO {

    public Map<String, List<String>> getFriendIsFriend(String snapshotName, String version) throws Exception;

    public List<IFriend> getFriendList(String snapshotName, String version) throws Exception;
}
