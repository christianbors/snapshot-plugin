/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract;

import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.likes.ILike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ICategorizedDomain;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;

/**
 *
 * @author chw
 */
public interface ILikeDAO {

    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotLatestVersion(String snapshotId) throws Exception;

    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotSpecificVersion(String snapshotId, String version) throws Exception;
    
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotLatestVersion(String snapshotName, long timeStart, long timeEnd) throws Exception;

    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotSpecificVersion(String snapshotName, String version, long timeStart, long timeEnd) throws Exception;

    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameAsProfileForSnapshotSpecificVersion(String snapshotName, String version, String profile_id) throws Exception;

    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameAsProfileForSnapshotLatestVersion(String snapshotName, String profile_id) throws Exception;

    public List<ILike> getLikeListForProfileInSnapshotLatestVersion(String profileId, String snapshotId) throws Exception;

    public List<ILike> getLikeListForProfileInSnapshotSpecificVersion(String profileId, String snapshotId, String version) throws Exception;

    public List<String> getDistinctLikeCategories() throws Exception;

    public Map<String, Integer> getExtendedDistinctLikeCategories() throws Exception;
}
