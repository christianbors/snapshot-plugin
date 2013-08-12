package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.video;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideoTagData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractTimedDomainJsonImport;

public class VideoTagDataJsonImport extends AbstractTimedDomainJsonImport implements IVideoTagData{

	public VideoTagDataJsonImport(JSONObject job) {
		super(job);
	}

}
