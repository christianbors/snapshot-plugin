/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.movie.IMovie;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.movie.MovieJsonMongo;

/**
 *
 * @author chw
 */
public class CategoryItemDAOMongo extends AbstractSuperDAOMongo {

    protected CategoryItemDAOMongo() throws UnknownHostException, MongoException {
        super();
    }
    private static CategoryItemDAOMongo instance = null;

    public static CategoryItemDAOMongo getInstance() throws UnknownHostException, MongoException {
        if (instance == null) {
            instance = new CategoryItemDAOMongo();
        }
        return instance;
    }

    public Long getFirstDate(String snapshot, String version, String collection_name) throws MongoException, UnknownHostException {

        BasicDBObject params = new BasicDBObject("snapshot", snapshot).append("version", version);
        DBCursor dbc = this.findInCollection(collection_name, params);

        Long min = System.currentTimeMillis();
        Long temp = null;

        for (DBObject dbo : dbc) {
            temp = new MovieJsonMongo(dbo).getCreated_time();
            if (temp != null) {
                if (temp < min) {
                    if (temp > 1000) {
                        min = temp;
                    }
                }
            }
        }

        return temp == null ? null : min;
    }

    public Long getLastDate(String snapshot, String version, String collection_name) throws MongoException, UnknownHostException {

        BasicDBObject params = new BasicDBObject("snapshot", snapshot).append("version", version);
        DBCursor dbc = this.findInCollection(collection_name, params);

        Long max = 0l;
        Long temp = null;

        for (DBObject dbo : dbc) {
            temp = new MovieJsonMongo(dbo).getCreated_time();
            if (temp != null) {
                if (temp > max) {
                    max = temp;
                }
            }
        }

        return max == 0 ? null : max;
    }

    private Map<CategoryItem, Integer> getEvidenceCount(String collection_name, DBObject params) throws MongoException, UnknownHostException {

        DBCursor dbc = this.findInCollection(collection_name, params);

        Map<CategoryItem, Integer> retMap = new HashMap<>();
        CategoryItem ci;
        Integer i;
        IMovie movie;

        int cnt = 0;

        for (DBObject dbo : dbc) {

            cnt++;

            //TODO: use a more generic CategoryDomain and not movie, but any domain extending AbstractCategorizedDomainJsonMongo should work.
            movie = new MovieJsonMongo(dbo);
            ci = new CategoryItem(movie.getId(), movie.getName(), movie.getCategory(), movie.getCreated_time());

            if (retMap.containsKey(ci)) {
                i = retMap.get(ci);
                i++;
                retMap.remove(ci);
                retMap.put(ci, i);
            } else {
                retMap.put(ci, 1);
            }
        }

        return retMap;
    }

    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotSpecificVersion(String snapshotId, String version, String collection_name) throws MongoException, UnknownHostException {

        BasicDBObject params = new BasicDBObject().append("snapshot", snapshotId).append("version", version);

        return this.getEvidenceCount(collection_name, params);
    }

    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotSpecificVersion(String snapshotId, String version, String collection_name, long timeStart, long timeEnd) throws MongoException, UnknownHostException {


        Date start = new Date(timeStart);
        String startString = (1900 + start.getYear()) + "-" + (1 + start.getMonth()) + "-" + start.getDate();
        Date end = new Date(timeEnd);
        String endString = (1900 + end.getYear()) + "-" + (1 + end.getMonth()) + "-" + end.getDate();

        BasicDBObject params = new BasicDBObject().append("snapshot", snapshotId).append("version", version);
        params.append("data.created_time", new BasicDBObject("$gt", startString).append("$lt", endString));


        return this.getEvidenceCount(collection_name, params);
    }

    public List<String> getDistinctCategories(String collection_name) throws MongoException, UnknownHostException {

        List<String> list = new LinkedList<>();
        BasicDBObject params = new BasicDBObject();

        DBObject dbo = this.findOne(collection_name, params);

        while (dbo != null) {
            String value = (String) ((DBObject) dbo.get("data")).get("category");
            if (value == null) {
                break;
            }
            list.add(value);
            params = new BasicDBObject("data.category", new BasicDBObject("$nin", list));

            dbo = this.findOne(collection_name, params);
        }

        return list;
    }

    public Map<String, String> getDistinctSnapshotsAndHighestVersion() throws MongoException, UnknownHostException {

        Map<String, String> retMap = new HashMap<>();

        List<String> shots = new LinkedList<>();

        BasicDBObject params = new BasicDBObject();

        DBObject dbo = this.findOne("snapshotInfo", params);

        while (dbo != null) {
            String value = (String) dbo.get("value");

            if (value == null) {
                break;
            }

            String version = this.findHighestVersionForSnapshot(value);
            shots.add(value);
            retMap.put(value, version);
            params = new BasicDBObject("value", new BasicDBObject("$nin", shots));

            dbo = this.findOne("snapshotInfo", params);
        }
        return retMap;
    }

    public Map<String, Integer> getExtendedDistinctCategories(String collection_name) throws MongoException, UnknownHostException {
        Map<String, Integer> retMap = new HashMap<>();

        Map<String, String> shots = this.getDistinctSnapshotsAndHighestVersion();

        for (String snapshot : shots.keySet()) {

            BasicDBObject params = new BasicDBObject().append("snapshot", snapshot).append("version", shots.get(snapshot));

            DBCursor dbc = this.findInCollection(collection_name, params);

            while (dbc.hasNext()) {

                String category = (String) ((DBObject) dbc.next().get("data")).get("category");
                if (category == null) {
                    continue;
                }

                if (retMap.containsKey(category)) {
                    Integer i = retMap.get(category);
                    i++;
                    retMap.put(category, i);
                } else {
                    retMap.put(category, 1);
                }
            }
        }

        return retMap;
    }
}
