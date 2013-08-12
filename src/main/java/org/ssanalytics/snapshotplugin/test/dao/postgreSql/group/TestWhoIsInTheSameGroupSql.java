/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.postgreSql.group;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.group.IGroup;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;

/**
 *
 * @author chw
 */
public class TestWhoIsInTheSameGroupSql {
    
        public static void main(String args[]) throws Exception{
            
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        
        Map<IGroup, List<NamedItem>> map = DaoFactory.getGroupDAO().getWhoIsInTheSameGroup("snapshot1327340118","523156250");
        
        System.out.println(map.size());
        
        for(IGroup g : map.keySet()){
        
            List<NamedItem> nil = map.get(g);
            System.out.println("Group name: " + g.getName() + "  friends count: " + nil.size());
            for(NamedItem ni : map.get(g)){
                System.out.println("      " + ni.getId() + "  " + ni.getName());
            }
        }        
    }
}
