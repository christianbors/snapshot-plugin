/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.daoReturnValues;

/**
 *
 * @author chw
 */
public class NamedItem extends IdentityItem {
    
    
    String name;
    
    public NamedItem(String id, String name){
        super(id);
        
        this.name = name;        
    }
    
    public String getName(){
        return this.name;
    }
}
