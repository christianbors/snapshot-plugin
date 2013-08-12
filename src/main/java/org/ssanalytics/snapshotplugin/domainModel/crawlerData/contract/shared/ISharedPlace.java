/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.INamedDomain;

/**
 *
 * @author chw
 */
public interface ISharedPlace extends INamedDomain{
    
    public ISharedLocation getLocation();
    
}
