package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared;

import java.util.ArrayList;
import java.util.List;


import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedCommentData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPaging;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractBaseDomainJsonMongo;

public class SharedCommentsJsonMongo extends AbstractBaseDomainJsonMongo implements ISharedComments {

	public SharedCommentsJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

	public ISharedPaging getPaging() {
		
		com.mongodb.DBObject temp = this.getDataDbo("paging");
		if (temp == null)
			return null;
		
		
		return new SharedPagingJsonMongo(temp);
	}

	public Integer getCount() {
		return getDataInteger("count");
	}

	public List<ISharedCommentData> getDataList() {
		
		BasicDBList resultSet = this.getList("data");
		
		List<ISharedCommentData> list = new ArrayList<ISharedCommentData>();
		
		if(resultSet != null){
			for (Object o : resultSet){
				list.add(new SharedCommentDataJsonMongo((DBObject) o));
			}
		}
		
		return list;
	}

}
