/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract;

import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.outbox.IOutbox;

/**
 *
 * @author chw
 */
public interface IOutboxDAO {

    public List<IOutbox> getOutboxListForRootAccountOfSnapshotSpecificVersion(String snapshotInfo, String version) throws Exception;

    public List<IOutbox> getOutboxListForRootAccountOfSnapshotLatestVersion(String snapshotInfo) throws Exception;

    public Map<String, Integer> getWordCountForOutboxOfRootAccountSnapshotLatestVersion(String snapshotInfo) throws Exception;

    public Map<String, Integer> getWordCountForOutboxOfRootAccountSnapshotSpecificVersion(String snapshotInfo, String version) throws Exception;
}
