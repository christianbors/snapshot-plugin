/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared;

import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedToData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractBaseDomainSql;

/**
 *
 * @author chw
 */
public class SharedToSql extends AbstractBaseDomainSql implements ISharedTo{

    List<ISharedToData> dataList;
    
    public SharedToSql(List<ISharedToData> dataList){
        this.dataList = dataList;
    }
    
    @Override
    public List<ISharedToData> getToDataList() {
        return this.dataList;
    }
    
}
