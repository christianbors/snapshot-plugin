/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.expandedArchiveReader;

import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IExpandedArchive;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.io.expandedArchiveReader.ExpandedArchiveDbImporter;

/**
 *
 * @author chw
 */
public class TestGetExpandedArchiveFromMongoDb {
    
    public static void main(String args[]) throws Exception{
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        
        IExpandedArchive exparch = DaoFactory.getExpandedArchiveDAO().getExpandedArchiveForSnapshotLatestVersion("dd");
        
        System.out.println(exparch.getPreviousNames());
    }
    
}
