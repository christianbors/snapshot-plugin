/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract;

import java.util.List;

/**
 *
 * @author chw
 */
public interface IAddressBookEntry {
    
    public String getName();
    public List<String> getContactInfoList();
}
