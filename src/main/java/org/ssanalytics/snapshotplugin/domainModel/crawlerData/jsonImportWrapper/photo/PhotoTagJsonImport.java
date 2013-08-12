package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.photo;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoTag;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoTagData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractBaseDomainJsonImport;

public class PhotoTagJsonImport extends AbstractBaseDomainJsonImport implements IPhotoTag {

	public PhotoTagJsonImport(JSONObject job) {
		super(job);
	}

	public List<IPhotoTagData> getTagDataList() {
		
		JSONArray jar = (JSONArray) this.job.get("data");
		List<IPhotoTagData> list = new ArrayList<IPhotoTagData>();
		
		if(jar != null)
			for(Object o : jar)
				list.add(new PhotoTagDataJsonImport((JSONObject) o));
		
		
		return list;
	}

}
