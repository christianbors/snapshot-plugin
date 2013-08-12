package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.music;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.music.IMusic;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractCategorizedDomainSql;

public class MusicSql extends AbstractCategorizedDomainSql implements IMusic{

	public MusicSql(String id, String name, long created_time,
			String category) {
		super(id, name, created_time, category);
	}

}
