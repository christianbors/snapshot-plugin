package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.video;


import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideoFormat;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideoTags;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.video.VideoFormatJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedCommentsJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.shared.SharedFromJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractIdentityDomainJsonMongo;

public class VideoJsonMongo extends AbstractIdentityDomainJsonMongo implements IVideo{
	
	public VideoJsonMongo(DBObject job) {
		super(job);
	}

	public long getUpdated_time() {
		return this.getDataTime("updated_time");
	}

	public IVideoTags getTags() {
		DBObject temp = this.getDataDbo("tags");		
		
		return temp != null ? new VideoTagsJsonMongo(temp) : null;
	}

	public String getSource() {
		return this.getDataString("source");
	}

	public String getPicture() {
		return this.getDataString("picture");
	}

	public String getIcon() {
		return this.getDataString("icon");
	}

	public ISharedFrom getFrom() {
		DBObject temp = this.getDataDbo("from");	
		
		return temp != null ? new SharedFromJsonMongo(temp) : null;
	}

	public String getEmbed_html() {
		return this.getDataString("embed_html");
	}

	public String getDescription() {
		return this.getDataString("description");
	}

	public long getCreated_time() {
		//TODO cast timestamp
		return 0;
	}

	public ISharedComments getComments() {
		DBObject temp = this.getDataDbo("comments");	
		
		return temp != null ? new SharedCommentsJsonMongo(temp) : null;
	}

    @Override
    public List<IVideoFormat> getFormatList() {
        BasicDBList dbList = (BasicDBList) this.getDataList("format");
        
        List<IVideoFormat> al = new ArrayList(dbList.size());
        if(dbList != null){            
            for(Object o : dbList){
                if(o != null)
                    al.add(new VideoFormatJsonMongo((DBObject) o));
            }
        }
        
        return al;
    }

}
