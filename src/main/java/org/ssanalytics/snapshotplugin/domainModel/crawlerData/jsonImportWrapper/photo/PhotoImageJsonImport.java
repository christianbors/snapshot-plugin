package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.photo;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoImage;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractBaseDomainJsonImport;

public class PhotoImageJsonImport extends AbstractBaseDomainJsonImport implements IPhotoImage {

	public PhotoImageJsonImport(JSONObject job) {
		super(job);
	}

	public Integer getHeight() {
		
		return getInteger("height");
	}

	public Integer getWidth() {

		return getInteger("width");
	}

	public String getSource() {

		return this.job.get("source").toString();
	}

}
