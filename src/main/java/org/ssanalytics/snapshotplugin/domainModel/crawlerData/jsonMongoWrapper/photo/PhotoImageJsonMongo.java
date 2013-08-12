package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.photo;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoImage;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractBaseDomainJsonMongo;

public class PhotoImageJsonMongo extends AbstractBaseDomainJsonMongo implements IPhotoImage {

	public PhotoImageJsonMongo(com.mongodb.DBObject job){
		super(job);
	}

	public Integer getHeight() {
		
		return getDataInteger("height");
	}

	public Integer getWidth() {

		return getDataInteger("width");
	}

	public String getSource() {

		return getDataString("source");
	}
}
