package org.ssanalytics.snapshotplugin.io.crawlerInterface;


public class CrawlerDbSaverTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			
            
            //this only works with a db configuration for mongo
            
		ICrawlerDbSaver dbSaver = new CrawlerDbSaverMongo();
		
		try {
			dbSaver.startSavingSession("testshot", "420021521548566");
			
			dbSaver.saveChunk("{\"category\": \"Book\", \"created_time\": \"2012-02-24T18:46:25+0000\", \"id\": \"113337825343690\", \"name\": \"Emilia Galotti\"}", ChunkNames.books, "2929292992");
			
			dbSaver.endSavingSession();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
