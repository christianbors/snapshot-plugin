/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract;

import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.feed.IFeed;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;

/**
 *
 * @author Robert K
 */
public interface IFeedDAO {

    public Map<NamedItem, Integer> getWhoPostedOnWallForRootAccountOfSnapshotLatestVersion(String snapshotName) throws Exception;

    public Map<NamedItem, Integer> getWhoPostedOnWallForRootAccountOfSnapshotSpecificVersion(String snapshotName, String version) throws Exception;
    
    public List<IFeed> getAllFeedsForRootAccountOfSnapshotLatestVersion(String snapshotName) throws Exception;
    
    public List<IFeed> getAllFeedsForRootAccountOfSnapshotSpecificVersion(String snapshotName, String version) throws Exception;
    
    public List<ISharedComments> getAllCommentsInSnapshotLatestVersion(String snapshot) throws Exception;
    public List<ISharedComments> getAllCommentsInSnapshotSpecificVersion(String snapshot, String version) throws Exception;
}
