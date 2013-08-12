package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLikeData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPaging;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractBaseDomainJsonImport;

public class SharedLikeJsonImport extends AbstractBaseDomainJsonImport implements ISharedLike {

	public SharedLikeJsonImport(JSONObject job) {
		super(job);
	}

	public Integer getCount() {
		return getInteger("count");
	}

	public List<ISharedLikeData> getDataList() {
		JSONArray jar = this.getJar("data");
		
		List<ISharedLikeData> list = new ArrayList<ISharedLikeData>();
		
		if (jar != null)
			for (Object o : jar)
				list.add(new SharedLikeDataJsonImport((JSONObject) o));
		
		return list;
	}

	public ISharedPaging getPaging() {
		JSONObject temp = getJob("paging");
		return temp == null ? null : new SharedPagingJsonImport(temp);
	}

}
