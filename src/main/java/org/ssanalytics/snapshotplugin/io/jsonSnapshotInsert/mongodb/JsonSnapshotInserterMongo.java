package org.ssanalytics.snapshotplugin.io.jsonSnapshotInsert.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.AbstractSuperDAOMongo;
import org.ssanalytics.snapshotplugin.io.jsonSnapshotInsert.contract.IJsonSnapshotInserter;

public class JsonSnapshotInserterMongo extends AbstractSuperDAOMongo implements
		IJsonSnapshotInserter {

	public JsonSnapshotInserterMongo() throws UnknownHostException, MongoException {
		super();
	}

	private String snapshotName;
	private Long timestamp;
	private String version;
	private String rootId;

	@Override
	public void insertFromFile(File file, String snapshotName,
			Long timestamp) throws MongoException, IOException, ParseException {

		List<String> friendsList = new ArrayList<String>();

		JSONParser jp = new JSONParser();

		com.mongodb.DB db = this.openDatabase();

		this.snapshotName = snapshotName;
		this.timestamp = timestamp;
		
		// SECTION: Determine version number of snapshotName
		DBCollection snapshotInfoCollection = db.getCollection("snapshotInfo");
		DBCursor snapshotInfoCursor = snapshotInfoCollection
				.find(new BasicDBObject().append("value", snapshotName));
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
		this.version = String.valueOf(highestVersion + 1);

		if (ConfigurationFileManager.getCurrentConfig().getOutputLevel() == 3) {
			System.out.println("New Version: " + this.version);
		}

		FileReader reader = new FileReader(file);
		BufferedReader br = new BufferedReader(reader);

		br.readLine();

		String line;
		int depth = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("{");

		JSONObject job;

		while ((line = br.readLine()) != null) {

			sb.append(line);
			sb.append("\n");
			if (line.trim().endsWith("{"))
				depth++;
			if (line.trim().startsWith("}"))
				depth--;

			if (depth == -1)
				break;

			if (depth == 0) {
				sb.append("}");

				job = (JSONObject) jp.parse(sb.toString());

				String s = job.keySet().toArray()[0].toString();

				job = (JSONObject) job.get(s);

				if (s.equals("root")) {

					JSONObject profileJson = (JSONObject) job.get("profile");
					rootId = profileJson.get("id").toString();
					JSONArray friendsArray = (JSONArray) job.get("friends");
					for (Object o : friendsArray) {
						friendsList.add(((JSONObject) o).get("id").toString());
					}

					DBObject snapshotInfo = new BasicDBObject();
					snapshotInfo.put("timestamp", timestamp);
					snapshotInfo.put("root", rootId);
					snapshotInfo.put("version", version);
					snapshotInfo.put("friends", friendsList);

					DBObject snapshot = new BasicDBObject();
					snapshot.put("value", snapshotName);
					snapshot.put(snapshotName, snapshotInfo);

					DBCollection snapCollection = db.createCollection(
							"snapshotInfo", null);
					snapCollection.insert(snapshot);
				}

				for (Object o : job.keySet()) {

					DBCollection dbc;

					if (o.toString().equals("inbox"))
						dbc = db.createCollection(o.toString(), null);
					else
						dbc = db.createCollection(o.toString(), null);

					Object toCast = job.get(o.toString());

					DBObject dbo;

					if (o.toString().equals("profile")
							|| o.toString().equals("friendisfriend")
							|| toCast.toString().equals("false")) {
						try {
							DBObject temp = (DBObject) JSON.parse(toCast
									.toString());
							dbo = getNewChunkAndDecideWhichOne(s);
							dbo.put("data", temp);
							dbc.insert(dbo);
						} catch (ClassCastException ccex) {
						}
					} else {
						try {

							JSONArray jar = (JSONArray) toCast;
							if (ConfigurationFileManager.getCurrentConfig().getOutputLevel() >= 3) {
								System.out.println(dbc.getName() + " has "
										+ jar.size() + " elements and is "
										+ jar.toString().length() + " big");
							}

							for (Object p : jar) {
								DBObject temp = (DBObject) JSON.parse(p
										.toString());
								dbo = getNewChunkAndDecideWhichOne(s);

								dbo.put("data", temp);

								dbc.save(dbo);
							}
						} catch (ClassCastException ccex) {
							ccex.printStackTrace();
						}

					}
				}

				sb = new StringBuffer();
				sb.append("{");
			}
		}
		br.close();
	}

	private DBObject getNewChunkAndDecideWhichOne(String s) {
		DBObject dbo;

		if (s.equals("root"))
			dbo = getNewChunk(rootId);
		else
			dbo = getNewChunk(s);

		return dbo;
	}

	private DBObject getNewChunk(String accountId) {

		DBObject dbo = new BasicDBObject();

		dbo.put("snapshot", snapshotName);
		dbo.put("timestamp", timestamp);
		dbo.put("accountId", accountId);
		dbo.put("version", version);

		return dbo;
	}

}
