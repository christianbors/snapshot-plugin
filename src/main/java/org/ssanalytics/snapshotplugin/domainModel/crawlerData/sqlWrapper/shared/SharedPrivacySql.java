/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPrivacy;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractBaseDomainSql;

/**
 *
 * @author chw
 */
public class SharedPrivacySql extends AbstractBaseDomainSql implements ISharedPrivacy{

    private String description;
    private String value;
    
    public SharedPrivacySql(String description, String value){
        
        super();
        this.description = description;
        this.value = value;
    }
    
    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getValue() {
        return this.value;
    }
    
}
