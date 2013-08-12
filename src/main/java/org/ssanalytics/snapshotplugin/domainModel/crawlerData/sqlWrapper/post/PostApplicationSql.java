/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.post;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.post.IPostApplication;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractNamedDomainSql;

/**
 *
 * @author chw
 */
public class PostApplicationSql extends AbstractNamedDomainSql implements IPostApplication {
    
    public PostApplicationSql(String id, String name){
        super(id, name);
    }
    
}
