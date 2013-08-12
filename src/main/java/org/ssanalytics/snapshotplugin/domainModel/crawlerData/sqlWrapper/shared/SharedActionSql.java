/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractBaseDomainSql;

/**
 *
 * @author chw
 */
public class SharedActionSql extends AbstractBaseDomainSql implements ISharedAction{

    private String name;
    private String link;
    
    public SharedActionSql(String name, String link){
        super();
        this.name = name;
        this.link = link;
    }
    
    
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getLink() {
        return this.link;
    }
    
}
