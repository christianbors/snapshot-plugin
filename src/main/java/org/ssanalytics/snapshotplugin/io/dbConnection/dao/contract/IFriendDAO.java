package org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract;

import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.filter.FriendFilterData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;

public interface IFriendDAO {

    public List<IFriend> getFriendListSpecificVersion(String snapshotName, String version) throws Exception;

    public List<IFriend> getFriendListLatestVersion(String snapshotName) throws Exception;

    public List<IFriend> getFriendList(String snapshotName, String version, FriendFilterData filter) throws Exception;

    public List<String> getFriendIdListSpecificVersion(String snapshotName, String version) throws Exception;

    public List<String> getFriendIdListLatestVersion(String snapshotName) throws Exception;
}
