package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.photo;

import java.util.ArrayList;
import java.util.List;


import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoTag;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoTagData;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractBaseDomainJsonMongo;

public class PhotoTagJsonMongo extends AbstractBaseDomainJsonMongo implements IPhotoTag {

	public PhotoTagJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

	public List<IPhotoTagData> getTagDataList() {
		
		BasicDBList resultSet = this.getList("data");
		
		List<IPhotoTagData> list = new ArrayList<IPhotoTagData>();
		
		if(resultSet != null){
			for (Object o : resultSet){
				list.add(new PhotoTagDataJsonMongo((DBObject) o));
			}
		}
		
		return list;
	}

}
