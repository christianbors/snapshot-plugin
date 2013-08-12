/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract;

import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IExpandedArchive;

/**
 *
 * @author chw
 */
public interface IExpandedArchiveDAO {
    
    public IExpandedArchive getExpandedArchiveForSnapshotLatestVersion(String snapshot) throws Exception;
    public IExpandedArchive getExpandedArchiveForSnapshotSpecificVersion(String snapshot, String version) throws Exception;
    
    public void saveExpandedArchive(IExpandedArchive toSave, String snapshot, String version) throws Exception;
    public void saveExpandedArchive(IExpandedArchive toSave, String snapshot) throws Exception;
    
}
