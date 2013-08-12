/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhoto;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoTag;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoTagData;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;

/**
 *
 * @author chw
 */
public class PhotoFetcher {

    private PhotoFetcher() {
    }
    private static PhotoFetcher instance = null;

    public static PhotoFetcher getInstance() {
        if (instance == null) {
            instance = new PhotoFetcher();
        }
        return instance;
    }

    public Map<IPhoto, Integer> getLikeCountForPhotosForSnapshotLatestVersion(String snapshot) throws Exception {
        return this.getLikeCountForPhotosForSnapshotSpecificVersion(snapshot, DaoFactory.getSnapshotInfoDAO().findHighestVersion(snapshot));

    }

    public Map<IPhoto, Integer> getLikeCountForPhotosForSnapshotSpecificVersion(String snapshot, String version) throws Exception {

        List<IPhoto> photos = DaoFactory.getPhotoDAO().getPhotoListSnapshotSpecificVersion(snapshot, version);

        Map<IPhoto, Integer> retMap = new HashMap<>();

        for (IPhoto p : photos) {
            if (p.getLikes() != null) {
                if (p.getLikes().getDataList() != null) {
                    retMap.put(p, p.getLikes().getDataList().size());
                    continue;
                }
            }


            retMap.put(p, 0);

        }

        return retMap;
    }

    public Map<String, Integer> getTaggedPhotoCountForSnapshotLatestVersion(String snapshot) throws Exception {

        return this.getTaggedPhotoCountForSnapshotSpecificVersion(snapshot, DaoFactory.getSnapshotInfoDAO().findHighestVersion(snapshot));

    }

    public Map<String, Integer> getTaggedPhotoCountForSnapshotSpecificVersion(String snapshot, String version) throws Exception {

        Map<String, Integer> retMap = new HashMap<>();

        List<IPhoto> photos = DaoFactory.getPhotoDAO().getPhotoListSnapshotLatestVersion(snapshot);

        for (IPhoto p : photos) {
            if ((p != null) && (p.getPhotoTags() != null) && (p.getPhotoTags().getTagDataList() != null)) {
                for (IPhotoTagData data : p.getPhotoTags().getTagDataList()) {
                    String s = data.getName();

                    Integer i = 1;
                    if (retMap.containsKey(s)) {
                        i = retMap.get(s);
                        i++;
                    }

                    retMap.put(s, i);
                }
            }
        }

        return retMap;
    }

    public Map<NamedItem, Float> getAverageTaggedCountForPhotosOfEveryProfileInSnapshotSpecificVersion(String snapshot, String version) throws Exception {

        List<String> profileIdList = DaoFactory.getFriendDAO().getFriendIdListSpecificVersion(snapshot, version);

        Map<NamedItem, Float> retMap = new HashMap<>();

        for (String pid : profileIdList) {
            List<IPhoto> photoList = DaoFactory.getPhotoDAO().getPhotoListForProfileInSnapshotSpecificVersion(snapshot, version, pid);
            float totalPhotos = photoList.size();
            float totalTags = 0;

            for (IPhoto photo : photoList) {
                try {
                    totalTags += photo.getPhotoTags().getTagDataList().size();
                } catch (Exception ex) {
                }
            }

            NamedItem ni = new NamedItem(pid, DaoFactory.getProfileDAO().getNameForAccountId(pid));

            retMap.put(ni, ((float) (totalTags / totalPhotos)));
        }

        return retMap;
    }

    public Map<NamedItem, Float> getAverageTaggedCountForPhotosOfEveryProfileInSnapshotLatestVersion(String snapshot) throws Exception {
        return this.getAverageTaggedCountForPhotosOfEveryProfileInSnapshotSpecificVersion(snapshot, DaoFactory.getSnapshotInfoDAO().findHighestVersion(snapshot));

    }
}
