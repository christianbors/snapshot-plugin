/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.expandedArchiveReader;

import org.ssanalytics.snapshotplugin.io.expandedArchiveReader.ExpandedArchiveReader;

/**
 *
 * @author Robert
 */
public class testExistingDirectory {
    
    public static void main(String args[]) throws Exception{
    
        ExpandedArchiveReader ear = ExpandedArchiveReader.getInstance();
        
        //should throw exception
        ear.readExpandedArchiveFromDirectory("C:\\temp");
        
    }      
    
}
