/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher;

import java.util.HashMap;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;

/**
 *
 * @author chw
 */
public class EvidenceFetcher {

    private EvidenceFetcher() {
    }
    
    private static EvidenceFetcher instance = null;

    public static EvidenceFetcher getInstance() {
        if (instance == null) {
            instance = new EvidenceFetcher();
        }
        return instance;
    }

    public Map<String, Integer> getEvidenceCountForTimespanForSnapsthotLatestVersion(String snapshot, long timeStart, long timeEnd) throws Exception {

        return this.getEvidenceCountForTimespanForSnapsthotSpecificVersion(snapshot, DaoFactory.getSnapshotInfoDAO().findHighestVersion(snapshot), timeStart, timeEnd);

    }

    private int getTotalCount(Map<CategoryItem, Integer> map) {

        int cnt = 0;
        for (Integer i : map.values()) {
            cnt += i;
        }

        return cnt;
    }

    public Map<String, Integer> getEvidenceCountForTimespanForSnapsthotSpecificVersion(String snapshot, String version, long timeStart, long timeEnd) throws Exception {

        Map<String, Integer> retMap = new HashMap<>();

        //activities
        retMap.put("activities", this.getTotalCount(DaoFactory.getActivityDAO().getEvidenceCountForSnapshotSpecificVersion(snapshot, version, timeStart, timeEnd)));

        //books                
        retMap.put("books", this.getTotalCount(DaoFactory.getBookDAO().getEvidenceCountForSnapshotSpecificVersion(snapshot, version, timeStart, timeEnd)));

        //interests
        retMap.put("interests", this.getTotalCount(DaoFactory.getInterestDAO().getEvidenceCountForSnapshotSpecificVersion(snapshot, version, timeStart, timeEnd)));

        //likes
        retMap.put("likes", this.getTotalCount(DaoFactory.getLikeDAO().getEvidenceCountForSnapshotSpecificVersion(snapshot, version, timeStart, timeEnd)));

        //music
        retMap.put("music", this.getTotalCount(DaoFactory.getMusicDAO().getEvidenceCountForSnapshotSpecificVersion(snapshot, version, timeStart, timeEnd)));

        //movie
        retMap.put("movies", this.getTotalCount(DaoFactory.getMovieDAO().getEvidenceCountForSnapshotSpecificVersion(snapshot, version, timeStart, timeEnd)));

        //tv
        retMap.put("television", this.getTotalCount(DaoFactory.getTelevisionDAO().getEvidenceCountForSnapshotSpecificVersion(snapshot, version, timeStart, timeEnd)));

        return retMap;

    }
}
