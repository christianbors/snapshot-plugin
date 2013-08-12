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
public interface ISharedPrivacy extends IBaseDomain{
    
    
    public String getDescription();
    public String getValue();
}
