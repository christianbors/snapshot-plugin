package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.tagged;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged.ITagged;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged.ITaggedApplication;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged.ITaggedProperties;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedActionJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedCommentsJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedFromJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedToJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractTimedDomainJsonImport;

public class TaggedJsonImport extends AbstractTimedDomainJsonImport implements ITagged{

	public TaggedJsonImport(JSONObject job) {
		super(job);
	}

	public List<ISharedAction> getActions() {
            JSONArray jar = getJar("actions");
            List<ISharedAction> list = new ArrayList<ISharedAction>();
		
            if (jar != null) 
                for (Object o : jar) 
                    list.add(new SharedActionJsonImport((JSONObject) o));
                
            return list;
	}

	public ITaggedApplication getApplication() {
		JSONObject temp = getJob("application");
		return temp == null ? null : new TaggedApplicationJsonImport(temp);
	}

	public String getCaption() {
		return getString("caption");
	}

	public ISharedComments getComments() {
		JSONObject temp = getJob("comments");
		return temp == null ? null : new SharedCommentsJsonImport(temp);
	}

	public ISharedFrom getFrom() {
		JSONObject temp = getJob("from");
		return temp == null ? null : new SharedFromJsonImport(temp);
	}

	public String getIcon() {
		return getString("icon");
	}

	public String getLink() {
		return getString("link");
	}

	public String getPicture() {
		return getString("picture");
	}

	public ISharedTo getTo() {
		JSONObject temp = getJob("to");
		return temp == null ? null : new SharedToJsonImport(temp);
	}

	public String getType() {
		return getString("type");
	}

	public Long getUpdated_time() {
		// TODO parse time
		return null;
	}

	public String getDescription() {
		return getString("description");
	}

	public String getMessage() {
		return getString("message");
	}

    @Override
    public List<ITaggedProperties> getProperties() {
        JSONArray jar = getJar("properties");
		
	List<ITaggedProperties> list = new ArrayList<ITaggedProperties>();
	
        if(jar != null)
            for(Object o : jar)
		list.add(new TaggedPropertiesJsonImport((JSONObject) o));	
		
        return list;
    }

}
