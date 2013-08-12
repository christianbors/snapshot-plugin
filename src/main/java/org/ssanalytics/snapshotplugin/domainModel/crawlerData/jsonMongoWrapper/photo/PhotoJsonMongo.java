package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.photo;

import java.util.ArrayList;
import java.util.List;



import com.mongodb.BasicDBList;
import com.mongodb.DBObject;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhoto;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoImage;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoTag;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedCommentsJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedFromJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedLikeJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedPlaceJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractTimedDomainJsonMongo;

public class PhotoJsonMongo extends AbstractTimedDomainJsonMongo implements IPhoto{

	public PhotoJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

	public String getLink() {
		return this.getDataString("link");
	}

	public Long getUpdated_time() {
		
		return this.getDataTime("updated_time");
	}

	public Integer getHeight() {
		return this.getDataInteger("height");
	}

	public Integer getPosition() {
		return this.getDataInteger("position");
	}

	public String getIcon() {
		
		return this.getDataString("icon");
	}

	public String getPicture() {

		return this.getDataString("picture");
	}

	public String getSource() {
		
		return this.getDataString("source");
	}

	public List<IPhotoImage> getImageList() {
		
		BasicDBList resultSet = this.getDataList("images");
		
		List<IPhotoImage> list = new ArrayList<IPhotoImage>();
		
		if(resultSet != null){
			for (Object o : resultSet){
				list.add(new PhotoImageJsonMongo((DBObject) o));
			}
		}
		
		return list;
	}

	public ISharedFrom getFrom() {
		
		com.mongodb.DBObject temp = this.getDataDbo("from");
		if(temp == null)
			return null;
		
		return new SharedFromJsonMongo(temp);
	}

	public ISharedLike getLikes() {
	
		com.mongodb.DBObject temp = this.getDataDbo("likes");
		if(temp == null)
			return null;
		
		return new SharedLikeJsonMongo(temp);
	
	}

	public Integer getWidth() {
		return this.getDataInteger("width");
	}

	@Override
	public IPhotoTag getPhotoTags() {
		com.mongodb.DBObject temp = this.getDataDbo("tags");
		if(temp == null)
			return null;
		
		return new PhotoTagJsonMongo(temp);
	}

    @Override
    public ISharedPlace getPlace() {
        DBObject temp = this.getDataDbo("place");
        return temp == null ? null : new SharedPlaceJsonMongo(temp);
    }

    @Override
    public ISharedComments getComments() {
        
        DBObject temp = this.getDataDbo("comments");
        return temp == null ? null : new SharedCommentsJsonMongo(temp);
    }

}
