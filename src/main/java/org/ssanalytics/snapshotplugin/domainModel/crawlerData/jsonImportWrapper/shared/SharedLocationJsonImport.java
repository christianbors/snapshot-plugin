/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared;

import org.json.simple.JSONObject;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLocation;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractBaseDomainJsonImport;

/**
 *
 * @author chw
 */
public class SharedLocationJsonImport extends AbstractBaseDomainJsonImport implements ISharedLocation {

    public SharedLocationJsonImport(JSONObject job){
        super(job);
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
