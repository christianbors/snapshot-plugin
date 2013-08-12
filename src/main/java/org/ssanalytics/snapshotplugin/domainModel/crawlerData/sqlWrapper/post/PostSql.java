package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.post;

import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.post.IPost;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.post.IPostApplication;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPrivacy;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractNamedDomainSql;

public class PostSql extends AbstractNamedDomainSql implements IPost{

	
	private List<ISharedAction> actionList;
	private ISharedComments comments;
	private ISharedFrom from;
	private String icon;
	private Long created_time;
	private Long updated_time;
	private ISharedLike likes; 
	private String picture; 
	private String type; 
	private String story; 
	private String link;
	private String object_id;
        private ISharedPrivacy privacy;
        private ISharedPlace place;
        private String caption;
        private String source;
        private String description;
        private IPostApplication application;
	
	
	public PostSql(String id, List<ISharedAction> actionList, ISharedComments comments, ISharedFrom from, String icon,	Long created_time, Long updated_time, ISharedLike likes, String picture, String type, String story, String link, String object_id, ISharedPlace place, ISharedPrivacy privacy, String name, String description, String source, String caption, IPostApplication application) {
		super(id, name);

		this.link = link;
		this.object_id = object_id;
		this.actionList = actionList;
		this.comments = comments;
		this.from = from;
		this.icon = icon;
		this.created_time = created_time;
		this.updated_time = updated_time;
		this.likes = likes;
		this.picture = picture;
		this.type = type;
		this.story = story;
                this.privacy = privacy;
                this.place = place;
                this.application = application;
                this.caption = caption;
                this.source = source;
                this.description = description;
	}


	public List<ISharedAction> getActionList() {
		return actionList;
	}


	public ISharedComments getComments() {
		return comments;
	}


	public ISharedFrom getFrom() {
		return from;
	}


	public String getIcon() {
		return icon;
	}


	public Long getCreated_time() {
		return created_time;
	}


	public Long getUpdated_time() {
		return updated_time;
	}


	public ISharedLike getLikes() {
		return likes;
	}


	public String getPicture() {
		return picture;
	}


	public String getType() {
		return type;
	}


	public String getStory() {
		return story;
	}


	public String getLink() {
		return link;
	}


	public String getObject_id() {
		return object_id;
	}

    @Override
    public ISharedPlace getPlace() {
        return this.place;
    }

    @Override
    public ISharedPrivacy getPrivacy() {
        return this.privacy;
    }

    @Override
    public String getCaption() {
        return this.caption;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getSource() {
        return this.source;
    }

    @Override
    public IPostApplication getApplication() {
        return this.application;
    }


}
