package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.tagged;

import java.util.ArrayList;
import java.util.List;



import com.mongodb.BasicDBList;
import com.mongodb.DBObject;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged.ITagged;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged.ITaggedApplication;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged.ITaggedProperties;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedActionJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedCommentsJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedFromJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedToJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractTimedDomainJsonMongo;

public class TaggedJsonMongo extends AbstractTimedDomainJsonMongo implements ITagged{

	public TaggedJsonMongo(DBObject job) {
		super(job);
	}

	public List<ISharedAction> getActions() {
		BasicDBList resultSet = this.getDataList("actions");
		
		List<ISharedAction> list = new ArrayList<ISharedAction>();
		
		if(resultSet != null){
			for(Object o : resultSet){
				list.add(new SharedActionJsonMongo((DBObject) o));
			}
		}
		
		return list;
	}

	public ITaggedApplication getApplication() {
		DBObject temp = getDataDbo("application");
		return temp == null ? null : new TaggedApplicationJsonMongo(temp);
	}

	public String getCaption() {
		return getDataString("caption");
	}

	public ISharedComments getComments() {
		DBObject temp = getDataDbo("comments");
		return temp == null ? null : new SharedCommentsJsonMongo(temp);
	}

	public ISharedFrom getFrom() {
		DBObject temp = getDataDbo("from");
		return temp == null ? null : new SharedFromJsonMongo(temp);
	}

	public String getIcon() {
		return getDataString("icon");
	}

	public String getLink() {
		return getDataString("link");
	}

	public String getPicture() {
		return getDataString("picture");
	}

	public ISharedTo getTo() {
		DBObject temp = getDataDbo("to");
		return temp == null ? null : new SharedToJsonMongo(temp);
	}

	public String getType() {
		return getDataString("type");
	}

	public Long getUpdated_time() {
		// TODO parse time
		return null;
	}

	public String getDescription() {
		return getDataString("description");
	}

	public String getMessage() {
		return getDataString("message");
	}

    @Override
    public List<ITaggedProperties> getProperties() {
        
       		BasicDBList resultSet = this.getDataList("properties");
		
		List<ITaggedProperties> list = new ArrayList<ITaggedProperties>();
		
		if(resultSet != null)
			for(Object o : resultSet)
				list.add(new TaggedPropertiesJsonMongo((DBObject) o));
			
		
		
		return list;
    }

}
