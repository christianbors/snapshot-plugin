package org.ssanalytics.snapshotplugin.ui.jsonSnapshotInsert;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mongodb.MongoException;

import java.io.File;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.DbMode;
import org.ssanalytics.snapshotplugin.io.jsonSnapshotInsert.contract.IJsonSnapshotInserter;
import org.ssanalytics.snapshotplugin.io.jsonSnapshotInsert.mongodb.JsonSnapshotInserterMongo;
import org.ssanalytics.snapshotplugin.io.jsonSnapshotInsert.sql.JsonSnapshotInserterSql;

public class SnapshotInsertController {

	public SnapshotInsertController() {
	}

	public String parseTimestampFromFilename(File snapshot) {
		Pattern p = Pattern.compile("^[a-zA-Z]+([0-9]+).*");
		Matcher m = p.matcher(snapshot.getName());
		
		if(m.find()) {
			System.out.println(m.group(1));
			return m.group(1);
		}
		else {
                return "";
            }
	}
	
	private IJsonSnapshotInserter getImporterDependingOnConfig() throws UnknownHostException, MongoException, SQLException {
	
            if(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode().equals(DbMode.POSTGRESQL)) {
                return new JsonSnapshotInserterSql();
            }
            
            if(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode().equals(DbMode.MYSQL)) {
                return new JsonSnapshotInserterSql();
            }
		
	
            if(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode().equals(DbMode.MONGODB)) {
                return new JsonSnapshotInserterMongo();
            }
		
		return null;
	}
	
	public void importFile(File snapshot, String timestamp, String snapshotName) throws Exception {
		
		this.getImporterDependingOnConfig().insertFromFile(snapshot, snapshotName, Long.parseLong(timestamp));	
		
	}
	
}
