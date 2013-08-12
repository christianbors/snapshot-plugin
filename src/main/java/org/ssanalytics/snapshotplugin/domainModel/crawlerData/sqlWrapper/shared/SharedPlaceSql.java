/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLocation;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractNamedDomainSql;

/**
 *
 * @author chw
 */
public class SharedPlaceSql extends AbstractNamedDomainSql implements ISharedPlace{

    ISharedLocation location;
    
    public SharedPlaceSql(String id, String name, ISharedLocation location){
        super(id, name);
        this.location = location;
    }
    
    @Override
    public ISharedLocation getLocation() {
        return this.location;
    }
    
    @Override
    public boolean equals(Object o){
        if(! (o instanceof ISharedPlace)){
            return false;
        }
        
        ISharedPlace comp = (ISharedPlace) o;
        if((comp.getId() != null) && (comp.getId().equals(this.getId())))
            return true;
        else
            return false;
    }

    
    @Override
    public int hashCode() {
        try{
            return Integer.parseInt(this.getId());
        }
        catch(Exception ex){
            return 0;
        }
    }
    
}
