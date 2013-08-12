package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.photo;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoTagData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractTimedDomainJsonImport;

public class PhotoTagDataJsonImport extends AbstractTimedDomainJsonImport implements IPhotoTagData {

	public PhotoTagDataJsonImport(JSONObject job) {
		super(job);
	}

	public Double getX() {		
		
		try{
			return new Double(this.job.get("X").toString());
		} catch(Exception ex){
			return null;
		}
	}

	public Double getY() {
		
		try{
			return new Double(this.job.get("Y").toString());
		} catch(Exception ex){
			return null;
		}
	}

}
