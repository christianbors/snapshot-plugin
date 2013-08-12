package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedToData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractBaseDomainJsonImport;

public class SharedToJsonImport extends AbstractBaseDomainJsonImport implements ISharedTo {

	public SharedToJsonImport(JSONObject job) {
		super(job);
	}

	public List<ISharedToData> getToDataList() {
		
		JSONArray jar = (JSONArray) this.job.get("data");
		List<ISharedToData> list = new ArrayList<ISharedToData>();
		
		for(Object o : jar)
		{				
			list.add(new SharedToDataJsonImport((JSONObject) o));
		}
		
		return list;
	}

}
