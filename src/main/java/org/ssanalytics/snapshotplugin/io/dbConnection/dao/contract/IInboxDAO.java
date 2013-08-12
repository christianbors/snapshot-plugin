/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract;

import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.inbox.IInbox;

/**
 *
 * @author chw
 */
public interface IInboxDAO {

    public List<IInbox> getInboxListForRootAccountOfSnapshotSpecificVersion(String snapshotInfo, String version) throws Exception;

    public List<IInbox> getInboxListForRootAccountOfSnapshotLatestVersion(String snapshotInfo) throws Exception;
}
