/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.expandedArchiveReader;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IAddressBookEntry;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IPreviousName;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.implementation.AddressBookEntry;

/**
 *
 * @author chw
 */
public class AddressBookReader extends AbstractFileParser{
    
    private AddressBookReader() {
        super();
    }
    private static AddressBookReader instance = null;

    public static AddressBookReader getInstance() {
        if (instance == null) {
            instance = new AddressBookReader();
        }
        return instance;
    }

    
    
    public List<IAddressBookEntry> extractAddressBookFromFile(File addressbookHtml) {

        String source = this.readFile(addressbookHtml);
        
        List<IAddressBookEntry> retList = new LinkedList<>();
        
        source = this.removeUntil(source, "</h1>");
        source = this.removeUntil(source, "<div id=\"content\"><ul class=\"uiList _4of _4kg \">");
        
        String search_begin_li = "<li><div class=\"fcb\">";
        String search_end_li = "</div></li>";
        String search_begin_ul = "<ul class=\"uiList _4of _4kg \">";
        String search_end_ul = "</ul>";
        
        int pos_begin_li = source.indexOf(search_begin_li);
        int pos_end_ul = source.indexOf(search_end_ul);
        
        while(((pos_begin_li > -1) && (pos_end_ul > -1)) && (pos_begin_li < pos_end_ul)){
            source = this.removeUntil(source, search_begin_li);
            String contactName = source.substring(0, source.indexOf(search_begin_ul)); 
            
            List<String> contactInfoList = new LinkedList<>();
            source = this.removeUntil(source, search_begin_ul);
            
            pos_begin_li = source.indexOf(search_begin_li);
            pos_end_ul = source.indexOf(search_end_ul);
            
            while(((pos_begin_li > -1) && (pos_end_ul > -1)) && (pos_begin_li < pos_end_ul)){
                source = this.removeUntil(source, search_begin_li);
                contactInfoList.add(source.substring(0, source.indexOf(search_end_li)).replace("&#064;", "@"));
               
                source = this.removeUntil(source, search_end_li);
                
                pos_begin_li = source.indexOf(search_begin_li);
                pos_end_ul = source.indexOf(search_end_ul);                
            }
            
            retList.add(new AddressBookEntry(contactName, contactInfoList));
            
            source = this.removeUntil(source, search_end_ul);
            
            pos_begin_li = source.indexOf(search_begin_li);
            pos_end_ul = source.indexOf(search_end_ul);  
            
        }
        
        
        return retList;
    }
}
