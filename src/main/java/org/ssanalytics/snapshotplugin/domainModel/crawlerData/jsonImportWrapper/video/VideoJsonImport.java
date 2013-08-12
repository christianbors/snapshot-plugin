package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.video;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideoFormat;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideoTags;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedCommentsJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.shared.SharedFromJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractIdentityDomainJsonImport;

public class VideoJsonImport extends AbstractIdentityDomainJsonImport implements IVideo{
	
	public VideoJsonImport(JSONObject job) {
		super(job);
	}

	public long getUpdated_time() {
		return this.getTime("updated_time");
	}

	public IVideoTags getTags() {
		JSONObject temp = (JSONObject) this.job.get("tags");		
		
		return temp != null ? new VideoTagsJsonImport(temp) : null;
	}

	public String getSource() {
		return this.job.get("source").toString();
	}

	public String getPicture() {
		return this.job.get("picture").toString();
	}

	public String getIcon() {
		return this.job.get("icon").toString();
	}

	public ISharedFrom getFrom() {
		JSONObject temp = (JSONObject) this.job.get("from");		
		
		return temp != null ? new SharedFromJsonImport(temp) : null;
	}

	public String getEmbed_html() {
		return this.job.get("embed_html").toString();
	}

	public String getDescription() {
		return this.getString("description");
	}

	public long getCreated_time() {
		//TODO cast timestamp
		return 0;
	}

	public ISharedComments getComments() {
		JSONObject temp = (JSONObject) this.job.get("comments");		
		
		return temp != null ? new SharedCommentsJsonImport(temp) : null;
	}

    @Override
    public List<IVideoFormat> getFormatList() {
        JSONArray jar = (JSONArray) this.getJar("format");
        List<IVideoFormat> al = new ArrayList(jar.size());
        if(jar != null){            
            for(Object o : jar){
                if(o != null)
                    al.add(new VideoFormatJsonImport((JSONObject) o));
            }
        }
        
        return al;
    }

}
