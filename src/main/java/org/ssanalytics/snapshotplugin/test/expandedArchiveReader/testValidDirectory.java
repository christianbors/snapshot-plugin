/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.expandedArchiveReader;

import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IAddressBookEntry;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IExpandedArchive;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IPreviousName;
import org.ssanalytics.snapshotplugin.io.expandedArchiveReader.ExpandedArchiveReader;

/**
 *
 * @author Robert
 */
public class testValidDirectory {
    
    public static void main(String args[]) throws Exception{
    
        ExpandedArchiveReader ear = ExpandedArchiveReader.getInstance();
        
        //should not throw exception
        IExpandedArchive expa = ear.readExpandedArchiveFromDirectory("../testdata/extended_archive_robo/dyi_100000324542854/");
        
        for(IPreviousName prevName : expa.getPreviousNames()){
            System.out.println(prevName.getPreviousName());
        }
        
        for(IAddressBookEntry entry : expa.getAddressBook()){
            System.out.println(entry.getName());
            for(String s : entry.getContactInfoList()){
                System.out.println("  => " + s);
            }
        }
    }      
    
}
