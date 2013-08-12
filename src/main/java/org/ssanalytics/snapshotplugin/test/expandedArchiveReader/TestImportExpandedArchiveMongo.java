/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.expandedArchiveReader;

import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.io.expandedArchiveReader.ExpandedArchiveDbImporter;

/**
 *
 * @author chw
 */
public class TestImportExpandedArchiveMongo {
    
    public static void main(String args[]) throws Exception{
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";
        
        ExpandedArchiveDbImporter.getInstance().readExpandedArchiveFromDirectoryAndImport("../testdata/extended_archive_robo/dyi_100000324542854/", "dd", "1");
    }
    
}
