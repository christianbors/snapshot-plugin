package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.music;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.music.IMusic;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractCategorizedDomainJsonImport;

public class MusicJsonImport extends AbstractCategorizedDomainJsonImport implements IMusic{

	public MusicJsonImport(JSONObject job) {
		super(job);              
	}
}
