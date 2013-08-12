/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher;

import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.openide.util.NotImplementedException;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;

/**
 *
 * @author chw
 */
public class LocationFetcher {

    private LocationFetcher() {
    }
    private static LocationFetcher instance = null;

    public static LocationFetcher getInstance() {
        if (instance == null) {
            instance = new LocationFetcher();
        }
        return instance;
    }

    public Map<NamedItem, List<ISharedPlace>> getWhereFriendsHaveBeenSnapshotLatestVersion(String snapshot) throws Exception {

        List<IFriend> friendList = DaoFactory.getFriendDAO().getFriendListLatestVersion(snapshot);

        Map<NamedItem, List<ISharedPlace>> retMap = new HashMap<>();

        for (IFriend friend : friendList) {
            List<ISharedPlace> places = this.getDistinctPlacesWhereProfileHasBeenLatestVersion(friend.getId(), snapshot);
            NamedItem ni = new NamedItem(friend.getId(), friend.getName());
            retMap.put(ni, places);
        }

        return retMap;

    }

    public Map<ISharedPlace, List<NamedItem>> getWhoHasBeenWhereIHaveBeenSnapshotLatestVersion(String snapshot, String profile_id) throws Exception {

        Map<ISharedPlace, List<NamedItem>> placesVisitors = this.getLocationsAndVisitorsForProfilesForSnapshotLatestVersion(snapshot);
        Map<ISharedPlace, List<NamedItem>> retMap = new HashMap<>();
        List<ISharedPlace> profilePlaces = this.getDistinctPlacesWhereProfileHasBeenLatestVersion(profile_id, snapshot);

        NamedItem prof = new NamedItem(profile_id, "");

        for (ISharedPlace p : profilePlaces) {
            if (placesVisitors.containsKey(p)) {
                List<NamedItem> friends = placesVisitors.get(p);
                friends.remove(prof);
                if (friends.size() > 0) {
                    retMap.put(p, friends);
                }
            }
        }

        return retMap;

    }

    public Map<ISharedPlace, List<NamedItem>> getLocationsAndVisitorsForProfilesForSnapshotLatestVersion(String snapshot) throws Exception {
        List<IFriend> friendList = DaoFactory.getFriendDAO().getFriendListLatestVersion(snapshot);

        Map<ISharedPlace, List<NamedItem>> retMap = new HashMap<>();

        for (IFriend friend : friendList) {

            NamedItem ni = new NamedItem(friend.getId(), friend.getName());

            for (ISharedPlace place : this.getDistinctPlacesWhereProfileHasBeenLatestVersion(friend.getId(), snapshot)) {
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

    /**
     * This method takes the following domains into account: * photo * status *
     * post Latest Snapshot Version is used.
     *
     * @return returns a list with every location the profile has been.
     */
    public List<ISharedPlace> getDistinctPlacesWhereProfileHasBeenLatestVersion(String profileId, String snapshot) throws Exception {
        List<ISharedPlace> retList = new LinkedList<>();

        List<ISharedPlace> list = DaoFactory.getPostDAO().getLocationsForProfileSnapshotLatestVersion(profileId, snapshot);



        list.addAll(DaoFactory.getPhotoDAO().getLocationsForProfileSnapshotLatestVersion(profileId, snapshot));



        list.addAll(DaoFactory.getStatusDAO().getLocationsForProfileSnapshotLatestVersion(profileId, snapshot));

        for (ISharedPlace place : list) {
            if (!retList.contains(place)) {
                retList.add(place);
            }
        }

        return retList;
    }

    /**
     * This method takes the following domains into account: * photo * status *
     * post Latest Snapshot Version is used.
     *
     * @return returns a map with every location the profile has been + the
     * number of occurencies.
     */
    public Map<ISharedPlace, Integer> getPlacesAndCountWhereProfileHasBeenLatestVersion(String profileId, String snapshot) throws Exception {

        Map<ISharedPlace, Integer> retMap = new HashMap<>();

        List<ISharedPlace> list = DaoFactory.getPostDAO().getLocationsForProfileSnapshotLatestVersion(profileId, snapshot);

        list.addAll(DaoFactory.getPhotoDAO().getLocationsForProfileSnapshotLatestVersion(profileId, snapshot));

        list.addAll(DaoFactory.getStatusDAO().getLocationsForProfileSnapshotLatestVersion(profileId, snapshot));

        for (ISharedPlace place : list) {
            if (retMap.containsKey(place)) {
                Integer i = retMap.get(place);
                i++;
                retMap.put(place, i);
            } else {
                retMap.put(place, 1);
            }
        }

        return retMap;
    }
}
