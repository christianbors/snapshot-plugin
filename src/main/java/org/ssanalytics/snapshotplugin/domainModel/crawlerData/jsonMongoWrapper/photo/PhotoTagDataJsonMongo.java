package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.photo;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoTagData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractBaseDomainJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractTimedDomainJsonMongo;

public class PhotoTagDataJsonMongo extends AbstractBaseDomainJsonMongo implements IPhotoTagData {

	public PhotoTagDataJsonMongo(com.mongodb.DBObject job) {
		super(job);
	}

	public Double getX() {		
		
		return this.getDataDouble("x");
	}

	public Double getY() {
		
		return this.getDataDouble("y");
	}

    @Override
    public Long getCreated_time() {
        return this.getTime("created_time");
    }

    @Override
    public String getName() {
        return this.getString("name");
    }

    @Override
    public String getId() {
        return this.getString("id");
    }

}
