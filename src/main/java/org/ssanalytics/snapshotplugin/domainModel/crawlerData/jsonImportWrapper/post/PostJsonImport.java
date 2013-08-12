package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.post;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.post.IPost;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.post.IPostApplication;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPrivacy;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedActionJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedCommentsJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedFromJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedLikeJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedPlaceJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedPrivacyJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractNamedDomainJsonImport;

public class PostJsonImport extends AbstractNamedDomainJsonImport implements IPost{

	public PostJsonImport(JSONObject job) {
		super(job);
	}

	public List<ISharedAction> getActionList() {
		JSONArray jar = this.getJar("actions");
		
		List<ISharedAction> list = new ArrayList<ISharedAction>();
		
		if (jar != null)
			for (Object o : jar)
				list.add(new SharedActionJsonImport((JSONObject) o));
		
		return list;
	}

	public ISharedComments getComments() {
		JSONObject temp = this.getJob("comments");
		return temp == null ? null : new SharedCommentsJsonImport(temp);
	}

	public ISharedFrom getFrom() {
		JSONObject temp = this.getJob("from");
		return temp == null ? null : new SharedFromJsonImport(temp);
	}

	public String getIcon() {
		return getString("icon");
	}

	public Long getCreated_time() {
		return this.getTime("created_time");
	}

	public Long getUpdated_time() {
		return this.getTime("updated_time");
	}

	public ISharedLike getLikes() {
		JSONObject temp = this.getJob("likes");
		return temp == null ? null : new SharedLikeJsonImport(temp);
	}

	public String getLink() {
		return getString("link");
	}

	public String getObject_id() {
		return getString("object_id");
	}

	public String getPicture() {
		return getString("picture");
	}

	public String getType() {
		return getString("type");
	}

	public String getStory() {
		return getString("story");
	}

    @Override
    public ISharedPlace getPlace() {
        return new SharedPlaceJsonImport(this.getJob("place"));
    }

    @Override
    public ISharedPrivacy getPrivacy() {
        return new SharedPrivacyJsonImport(this.getJob("privacy"));
    }

    @Override
    public String getCaption() {
        return this.getString("caption");
    }

    @Override
    public String getDescription() {
        return this.getString("description");
    }

    @Override
    public String getSource() {
        return this.getString("source");
    }

    @Override
    public IPostApplication getApplication() {
        JSONObject temp = this.getJob("application");
        return temp == null ? null : new PostApplicationJsonImport(temp);
    }
}
