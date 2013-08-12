/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedToData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractNamedDomainSql;

/**
 *
 * @author chw
 */
public class SharedToDataSql extends AbstractNamedDomainSql implements ISharedToData{
    
    public SharedToDataSql(String name, String id){
        super(id, name);
    }
    
}
