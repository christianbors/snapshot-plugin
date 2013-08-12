package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared;

import java.util.ArrayList;
import java.util.List;


import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLikeData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPaging;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractBaseDomainJsonMongo;

public class SharedLikeJsonMongo extends AbstractBaseDomainJsonMongo implements ISharedLike {

    public SharedLikeJsonMongo(com.mongodb.DBObject job) {
        super(job);
    }

    public Integer getCount() {
        return getInteger("count");
    }

    public List<ISharedLikeData> getDataList() {
        BasicDBList resultSet = this.getList("data");

        List<ISharedLikeData> list = new ArrayList<ISharedLikeData>();

        if (resultSet != null) {
            for (Object o : resultSet) {
                list.add(new SharedLikeDataJsonMongo((DBObject) o));
            }
        }

        return list;
    }

    public ISharedPaging getPaging() {
        com.mongodb.DBObject temp = getDataDbo("paging");
        return temp == null ? null : new SharedPagingJsonMongo(temp);
    }
}
