package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.photo;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhoto;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoImage;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoTag;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedCommentsJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedFromJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedLikeJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedPlaceJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractTimedDomainJsonImport;

public class PhotoJsonImport extends AbstractTimedDomainJsonImport implements IPhoto{

	public PhotoJsonImport(JSONObject job) {
		super(job);
	}

	public String getLink() {
		return this.job.get("link").toString();
	}

	public Long getUpdated_time() {
		
		return this.getTime("updated_time");
	}

	public Integer getHeight() {
		return this.getInteger("height");
	}

	public Integer getPosition() {
		return this.getInteger("position");
	}

	public String getIcon() {
		
		return this.job.get("icon").toString();
	}

	public String getPicture() {

		return this.job.get("picture").toString();
	}

	public String getSource() {
		
		return this.job.get("source").toString();
	}

	public List<IPhotoImage> getImageList() {
		
		JSONArray jar = this.getJar("languages");
		
		List<IPhotoImage> list = new ArrayList<IPhotoImage>();
		
		if(jar != null)
			for (Object o : jar)
				list.add(new PhotoImageJsonImport((JSONObject) o));
		
		
		return list;
	}
	
	public IPhotoTag getPhotoTags(){
		
		JSONObject temp = this.getJob("tags");
		if(temp == null)
			return null;
		
		return new PhotoTagJsonImport(temp);
	}

	public ISharedFrom getFrom() {
		
		JSONObject temp = this.getJob("from");
		if(temp == null)
			return null;
		
		return new SharedFromJsonImport(temp);
	}

	public ISharedLike getLikes() {
	
		JSONObject temp = this.getJob("likes");
		if(temp == null)
			return null;
		
		return new SharedLikeJsonImport(temp);
	
	}

	public Integer getWidth() {
		return this.getInteger("width");
	}

    @Override
    public ISharedPlace getPlace() {
        JSONObject temp = this.getJob("place");
        return temp == null ? null : new SharedPlaceJsonImport(temp);
    }

    @Override
    public ISharedComments getComments() {
        
        JSONObject temp = this.getJob("comments");
        return temp == null ? null : new SharedCommentsJsonImport(temp);

    }

}
