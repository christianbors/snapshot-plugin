/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.expandedArchiveReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IExpandedArchive;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;

/**
 *
 * @author chw
 */
public class ExpandedArchiveDbImporter {
    
    private ExpandedArchiveDbImporter() {
        super();
    }
    private static ExpandedArchiveDbImporter instance = null;

    public static ExpandedArchiveDbImporter getInstance() {
        if (instance == null) {
            instance = new ExpandedArchiveDbImporter();
        }
        return instance;
    }
    
     public void readExpandedArchiveFromDirectoryAndImport(String directory, String snapshot, String version) throws Exception{
         
         IExpandedArchive ear = ExpandedArchiveReader.getInstance().readExpandedArchiveFromDirectory(directory);
         DaoFactory.getExpandedArchiveDAO().saveExpandedArchive(ear, snapshot, version);
     
     }
    
}
