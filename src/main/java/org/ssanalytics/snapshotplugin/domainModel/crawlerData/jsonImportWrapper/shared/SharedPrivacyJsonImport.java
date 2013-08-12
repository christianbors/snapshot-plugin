/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared;

import org.json.simple.JSONObject;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPrivacy;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractBaseDomainJsonImport;

/**
 *
 * @author chw
 */
public class SharedPrivacyJsonImport extends AbstractBaseDomainJsonImport implements ISharedPrivacy{

    
    public SharedPrivacyJsonImport(JSONObject job){
        super (job);
    }
    
    @Override
    public String getDescription() {
        return this.getString("description");
    }

    @Override
    public String getValue() {
        return this.getString("value");
    }
    
}
