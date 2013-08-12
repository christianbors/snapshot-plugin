/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared;

import com.mongodb.DBObject;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLocation;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractBaseDomainJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractNamedDomainJsonMongo;

/**
 *
 * @author chw
 */
public class SharedPlaceJsonMongo extends AbstractBaseDomainJsonMongo implements ISharedPlace{

    
    public SharedPlaceJsonMongo(DBObject dbo){
        super(dbo);
    }
    
    @Override
    public ISharedLocation getLocation() {
        return new SharedLocationJsonMongo(this.getDbo("location"));
    }

    @Override
    public String getName() {
        return this.getString("name");
    }

    @Override
    public String getId() {
        return this.getString("id");
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
