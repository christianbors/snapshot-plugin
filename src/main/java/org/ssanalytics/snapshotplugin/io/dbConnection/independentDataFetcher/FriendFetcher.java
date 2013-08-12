/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher;

import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;

/**
 *
 * @author chw
 */
public class FriendFetcher {

    private FriendFetcher() {
    }
    private static FriendFetcher instance = null;

    public static FriendFetcher getInstance() {
        if (instance == null) {
            instance = new FriendFetcher();
        }
        return instance;
    }

    public float getAverageNumberOfMutualFriendsForSnapshotLatestVersion(String snapshotName) throws Exception {
        return this.getAverageNumberOfMutualFriendsForSnapshotSpecificVersion(snapshotName, DaoFactory.getSnapshotInfoDAO().findHighestVersion(snapshotName));
    }

    public float getAverageNumberOfMutualFriendsForSnapshotSpecificVersion(String snapshotName, String version) throws Exception {


        Map<String, List<String>> map = DaoFactory.getRootAccountDAO().getFriendIsFriend(snapshotName, version);

        float total = 0;
        for (String s : map.keySet()) {
            total += map.get(s).size();
        }

        float count = map.size();

        return total / count;


    }
}
