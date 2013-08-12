/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared;

import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLikeData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPaging;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractBaseDomainSql;

/**
 *
 * @author chw
 */
public class SharedLikeSql extends AbstractBaseDomainSql implements ISharedLike{

    
    private Integer count;
    private List<ISharedLikeData> dataList;
    private ISharedPaging paging;
    
    public SharedLikeSql(Integer count, List<ISharedLikeData> dataList, ISharedPaging paging){
        super();
        
        this.count = count;
        this.dataList = dataList;
        this.paging = paging;
    }
    
    @Override
    public Integer getCount() {
        return this.count;
    }

    @Override
    public List<ISharedLikeData> getDataList() {
        return this.dataList;
    }

    @Override
    public ISharedPaging getPaging() {
        return this.paging;
    }
    
}
