/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IBaseDomain;

/**
 *
 * @author chw
 */
public interface ISharedLocation extends IBaseDomain{
    
    public Double getLongitude();
    public Double getLatitude();
    
    public String getCity();
    public String getCountry();
    public String getStreet();
    public String getZip();
    public String getState();
    
}
