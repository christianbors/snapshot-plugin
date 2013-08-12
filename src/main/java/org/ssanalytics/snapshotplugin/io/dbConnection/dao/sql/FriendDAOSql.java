package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IFriendDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.filter.FriendFilterData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.friend.FriendSql;

public class FriendDAOSql extends AbstractSuperDAOSql implements IFriendDAO {

    private static FriendDAOSql instance = null;

    protected FriendDAOSql() throws SQLException {
        super();
        this.table_name = "friends";
    }

    public static FriendDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new FriendDAOSql();
        }
        return instance;
    }

    public void saveFriendList(long account_id, List<IFriend> friendList) throws SQLException {

        PreparedStatement pst = this.prepareStatement("INSERT INTO " + this.table_name + " (id, account_id, fb_id, fb_name) VALUES (?, ?, ?, ?)");
        pst.clearBatch();

        this.setLongOrNull(2, account_id, pst);

        for (IFriend friend : friendList) {
            this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);

            this.setStringOrNull(3, friend.getId(), pst);
            this.setStringOrNull(4, friend.getName(), pst);

            pst.addBatch();
        }

        pst.executeBatch();
    }

    @Override
    public List<IFriend> getFriendListSpecificVersion(String snapshotName, String version) throws SQLException {

        List<IFriend> friendList = new LinkedList<>();

        PreparedStatement pst = this.prepareStatement("SELECT fb_id, fb_name FROM " + this.table_name + " WHERE account_id = (SELECT root_db_id FROM snapshotinfo WHERE snapshotname = ? AND version = ?)");

        this.setStringOrNull(1, snapshotName, pst);
        this.setStringOrNull(2, version, pst);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {

            String fb_id = rs.getString(1);
            String fb_name = rs.getString(2);

            friendList.add(new FriendSql(fb_id, fb_name));
        }

        return friendList;
    }

    @Override
    public List<IFriend> getFriendList(String snapshotName, String version, FriendFilterData filter) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IFriend> getFriendListLatestVersion(String snapshotName) throws Exception {
        return this.getFriendListSpecificVersion(snapshotName, this.getHighestVersion(snapshotName));
    }

    @Override
    public List<String> getFriendIdListSpecificVersion(String snapshotName, String version) throws SQLException {
        List<String> retList = new LinkedList<>();

        for (IFriend f : this.getFriendListSpecificVersion(snapshotName, version)) {
            retList.add(f.getId());
        }

        return retList;
    }

    @Override
    public List<String> getFriendIdListLatestVersion(String snapshotName) throws SQLException {
        return this.getFriendIdListSpecificVersion(snapshotName, this.getHighestVersion(snapshotName));
    }
}
