/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract;

import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.home.IHome;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;

/**
 *
 * @author chw
 */
public interface IHomeDAO {
    
    public List<IHome> getHomeListForRootAccountOfSnapshotLatestVersion(String snapshotName) throws Exception;
    
    public List<IHome> getHomeListForRootAccountOfSnapshotSpecificVersion(String snapshotName, String version) throws Exception;
    
    public List<ISharedComments> getAllCommentsInSnapshotLatestVersion(String snapshot) throws Exception;
    public List<ISharedComments> getAllCommentsInSnapshotSpecificVersion(String snapshot, String version) throws Exception;
    
}
