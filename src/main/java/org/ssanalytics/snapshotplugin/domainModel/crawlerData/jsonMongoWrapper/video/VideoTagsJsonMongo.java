package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.video;

import java.util.ArrayList;
import java.util.List;



import com.mongodb.BasicDBList;
import com.mongodb.DBObject;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideoTagData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideoTags;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractBaseDomainJsonMongo;

public class VideoTagsJsonMongo extends AbstractBaseDomainJsonMongo implements IVideoTags{

	public VideoTagsJsonMongo(DBObject job) {
		
		super(job);
	}

	public List<IVideoTagData> getDataList() {
		BasicDBList resultSet = this.getDataList("data");
		
		List<IVideoTagData> list = new ArrayList<IVideoTagData>();
		if(resultSet != null){
			for (Object o : resultSet){
				list.add(new VideoTagDataJsonMongo((DBObject) o));
			}
		}
		
		return list;
	}

}
