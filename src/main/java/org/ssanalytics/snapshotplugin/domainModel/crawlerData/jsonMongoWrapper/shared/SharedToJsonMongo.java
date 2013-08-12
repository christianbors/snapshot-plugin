package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared;

import java.util.ArrayList;
import java.util.List;


import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedToData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractBaseDomainJsonMongo;

public class SharedToJsonMongo extends AbstractBaseDomainJsonMongo implements ISharedTo {

	public SharedToJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

	public List<ISharedToData> getToDataList() {
		
		BasicDBList resultSet = getList("data");
		List<ISharedToData> list = new ArrayList<ISharedToData>();
		
		if(resultSet != null){
			for(Object o : resultSet)
			{				
				list.add(new SharedToDataJsonMongo((DBObject) o));
			}
		}
		
		return list;
	}

}
