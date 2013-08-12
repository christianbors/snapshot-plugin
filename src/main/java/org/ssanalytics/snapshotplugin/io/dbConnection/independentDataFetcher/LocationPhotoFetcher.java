/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;

/**
 *
 * @author chw
 */
public class LocationPhotoFetcher {

    private LocationPhotoFetcher() {
    }
    private static LocationPhotoFetcher instance = null;

    public static LocationPhotoFetcher getInstance() {
        if (instance == null) {
            instance = new LocationPhotoFetcher();
        }
        return instance;
    }

    public Map<ISharedPlace, List<NamedItem>> getWhoHasBeenWhereSnapshotLatestVersion(String snapshot) throws Exception {

        List<IFriend> friendList = DaoFactory.getFriendDAO().getFriendListLatestVersion(snapshot);

        Map<ISharedPlace, List<NamedItem>> retMap = new HashMap<>();

        for (IFriend friend : friendList) {

            NamedItem ni = new NamedItem(friend.getId(), friend.getName());

            for (ISharedPlace place : DaoFactory.getPhotoDAO().getLocationsForProfileSnapshotLatestVersion(friend.getId(), snapshot)) {
                if (retMap.containsKey(place)) {
                    List<NamedItem> names = retMap.get(place);
                    if (!names.contains(ni)) {
                        names.add(ni);
                        retMap.put(place, names);
                    }
                } else {
                    List<NamedItem> names = new LinkedList<>();
                    names.add(ni);
                    retMap.put(place, names);
                }
            }
        }

        return retMap;

    }
}
