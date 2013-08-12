package org.ssanalytics.snapshotplugin.io.crawlerInterface;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;
import java.net.UnknownHostException;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;

public class CrawlerDbSaverMongo implements ICrawlerDbSaver {
	
	
	private String snapshotName;
	private String version;
	private Long timestamp;
	private com.mongodb.DB db;
	private boolean init;
	
	public CrawlerDbSaverMongo(){
		this.init = false;
	}

	private com.mongodb.DB openDatabase() throws UnknownHostException,	MongoException {
	
            Mongo m = new Mongo(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getHost(), Integer.parseInt(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getPort()));
            
            return m.getDB(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbName());
	}
	
	private String findNextVersionForSnapshot(String snapshotName) throws UnknownHostException, MongoException{
		
		DBCollection snapshotInfoCollection = this.db.getCollection("snapshotInfo");
		DBCursor snapshotInfoCursor = snapshotInfoCollection.find(new BasicDBObject().append("value", snapshotName));
		int highestVersion = 0;
		if (snapshotInfoCursor.hasNext()) {
			for (DBObject snapshot : snapshotInfoCursor) {
				if (snapshot != null) {
					String version = ((DBObject) snapshot.get(snapshot.get(
							"value").toString())).get("version").toString();
					if (version != null) {
						int tempVersion = Integer.parseInt(version);
						if (tempVersion > highestVersion)
							highestVersion = tempVersion;
					}
				}
			}
		}
		
		return String.valueOf(highestVersion + 1);
	}
	
	@Override
	public void startSavingSession(String snapshotName, String root_fb_id) throws UnknownHostException, MongoException {
		
		this.init = true;
		this.db = this.openDatabase();
		
		this.timestamp = System.currentTimeMillis();
		this.snapshotName = snapshotName;
		this.version = this.findNextVersionForSnapshot(snapshotName);
		
		DBObject snapshotInfo = new BasicDBObject();
		snapshotInfo.put("timestamp", this.timestamp);
		snapshotInfo.put("root", root_fb_id);
		snapshotInfo.put("version", this.version);
		
		DBObject snapshot = new BasicDBObject();
		snapshot.put("value", snapshotName);
		snapshot.put(snapshotName, snapshotInfo);

		DBCollection snapCollection = this.db.createCollection("snapshotInfo", null);
		
		snapCollection.insert(snapshot);		
	}

	@Override
	public void saveChunk(String data, ChunkNames chunkName, String fb_id) {
		
		if (this.init) {
			DBCollection dbc = this.db.createCollection(chunkName.toString(), null);

			DBObject chunk = this.getNewChunk(fb_id);
			chunk.put("data", (DBObject) JSON.parse(data));
			dbc.insert(chunk);
		}
		
	}

	@Override
	public void endSavingSession() {
		this.db = null;
		this.snapshotName = null;
		this.version = null;
		this.timestamp = null;
		this.init = false;
		
	}
	
	private DBObject getNewChunk(String accountId) {

		DBObject dbo = new BasicDBObject();

		dbo.put("snapshot", this.snapshotName);
		dbo.put("timestamp", this.timestamp);
		dbo.put("accountId", accountId);
		dbo.put("version", this.version);

		return dbo;
	}

}
