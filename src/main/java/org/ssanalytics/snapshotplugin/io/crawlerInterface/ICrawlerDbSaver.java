package org.ssanalytics.snapshotplugin.io.crawlerInterface;

public interface ICrawlerDbSaver {
	
	public void startSavingSession(String snapshotName, String root_fb_id) throws Exception;
	public void endSavingSession();
	public void saveChunk(String data, ChunkNames chunkName, String fb_id) throws Exception;

}
