package org.ssanalytics.snapshotplugin.test.expandedArchiveReader;

import org.ssanalytics.snapshotplugin.test.independentDataFetcher.comments.*;
import java.util.Map;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.CommentsFetcher;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.io.expandedArchiveReader.ExpandedArchiveReader;

/**
 *
 * @author chw
 */
public class testWrongDirectory {

    public static void main(String args[]) throws Exception {
        
        ExpandedArchiveReader ear = ExpandedArchiveReader.getInstance();
        
        //should throw exception
        ear.readExpandedArchiveFromDirectory("adlkfjasd;lkjf");
        
        //should throw exception
        ear.readExpandedArchiveFromDirectory("C:/tictactow");
    }
}
