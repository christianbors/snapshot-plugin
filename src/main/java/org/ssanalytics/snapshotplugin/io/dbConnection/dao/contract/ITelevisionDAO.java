/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract;

import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ICategorizedDomain;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.television.ITelevision;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;

/**
 *
 * @author chw
 */
public interface ITelevisionDAO {

    public Map<CategoryItem, Integer> getTelevisionEvidenceCountForSnapshotLatestVersion(String snapshotName) throws Exception;

    public Map<CategoryItem, Integer> getTelevisionEvidenceCountForSnapshotSpecificVersion(String snapshotName, String version) throws Exception;
    
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotLatestVersion(String snapshotName, long timeStart, long timeEnd) throws Exception;

    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotSpecificVersion(String snapshotName, String version, long timeStart, long timeEnd) throws Exception;

    public List<ITelevision> getTelevisionListForProfileInSnapshotLatestVersion(String snapshotName, String accountId) throws Exception;

    public List<ITelevision> getTelevisionListForProfileInSnapshotSpecificVersion(String snapshotName, String version, String accountId) throws Exception;

    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameTelevisionAsProfileForSnapshotSpecificVersion(String snapshotName, String version, String profile_id) throws Exception;

    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameTelevisionAsProfileForSnapshotLatestVersion(String snapshotName, String profile_id) throws Exception;

    public List<String> getDistinctTelevisionCategories() throws Exception;

    public Map<String, Integer> getExtendedDistinctTelevisionCategories() throws Exception;
}
