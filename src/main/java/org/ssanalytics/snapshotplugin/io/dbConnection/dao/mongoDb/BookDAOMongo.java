/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IBookDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.CategoryItemDAOSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.book.IBook;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.movie.IMovie;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ICategorizedDomain;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account.RootAccountJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.movie.MovieJsonMongo;

/**
 *
 * @author Robert
 */
public class BookDAOMongo extends AbstractSuperDAOMongo implements IBookDAO {

    public final String collection_name = "books";

    protected BookDAOMongo() throws UnknownHostException, MongoException {
        super();
        // TODO Auto-generated constructor stub
    }
    private static BookDAOMongo instance = null;

    public static BookDAOMongo getInstance() throws UnknownHostException, MongoException {
        if (instance == null) {
            instance = new BookDAOMongo();
        }
        return instance;
    }

    @Override
    public List<String> getDistinctBookCategories() throws Exception {
        return CategoryItemDAOMongo.getInstance().getDistinctCategories(this.collection_name);
    }

    @Override
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotLatestVersion(String snapshotName) throws MongoException, UnknownHostException {
        return this.getEvidenceCountForSnapshotSpecificVersion(snapshotName, this.findHighestVersionForSnapshot(snapshotName));
    }

    @Override
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotSpecificVersion(String snapshotName, String version) throws MongoException, UnknownHostException {
        return CategoryItemDAOMongo.getInstance().getEvidenceCountForSnapshotSpecificVersion(snapshotName, version, this.collection_name);
    }

    @Override
    public List<IBook> getBookListForProfileInSnapshotLatestVersion(String profileId, String snapshotId) throws MongoException, UnknownHostException {
        return this.getBookListForProfileInSnapshotSpecificVersion(profileId, snapshotId, this.findHighestVersionForSnapshot(snapshotId));
    }

    @Override
    public List<IBook> getBookListForProfileInSnapshotSpecificVersion(String profileId, String snapshotId, String version) throws MongoException, UnknownHostException {

        BasicDBObject params = new BasicDBObject();
        params.append("snapshot", snapshotId);
        params.append("version", version);
        params.append("accountId", profileId);
        DBCursor dbc = this.findInCollection(this.collection_name, params);

        BasicDBList bdl = new BasicDBList();
        bdl.addAll(dbc.toArray());

        RootAccountJsonMongo racson = new RootAccountJsonMongo();
        racson.setBookList(bdl);

        return racson.getBookList();
    }

    @Override
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameBooksAsProfileForSnapshotSpecificVersion(String snapshotName, String version, String profile_id) throws MongoException, UnknownHostException {
        Map<ICategorizedDomain, List<NamedItem>> map = new HashMap<>();

        List<IBook> bookList = this.getBookListForProfileInSnapshotSpecificVersion(profile_id, snapshotName, version);

        List<NamedItem> friends;

        BasicDBObject params;

        for (IBook b : bookList) {
            params = new BasicDBObject();
            params.append("snapshot", snapshotName);
            params.append("version", version);
            params.append("data.id", b.getId());

            DBCursor dbc = this.findInCollection(this.collection_name, params);
            friends = new LinkedList<>();
            while (dbc.hasNext()) {

                String friend_fb_id = new MovieJsonMongo(dbc.next()).getAccountId();
                if (profile_id.equals(friend_fb_id)) {
                    continue;
                }
                friends.add(new NamedItem(friend_fb_id, ProfileDAOMongo.getInstance().getNameForAccountId(friend_fb_id)));
            }

            if (friends.size() > 0) {
                map.put(b, friends);
            }
        }

        return map;
    }

    @Override
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameBooksAsProfileForSnapshotLatestVersion(String snapshotName, String profile_id) throws MongoException, UnknownHostException {
        return this.getWhoLikesTheSameBooksAsProfileForSnapshotSpecificVersion(snapshotName, this.findHighestVersionForSnapshot(snapshotName), profile_id);
    }

    @Override
    public Map<String, Integer> getExtendedDistinctBookCategories() throws MongoException, UnknownHostException {
        return CategoryItemDAOMongo.getInstance().getExtendedDistinctCategories(this.collection_name);
    }
    
    @Override
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotLatestVersion(String snapshotName, long timeStart, long timeEnd) throws MongoException, UnknownHostException {
        return this.getEvidenceCountForSnapshotSpecificVersion(snapshotName, this.findHighestVersionForSnapshot(snapshotName), timeStart, timeEnd);
    }

    @Override
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotSpecificVersion(String snapshotName, String version, long timeStart, long timeEnd) throws MongoException, UnknownHostException {
        return CategoryItemDAOMongo.getInstance().getEvidenceCountForSnapshotSpecificVersion(snapshotName, version, this.collection_name, timeStart, timeEnd);
    }
}
