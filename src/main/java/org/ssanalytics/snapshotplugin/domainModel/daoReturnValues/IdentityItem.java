/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.daoReturnValues;

/**
 *
 * @author chw
 */
public class IdentityItem {
    
    private String id;
    
    public IdentityItem(String id){
        this.id = id;
    }
    
    public String getId(){
        return this.id;
    }
    
    @Override
    public boolean equals(Object o){
        if(! (o instanceof IdentityItem)){
            return false;
        }
        
        if((this.id == null) || (o == null))        
            return false;        
        
        IdentityItem comp = (IdentityItem) o;
        
        if(this.id.equals(comp.getId()))
            return true;
        else
            return false;
    }
    
   @Override
   public int hashCode(){
       return this.id == null ? 0 : this.id.hashCode();
   }
}
