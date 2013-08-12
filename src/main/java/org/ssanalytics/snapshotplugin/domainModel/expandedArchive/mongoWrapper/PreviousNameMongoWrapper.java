/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.expandedArchive.mongoWrapper;

import com.mongodb.DBObject;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractBaseDomainJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IPreviousName;

/**
 *
 * @author chw
 */
public class PreviousNameMongoWrapper extends AbstractBaseDomainJsonMongo implements IPreviousName{
    
    public PreviousNameMongoWrapper(DBObject dbo){
        super(dbo);
    }
    
    @Override
    public String getPreviousName() {
        return this.getString("previous_name");
    }
    
}
