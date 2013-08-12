package org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract;

import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;

public interface IProfileDAO {

    public List<IProfile> getProfileListForSnapshotLatestVersion(String snapshotInfoName) throws Exception;

    public List<IProfile> getProfileListForSnapshotSpecificVersion(String snapshotInfoName, String version) throws Exception;

    public IProfile getProfileForAccountIdInSnapshotLatestVersion(String accountId, String snapshotInfoName) throws Exception;

    public IProfile getProfileForAccountIdInSnapshotSpecificVersion(String accountId, String snapshotInfoName, String version) throws Exception;

    public String getNameForAccountId(String accountId) throws Exception;
}
