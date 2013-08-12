package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IBookDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.book.IBook;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.movie.IMovie;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ICategorizedDomain;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.book.BookSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.movie.MovieSql;

public class BookDAOSql extends AbstractSuperDAOSql implements IBookDAO {

    private static BookDAOSql instance = null;

    protected BookDAOSql() throws SQLException {
        super();
        this.table_name = "book";
    }

    public static BookDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new BookDAOSql();
        }
        return instance;
    }

    public void saveBookList(List<IBook> toSave, long accountId) throws SQLException {

        if (toSave == null) {
            return;
        }


        PreparedStatement pst = this.prepareStatement("INSERT INTO book (id, account_id, fb_id, fb_name, category, created_time) VALUES (?, ?, ?, ?, ?, ?)");
        pst.clearBatch();

        for (IBook book : toSave) {
            long bookId = IdProvider.getInstance().getNextId();

            this.setLongOrNull(1, bookId, pst);
            this.setLongOrNull(2, accountId, pst);
            this.setStringOrNull(3, book.getId(), pst);
            this.setStringOrNull(4, book.getName(), pst);
            this.setStringOrNull(5, book.getCategory(), pst);
            this.setLongOrNull(6, book.getCreated_time(), pst);

            pst.addBatch();
        }

        pst.executeBatch();


    }

    @Override
    public List<String> getDistinctBookCategories() throws Exception {
        return CategoryItemDAOSql.getInstance().getDistinctCategories(this.table_name);
    }

    @Override
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotLatestVersion(String snapshotName) throws SQLException {
        return this.getEvidenceCountForSnapshotSpecificVersion(snapshotName, this.getHighestVersion(snapshotName));
    }

    @Override
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotSpecificVersion(String snapshotName, String version) throws SQLException {
        return CategoryItemDAOSql.getInstance().getEvidenceCountForSnapshotAndVersion(snapshotName, version, this.table_name);
    }

    @Override
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameBooksAsProfileForSnapshotSpecificVersion(String snapshotName, String version, String profile_id) throws SQLException {

        List<IBook> profBook = this.getBookListForProfileInSnapshotSpecificVersion(profile_id, snapshotName, version);

        Map<ICategorizedDomain, List<NamedItem>> map = new HashMap<>();

        List<NamedItem> friends;

        Long accountDbId = this.getAccountDbId(profile_id, snapshotName, version);

        PreparedStatement pst = this.prepareStatement("SELECT account_id from " + this.table_name + " WHERE fb_id = ? AND account_id != ?");
        this.setLongOrNull(2, accountDbId, pst);
        ResultSet rs;

        for (IBook b : profBook) {
            this.setStringOrNull(1, b.getId(), pst);
            rs = pst.executeQuery();

            if (rs.next()) {

                friends = new LinkedList<>();
                do {
                    friends.add(ProfileDAOSql.getInstance().getNameItemForAccountId(rs.getLong("account_id")));
                } while (rs.next());

                map.put(b, friends);
            }
        }

        return map;
    }

    @Override
    public Map<ICategorizedDomain, List<NamedItem>> getWhoLikesTheSameBooksAsProfileForSnapshotLatestVersion(String snapshotName, String profile_id) throws Exception {
        return this.getWhoLikesTheSameBooksAsProfileForSnapshotSpecificVersion(snapshotName, this.getHighestVersion(snapshotName), profile_id);
    }

    @Override
    public List<IBook> getBookListForProfileInSnapshotLatestVersion(String profileId, String snapshotId) throws SQLException {
        return this.getBookListForProfileInSnapshotSpecificVersion(profileId, snapshotId, this.getHighestVersion(snapshotId));
    }

    @Override
    public List<IBook> getBookListForProfileInSnapshotSpecificVersion(String profileId, String snapshotId, String version) throws SQLException {
        List<IBook> returnList = new LinkedList<>();

        PreparedStatement pst = this.prepareStatement("SELECT fb_id, fb_name, category, created_time FROM " + this.table_name + " WHERE account_id = ?");
        this.setLongOrNull(1, this.getAccountDbId(profileId, snapshotId, version), pst);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            returnList.add(new BookSql(rs.getString("fb_id"), rs.getString("fb_name"), rs.getLong("created_time"), rs.getString("category")));
        }
        return returnList;
    }

    @Override
    public Map<String, Integer> getExtendedDistinctBookCategories() throws SQLException {
        return CategoryItemDAOSql.getInstance().getExtendedDistinctCategories(this.table_name);
    }

    @Override
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotLatestVersion(String snapshotName, long timeStart, long timeEnd) throws SQLException {
        return this.getEvidenceCountForSnapshotSpecificVersion(snapshotName, this.getHighestVersion(snapshotName), timeStart, timeEnd);
    }

    @Override
    public Map<CategoryItem, Integer> getEvidenceCountForSnapshotSpecificVersion(String snapshotName, String version, long timeStart, long timeEnd) throws SQLException {
        return CategoryItemDAOSql.getInstance().getEvidenceCountForSnapshotAndVersion(snapshotName, version, table_name, timeStart, timeEnd);
    }
}
