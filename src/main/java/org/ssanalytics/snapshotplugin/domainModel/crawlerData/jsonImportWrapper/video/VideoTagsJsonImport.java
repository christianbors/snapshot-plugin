package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.video;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideoTagData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideoTags;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractBaseDomainJsonImport;

public class VideoTagsJsonImport extends AbstractBaseDomainJsonImport implements IVideoTags{

	public VideoTagsJsonImport(JSONObject job) {
		
		super(job);
	}

	public List<IVideoTagData> getDataList() {
		JSONArray jar = (JSONArray) this.job.get("data");
		
		List<IVideoTagData> list = new ArrayList<IVideoTagData>();
		for (Object o : jar){
			list.add(new VideoTagDataJsonImport((JSONObject) o));
		}
		
		return list;
	}

}
