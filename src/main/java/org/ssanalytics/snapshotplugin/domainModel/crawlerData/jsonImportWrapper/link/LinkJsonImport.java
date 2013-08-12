package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.link;

import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.link.ILink;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPrivacy;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedCommentsJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedFromJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedPrivacyJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractTimedDomainJsonImport;

public class LinkJsonImport extends AbstractTimedDomainJsonImport implements ILink {

	public LinkJsonImport(JSONObject job) {
		super(job);
	}

	public ISharedFrom getFrom() {		
		
		JSONObject temp = this.getJob("from");
				
		return temp == null ? null : new SharedFromJsonImport(temp);
	}

	public String getIcon() {
		return this.getString("icon");
	}

	public String getLink() {
		return this.getString("link");
	}

	public String getPicture() {
		return this.getString("picture");
	}

	public String getDescription() {
		return this.getString("description");
	}

	public String getMessage() {
		return this.getString("message");
	}

	public ISharedComments getComments() {
		
		JSONObject temp = this.getJob("comments");
		
		return temp == null ? null : new SharedCommentsJsonImport(temp);
	}
        
        @Override
        public ISharedPrivacy getPrivacy() {
            
            JSONObject temp = this.getJob("privacy");
            
            return temp == null ? null : new SharedPrivacyJsonImport(temp);
    }
}
