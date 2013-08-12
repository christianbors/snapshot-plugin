package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.link;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.link.ILink;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPrivacy;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractTimedDomainSql;

public class LinkSql extends AbstractTimedDomainSql implements ILink{

	private ISharedFrom from;
	private String icon;
	private String link;
	private String picture;
	private String description;
	private String message;
	private ISharedComments comments; 
        private ISharedPrivacy privacy;
	
	public LinkSql(String id, String name, long created_time, ISharedFrom from, String icon, String link, String picture, String description, String message, ISharedComments comments, ISharedPrivacy privacy) {
		super(id, name, created_time);
		
		this.from = from;
		this.icon = icon;
		this.link = link;
		this.picture = picture;
		this.description = description;
		this.message = message;
                this.privacy = privacy;
		
		this.comments = comments;
	}


	public ISharedFrom getFrom() {
		return this.from;
	}


	public String getIcon() {
		return this.icon;
	}


	public String getLink() {
		return this.link;
	}

	public String getPicture() {
		return this.picture;
	}


	public String getDescription() {
		return this.description;
	}

	public String getMessage() {
		return this.message;
	}

	public ISharedComments getComments() {
		return this.comments;
	}

    @Override
    public ISharedPrivacy getPrivacy() {
        return this.privacy;
    }
}
