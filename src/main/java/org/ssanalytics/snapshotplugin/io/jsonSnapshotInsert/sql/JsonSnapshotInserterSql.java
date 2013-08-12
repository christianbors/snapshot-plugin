package org.ssanalytics.snapshotplugin.io.jsonSnapshotInsert.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.AbstractSuperDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.AccountDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.RootAccountDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.SnapshotInfoDAOSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.account.AccountJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.account.RootAccountJsonImport;
import org.ssanalytics.snapshotplugin.io.jsonSnapshotInsert.contract.IJsonSnapshotInserter;

public class JsonSnapshotInserterSql extends AbstractSuperDAOSql implements IJsonSnapshotInserter {

	public JsonSnapshotInserterSql() throws SQLException {
		super();
	}

	private String snapshotName;
	private Long timestamp;
	private String version;
	private String rootId;

	@Override
	public void insertFromFile(File file, String snapshotName,
			Long timestamp) throws ParseException, SQLException, IOException {
		
		this.snapshotName = snapshotName;
		this.timestamp = timestamp;
		
		long daoTime = 0;
		
		JSONParser jp = new JSONParser();		

		SnapshotInfoDAOSql snapshotInfoDao = SnapshotInfoDAOSql.getInstance();
		
		this.version = String.valueOf(snapshotInfoDao.getNextVersionNumber(this.snapshotName));

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
		
		long snapshotInfoDbId = snapshotInfoDao.createSnapshotInfo(this.snapshotName, this.timestamp, this.version);

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
					RootAccountJsonImport rac = new RootAccountJsonImport(job);
					
					IProfile prof = rac.getProfile();
													
					this.rootId = prof.getId();
					
					List<IFriend> friendList = rac.getFriendList();
					
					RootAccountDAOSql racDAO = RootAccountDAOSql.getInstance();
					
					
					long st = System.currentTimeMillis();
					long rootAccountDbId = racDAO.saveRootAccount(snapshotInfoDbId, rac);
					long et = System.currentTimeMillis();
					
					System.out.println("Saved the root account in " + (et-st) + "ms");
					
					daoTime += et;
					daoTime -= st;
					
					snapshotInfoDao.updateSnapshotInfo(snapshotInfoDbId, this.rootId, friendList, rootAccountDbId);
					
					
				}
				else{
					AccountJsonImport ac = new AccountJsonImport(job);
					AccountDAOSql acDAO = AccountDAOSql.getInstance();
					
					long st = System.currentTimeMillis();
					acDAO.saveAccount(snapshotInfoDbId, ac);
					long et = System.currentTimeMillis();
					
					System.out.println("Saved account (" + ac.getProfile().getName() + ") in " + (et-st) + "ms");
					daoTime += et;
					daoTime -= st;
				}

				sb = new StringBuffer();
				sb.append("{");
			}
		}
		
		System.out.println("Import used "+ String.valueOf(daoTime) + " ms for Postgre DAOs");
		
		float time_needed_seconds = daoTime/1000;
		float time_needed_minutes = time_needed_seconds/60;
		System.out.println("That's exactly "+ String.valueOf(time_needed_minutes) + " minutos");
		br.close();
	}
}
