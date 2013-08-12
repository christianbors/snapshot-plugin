/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared;

import com.mongodb.DBObject;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPrivacy;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractBaseDomainJsonMongo;

/**
 *
 * @author chw
 */
public class SharedPrivacyJsonMongo extends AbstractBaseDomainJsonMongo implements ISharedPrivacy{

    
    public SharedPrivacyJsonMongo(DBObject dbo){
        super(dbo);
    }
    
    
    @Override
    public String getDescription() {
        return this.getString("description");
    }

    @Override
    public String getValue() {
        return this.getString("value");
    }
    
}
