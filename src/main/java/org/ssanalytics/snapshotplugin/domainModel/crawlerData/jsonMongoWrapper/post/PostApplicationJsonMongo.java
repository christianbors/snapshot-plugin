/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.post;

import com.mongodb.DBObject;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.post.IPostApplication;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractNamedDomainJsonMongo;

/**
 *
 * @author chw
 */
public class PostApplicationJsonMongo extends AbstractNamedDomainJsonMongo implements IPostApplication{
    
    public PostApplicationJsonMongo(DBObject dbo){
        super(dbo);
    }
            
}
