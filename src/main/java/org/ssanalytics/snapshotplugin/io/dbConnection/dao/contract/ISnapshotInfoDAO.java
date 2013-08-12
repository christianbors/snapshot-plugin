package org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract;

import java.util.Date;
import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;

public interface ISnapshotInfoDAO {

    public List<ISnapshotInfo> getSnapshotInfoList() throws Exception;

    public String findHighestVersion(String snapshotinfo) throws Exception;
    
    public String getRootAccountFbId(String snapshotinfo, String version) throws Exception;
    
    public Date getFirstDateForEvidenceDomainsInSnapshotLatestVersion(String snapshotinfo) throws Exception;
    public Date getFirstDateForEvidenceDomainsInSnapshotSpecificVersion(String snapshotinfo, String version) throws Exception;
    
    public Date getLastDateForEvidenceDomainsInSnapshotLatestVersion(String snapshotinfo) throws Exception;
    public Date getLastDateForEvidenceDomainsInSnapshotSpecificVersion(String snapshotinfo, String version) throws Exception;
}
