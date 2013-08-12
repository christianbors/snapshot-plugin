/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract;

import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhoto;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;

/**
 *
 * @author chw
 */
public interface IPhotoDAO {

    public List<IPhoto> getPhotoListForProfileInSnapshotSpecificVersion(String snapshotName, String version, String profileId) throws Exception;

    public List<IPhoto> getPhotoListForProfileInSnapshotLatestVersion(String snapsthoName, String profileId) throws Exception;

    public List<IPhoto> getPhotoListSnapshotSpecificVersion(String snapshotName, String version) throws Exception;

    public List<IPhoto> getPhotoListSnapshotLatestVersion(String snapsthoName) throws Exception;

    public List<ISharedPlace> getLocationsForProfileSnapshotLatestVersion(String profile_id, String snapshot) throws Exception;

    public List<ISharedPlace> getLocationsForProfileSnapshotSpecificVersion(String profile_id, String snapshot, String version) throws Exception;
    
    public List<ISharedComments> getAllCommentsInSnapshotLatestVersion(String snapshot) throws Exception;
    public List<ISharedComments> getAllCommentsInSnapshotSpecificVersion(String snapshot, String version) throws Exception;
}
