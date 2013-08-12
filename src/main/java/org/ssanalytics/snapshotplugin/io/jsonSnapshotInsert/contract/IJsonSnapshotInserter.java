package org.ssanalytics.snapshotplugin.io.jsonSnapshotInsert.contract;

import java.io.File;

public interface IJsonSnapshotInserter {
	public void insertFromFile(File file, String snapshotName, Long timestamp) throws Exception;
        
}
