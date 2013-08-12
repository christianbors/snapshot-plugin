/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared;

import org.json.simple.JSONObject;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLocation;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractNamedDomainJsonImport;

/**
 *
 * @author chw
 */
public class SharedPlaceJsonImport extends AbstractNamedDomainJsonImport implements ISharedPlace{

    public SharedPlaceJsonImport(JSONObject job){
        super (job);
    }
    
    @Override
    public ISharedLocation getLocation() {
        return new SharedLocationJsonImport(this.getJob("location"));
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
