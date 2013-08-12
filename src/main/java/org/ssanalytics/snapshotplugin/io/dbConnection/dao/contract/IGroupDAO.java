/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract;

import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.group.IGroup;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;

/**
 *
 * @author chw
 */
public interface IGroupDAO {

    public Map<IGroup, List<NamedItem>> getWhoIsInTheSameGroup(String snapshotName, String profileId) throws Exception;
}
