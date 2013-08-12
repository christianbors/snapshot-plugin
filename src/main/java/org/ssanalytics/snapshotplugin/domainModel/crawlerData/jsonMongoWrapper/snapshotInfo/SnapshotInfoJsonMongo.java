package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.snapshotInfo;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses.AbstractBaseDomainJsonMongo;

public class SnapshotInfoJsonMongo extends AbstractBaseDomainJsonMongo implements ISnapshotInfo{

	public SnapshotInfoJsonMongo(DBObject job) {
		super(job);
	}

	@Override
	public String getValue() {
		return (String) this.dbo.get("value");
	}

	
	private DBObject getValueObject(){
		return (DBObject) this.dbo.get(this.getValue());
	}
	
	@Override
	public String getRoot() {
		
		return (String) this.getValueObject().get("root");
	}

	@Override
	public String version() {
		
		Object o = this.getValueObject().get("version");
		
		if(o == null)
			return "";
		else
			return o.toString();
		
	}

	@Override
	public List<String> getFriends() {
		
		List<String> returnList = new ArrayList<String>();
		
		BasicDBList bdl = (BasicDBList) this.getValueObject().get("friends");
		for (Object o : bdl){
			returnList.add(o.toString());			
		}			
		
		return returnList;
	}
	
	@Override
	public String toString() {
		return this.getValue() + " " + this.getRoot();
	}

}
