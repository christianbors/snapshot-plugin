/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.postgreSql.book;
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
public class TestWhoLikesTheSameBooksSql {
    
    public static void main(String args[]) throws Exception {

        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";


        Map<ICategorizedDomain, List<NamedItem>> map = DaoFactory.getBookDAO().getWhoLikesTheSameBooksAsProfileForSnapshotLatestVersion("dd", "100003541991661");

        System.out.println(map.size());
        for (ICategorizedDomain icd : map.keySet()) {

            List<NamedItem> l = map.get(icd);
            System.out.println("Book name: " + icd.getName() + "   count: " + l.size());
        }
    }  
    
}
