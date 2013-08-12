/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.expandedArchive.implementation;

import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IAddressBookEntry;

/**
 *
 * @author chw
 */
public class AddressBookEntry implements IAddressBookEntry{

    private String name;
    private List<String> contactList;
    
    public AddressBookEntry(String name, List<String> contactInfoList){
        this.name = name;
        this.contactList = contactInfoList;
    }
    
    
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<String> getContactInfoList() {
        return this.contactList;
    }
    
}
