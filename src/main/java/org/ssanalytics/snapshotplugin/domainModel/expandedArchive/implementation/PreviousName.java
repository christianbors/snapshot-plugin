/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.expandedArchive.implementation;

import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IPreviousName;

/**
 *
 * @author chw
 */
public class PreviousName implements IPreviousName {

    
    private String previousName;
    
    public PreviousName(String previousName){
        this.previousName = previousName;
    }
    
    @Override
    public String getPreviousName() {
        return this.previousName;
    }
    
}
