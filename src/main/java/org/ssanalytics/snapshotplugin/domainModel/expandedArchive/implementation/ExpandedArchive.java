/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.expandedArchive.implementation;

import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IAddressBookEntry;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IExpandedArchive;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IPreviousName;

/**
 *
 * @author chw
 */
public class ExpandedArchive implements IExpandedArchive{

    private List<IPreviousName> previousNames;
    private List<IAddressBookEntry> addressBook;
    
    
    public ExpandedArchive(){
        this.previousNames = new LinkedList<>();
    }
    
    @Override
    public List<IPreviousName> getPreviousNames() {
        return this.previousNames;
    }
    
    public void setPreviousNames(List<IPreviousName> prevNames){
        this.previousNames = prevNames;
    }

    @Override
    public List<IAddressBookEntry> getAddressBook() {
        return this.addressBook;
    }
    
    public void setAddressBook(List<IAddressBookEntry> addressBook){
        this.addressBook = addressBook;
    } 
    
}
