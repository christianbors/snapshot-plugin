/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared;

import com.mongodb.DBObject;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLocation;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractBaseDomainJsonMongo;

/**
 *
 * @author chw
 */
public class SharedLocationJsonMongo extends AbstractBaseDomainJsonMongo implements ISharedLocation{

    
    public SharedLocationJsonMongo(DBObject dbo){
        super(dbo);
    }
    
    @Override
    public Double getLongitude() {
        return this.getDouble("longitude");
    }

    @Override
    public Double getLatitude() {
        return this.getDouble("latitude");
    }

    @Override
    public String getCity() {
        return this.getString("city");
    }

    @Override
    public String getCountry() {
        return this.getString("country");
    }

    @Override
    public String getStreet() {
       return this.getString("street");
    }

    @Override
    public String getZip() {
        return this.getString("zip");
    }

    @Override
    public String getState() {
        return this.getString("state");
    }
    
}
