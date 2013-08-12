/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.dao.postgreSql.snapshotinfo;

import java.util.Date;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;

/**
 *
 * @author chw
 */
public class TestGetLastDateForEvidenceDomainsSql {
    
    public static void main(String args[]) throws Exception{
        
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
        
        Date maxDate = DaoFactory.getSnapshotInfoDAO().getLastDateForEvidenceDomainsInSnapshotLatestVersion("dd");
        
        System.out.println(maxDate.toString());
        
    }
    
}
