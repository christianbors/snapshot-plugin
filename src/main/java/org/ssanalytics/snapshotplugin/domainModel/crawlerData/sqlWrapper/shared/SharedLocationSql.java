/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLocation;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractBaseDomainSql;

/**
 *
 * @author chw
 */
public class SharedLocationSql extends AbstractBaseDomainSql implements ISharedLocation {

    private Double longitude;
    private Double latitude;
    private String city;
    private String zip;
    private String state;
    private String country;
    private String street;
    
    public SharedLocationSql(Double longitude, Double latitude, String city, String street, String zip, String country, String state){
        this.longitude = longitude;
        this.latitude = latitude;
        
        this.city = city;
        this.street = street;
        this.zip = zip;
        this.country = country;
        this.state = state;
    }
    
    @Override
    public Double getLongitude() {
        return this.longitude;
    }

    @Override
    public Double getLatitude() {
        return this.latitude;
    }

    @Override
    public String getCity() {
        return this.city;
    }

    @Override
    public String getCountry() {
        return this.country;
    }

    @Override
    public String getStreet() {
        return this.street;
    }

    @Override
    public String getZip() {
        return this.zip;
    }

    @Override
    public String getState() {
       return this.state;
    }
    
    
    
}
