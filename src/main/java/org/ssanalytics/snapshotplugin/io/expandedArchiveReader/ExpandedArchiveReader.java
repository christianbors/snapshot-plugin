/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.expandedArchiveReader;

import com.mongodb.MongoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IExpandedArchive;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IPreviousName;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.implementation.ExpandedArchive;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.implementation.PreviousName;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.OutboxDAOMongo;

/**
 *
 * @author Robert
 */
public class ExpandedArchiveReader {
    
    private ExpandedArchiveReader() {
        super();
    }
    private static ExpandedArchiveReader instance = null;

    public static ExpandedArchiveReader getInstance() {
        if (instance == null) {
            instance = new ExpandedArchiveReader();
        }
        return instance;
    }
    

    
    public IExpandedArchive readExpandedArchiveFromDirectory(String directory) throws FileNotFoundException, IOException{
        
        ExpandedArchive retVal = new ExpandedArchive();
        
        //check path
        File basedir = new File(directory);
        if(!basedir.exists())
            throw new FileNotFoundException("The directory " + basedir.getAbsolutePath() + " does not exist");
        
        directory = directory.replace('\\', '/');
        
        if(!directory.endsWith("/"))
            directory += "/";
        
        File index = new File(directory + "index.html");
        
        if(!index.exists())
            throw new FileNotFoundException("The chosen directory " + basedir.getAbsolutePath() + " is not a valid \"Expanded Archive\"\nThe index.html was not found!");       
        
        
        String html_dir = directory + "html/";
        
        if(!(new File(html_dir).exists())){
            throw new FileNotFoundException("The chosen directory " + basedir.getAbsolutePath() + " is not a valid \"Expanded Archive\"\nThe html directory was not found!");
        }
        
        //read previous_names
        File namesHtml = new File(html_dir + "names.html");
        
        if(namesHtml.exists()){
                        
            retVal.setPreviousNames(PreviousNamesReader.getInstance().extractPrevNamesFromFile(namesHtml));
        }
        
        //read address book
        File addressbookHtml = new File(html_dir + "addressbook.html");
        
        if(addressbookHtml.exists()){
            retVal.setAddressBook(AddressBookReader.getInstance().extractAddressBookFromFile(addressbookHtml));            
        }
        
        return retVal;       
        
    }
    
    
    
}
