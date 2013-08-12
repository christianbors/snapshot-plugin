package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.video;

import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideoFormat;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideoTags;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractIdentityDomainSql;

public class VideoSql extends AbstractIdentityDomainSql implements IVideo{

	
	private long updated_time;
	private IVideoTags tags;
	private String source;
	private String picture;
	private String icon;
	private ISharedFrom from;
	private String embed_html;
	private String description;
	private long created_time;
	private ISharedComments comments;
        private List<IVideoFormat> formatList;
	
	public VideoSql(String id, long updated_time, IVideoTags tags, String source, String picture, String icon, ISharedFrom from, String embed_html, String description, long created_time, ISharedComments comments, List<IVideoFormat> formatList) {
		super(id);
                
                this.formatList = formatList;
		
		this.updated_time = updated_time;
		this.tags = tags;
		this.source = source;
		this.picture = picture;
		this.icon = icon;
		this.from = from;
		this.embed_html = embed_html;
		this.description = description;
		this.created_time= created_time;
		this.comments = comments;
	}


	public long getUpdated_time() {
		return this.updated_time;
	}


	public IVideoTags getTags() {
		return this.tags;
	}


	public String getSource() {
		return this.source;
	}


	public String getPicture() {
		return this.picture;
	}


	public String getIcon() {
		return this.icon;
	}


	public ISharedFrom getFrom() {
		return this.from;
	}


	public String getEmbed_html() {
		return this.embed_html;
	}


	public String getDescription() {
		return this.description;
	}


	public long getCreated_time() {
		return this.created_time;
	}


	public ISharedComments getComments() {
		return this.comments;
	}

    @Override
    public List<IVideoFormat> getFormatList() {
        return this.formatList;
    }

}
