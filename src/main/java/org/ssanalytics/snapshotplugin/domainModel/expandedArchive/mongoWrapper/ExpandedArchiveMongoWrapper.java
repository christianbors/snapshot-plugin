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
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IExpandedArchive;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IPreviousName;

/**
 *
 * @author chw
 */
public class ExpandedArchiveMongoWrapper extends AbstractBaseDomainJsonMongo implements IExpandedArchive{

    public ExpandedArchiveMongoWrapper(DBObject dbo){
        super(dbo);
    }
    
    @Override
    public List<IPreviousName> getPreviousNames() {
        BasicDBList tempList = this.getDataList("previous_names");
        
        List<IPreviousName> retList = new LinkedList<>();
        
        if(tempList != null){
            for(Object o : tempList){ 
                retList.add(new PreviousNameMongoWrapper((DBObject) o));
            }
        }
        
        return retList;        
    }    

    @Override
    public List<IAddressBookEntry> getAddressBook() {
        BasicDBList tempList = this.getDataList("addressbook");
        
        List<IAddressBookEntry> retList = new LinkedList<>();
        
        if(tempList != null){
            for(Object o : tempList){ 
                retList.add(new AddressBookEntryMongoWrapper((DBObject) o));
            }
        }
        
        return retList;   
    }
}
