package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedCommentData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPaging;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractBaseDomainJsonImport;

public class SharedCommentsJsonImport extends AbstractBaseDomainJsonImport implements ISharedComments {

    public SharedCommentsJsonImport(JSONObject job) {
        super(job);
    }

    public ISharedPaging getPaging() {

        JSONObject temp = (JSONObject) this.job.get("paging");
        if (temp == null) {
            return null;
        }


        return new SharedPagingJsonImport(temp);
    }

    public List<ISharedCommentData> getData() {
        JSONArray jar = (JSONArray) this.job.get("data");

        List<ISharedCommentData> list = new ArrayList<ISharedCommentData>();

        for (Object o : jar) {
            list.add(new SharedCommentDataJsonImport((JSONObject) o));
        }

        return list;
    }

    public Integer getCount() {
        return this.getInteger("count");
    }

    public List<ISharedCommentData> getDataList() {

        JSONArray jar = (JSONArray) this.job.get("data");

        List<ISharedCommentData> list = new ArrayList<ISharedCommentData>();

        if (jar != null) {
            for (Object o : jar) {
                list.add(new SharedCommentDataJsonImport((JSONObject) o));
            }
        }

        return list;

    }
}
