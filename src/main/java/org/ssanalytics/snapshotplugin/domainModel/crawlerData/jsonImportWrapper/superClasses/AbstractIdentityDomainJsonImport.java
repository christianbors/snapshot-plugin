package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses;

import org.json.simple.JSONObject;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IIdentityDomain;

public abstract class AbstractIdentityDomainJsonImport extends AbstractBaseDomainJsonImport implements IIdentityDomain{

	public AbstractIdentityDomainJsonImport(JSONObject job) {
		super(job);
	}
	
	public String getId(){     
            try{
		return (String) this.job.get("id");
            }catch(NullPointerException nex){

            }
            return null;
	}
}
