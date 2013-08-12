/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract;

import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.book.IBook;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ICategorizedDomain;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;

/**
 *
 * @author Robert
 */
public interface IBookDAO {

    public List<String> getDistinctBookCategories() throws Exception;

    public Map<String, Integer> getExtendedDistinctBookCategories() throws Exception;

    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotLatestVersion(String snapshotName) throws Exception;

    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotSpecificVersion(String snapshotName, String version) throws Exception;
    
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotLatestVersion(String snapshotName, long timeStart, long timeEnd) throws Exception;

    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotSpecificVersion(String snapshotName, String version, long timeStart, long timeEnd) throws Exception;

    public List<IBook> getBookListForProfileInSnapshotLatestVersion(String profileId, String snapshotId) throws Exception;

    public List<IBook> getBookListForProfileInSnapshotSpecificVersion(String profileId, String snapshotId, String version) throws Exception;

    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameBooksAsProfileForSnapshotSpecificVersion(String snapshotName, String version, String profile_id) throws Exception;

    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameBooksAsProfileForSnapshotLatestVersion(String snapshotName, String profile_id) throws Exception;
}
