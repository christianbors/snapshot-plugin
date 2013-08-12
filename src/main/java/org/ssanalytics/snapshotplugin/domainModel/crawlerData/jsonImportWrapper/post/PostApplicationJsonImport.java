/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.post;

import org.json.simple.JSONObject;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.post.IPostApplication;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractNamedDomainJsonImport;

/**
 *
 * @author chw
 */
public class PostApplicationJsonImport extends AbstractNamedDomainJsonImport implements IPostApplication{
    
    public PostApplicationJsonImport(JSONObject job){
        super(job);
    }
}
