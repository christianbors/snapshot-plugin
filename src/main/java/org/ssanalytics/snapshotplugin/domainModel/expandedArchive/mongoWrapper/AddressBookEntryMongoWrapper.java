/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.expandedArchive.mongoWrapper;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractBaseDomainJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IAddressBookEntry;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IPreviousName;

/**
 *
 * @author chw
 */
public class AddressBookEntryMongoWrapper extends AbstractBaseDomainJsonMongo implements IAddressBookEntry{

    
    public AddressBookEntryMongoWrapper(DBObject dbo){
        super(dbo);
    }

    @Override
    public String getName() {
        return this.getString("name");
    }

    @Override
    public List<String> getContactInfoList() {
        BasicDBList bdl = this.getList("contact_info");
        
        List<String> retList = new LinkedList<>();
        if(bdl != null){
            for(Object o : bdl){
                retList.add(o.toString());
            }
        }
        
        return retList;            
    }
    
    
}
