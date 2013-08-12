/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.mongo.like;

import org.ssanalytics.snapshotplugin.test.dao.mongo.activity.*;
import org.ssanalytics.snapshotplugin.test.dao.mongo.book.*;
import org.ssanalytics.snapshotplugin.test.dao.mongo.movie.*;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ICategorizedDomain;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;

/**
 *
 * @author chw
 */
public class TestGetWhoLikesTheSameMongo {
    
        
    public static void main(String args[]) throws Exception {

        ConfigurationFileManager.CONFIG_FILE_NAME = "localMongo.json";

        Map<ICategorizedDomain, List<NamedItem>> map = DaoFactory.getLikeDAO().getWhoLikesTheSameAsProfileForSnapshotLatestVersion("dd", "100000947086937");

        System.out.println(map.size());

        for (ICategorizedDomain icd : map.keySet()) {

            List<NamedItem> l = map.get(icd);
            System.out.println("Like name: " + icd.getName() + "   count: " + l.size());
        }
    }
    
}
