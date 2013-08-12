/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract;

import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideo;

/**
 *
 * @author chw
 */
public interface IVideoDAO {
    
    public List<IVideo> getAllVideosInSnapshotLatestVersion(String snapshot) throws Exception;
    public List<IVideo> getAllVideosInSnapshotSpecificVersion(String snapshot, String version) throws Exception;
    
    public List<ISharedComments> getAllVideoCommentsInSnapshotLatestVersion(String snapshot) throws Exception;
    public List<ISharedComments> getAllVideoCommentsInSnapshotSpecificVersion(String snapshot, String version) throws Exception;
    
}
