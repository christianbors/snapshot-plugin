package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IRootAccountDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.account.IRootAccount;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.friend.FriendSql;

public class RootAccountDAOSql extends AccountDAOSql implements IRootAccountDAO {

    private static RootAccountDAOSql instance = null;

    protected RootAccountDAOSql() throws SQLException {
        super();
    }

    public static RootAccountDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new RootAccountDAOSql();
        }
        return instance;
    }

    @Override
    public Map<String, List<String>> getFriendIsFriend(String snapshotName, String version) throws Exception {


        PreparedStatement pst = this.prepareStatement("SELECT id, friend_fb_id FROM friendisfriend WHERE snapshotinfo_id = (SELECT id FROM snapshotinfo WHERE snapshotname = ? AND version = ?)");
        this.setStringOrNull(1, snapshotName, pst);
        this.setStringOrNull(2, version, pst);

        ResultSet friendisfriend = pst.executeQuery();

        Map<String, List<String>> returnMap = new HashMap<>();

        while (friendisfriend.next()) {

            long friendisfriend_id = friendisfriend.getLong("id");
            String friendisfriend_fb_id = friendisfriend.getString("friend_fb_id");

            pst = this.prepareStatement("SELECT friend_fb_id FROM friendisfriend_friend WHERE friendisfriend_id = ?");
            this.setLongOrNull(1, friendisfriend_id, pst);

            ResultSet friendisfriend_friend = pst.executeQuery();

            List<String> friendisfriend_friendList = new ArrayList<>();

            while (friendisfriend_friend.next()) {
                friendisfriend_friendList.add(friendisfriend_friend.getString("friend_fb_id"));
            }

            returnMap.put(friendisfriend_fb_id, friendisfriend_friendList);
        }

        return returnMap;
    }

    @Override
    public List<IFriend> getFriendList(String snapshotName, String version) throws Exception {
        List<IFriend> returnList = new ArrayList<IFriend>();

        PreparedStatement pst = this.prepareStatement("SELECT id, fb_id, fb_name FROM friends WHERE snapshotinfo_id = (SELECT id FROM snapshotinfo WHERE snapshotname = ? AND version = ?)");
        this.setStringOrNull(1, snapshotName, pst);
        this.setStringOrNull(2, version, pst);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            returnList.add(new FriendSql(rs.getString("fb_id"), rs.getString("fb_name")));
        }

        return returnList;
    }

    public long saveRootAccount(long snapshotInfoDbId, IRootAccount rac) throws SQLException {

        long accountId = this.saveAccount(snapshotInfoDbId, rac);

        this.saveFriendIsFriend(snapshotInfoDbId, rac.getFriendIsFriend());

        long start = System.currentTimeMillis();
        //save feeds
        FeedDAOSql.getInstance().saveFeedList(rac.getFeedList(), accountId);
        long end = System.currentTimeMillis();
        System.out.println("Saved the feeds in " + (end - start) + " ms");


        start = System.currentTimeMillis();

        //save friends
        FriendDAOSql.getInstance().saveFriendList(accountId, rac.getFriendList());
        end = System.currentTimeMillis();
        System.out.println("Saved the friends in " + (end - start) + " ms");


        start = System.currentTimeMillis();

        //save inbox
        InboxDAOSql.getInstance().saveInboxList(rac.getInboxList(), accountId);
        end = System.currentTimeMillis();
        System.out.println("Saved the inbox in " + (end - start) + " ms");


        start = System.currentTimeMillis();

        //save outbox
        OutboxDAOSql.getInstance().saveOutboxList(rac.getOutboxList(), accountId);
        end = System.currentTimeMillis();
        System.out.println("Saved the outobx in " + (end - start) + " ms");


        return accountId;


    }

    private void saveFriendIsFriend(long snapshotInfoDbId, Map<String, List<String>> friendIsFriendMap) throws SQLException {

        for (String key : friendIsFriendMap.keySet()) {
            PreparedStatement pst = this.prepareStatement("INSERT INTO friendisfriend (id, snapshotinfo_id, friend_fb_id) VALUES (?, ?, ?)");

            long nextId = IdProvider.getInstance().getNextId();
            this.setLongOrNull(1, nextId, pst);
            this.setLongOrNull(2, snapshotInfoDbId, pst);
            this.setStringOrNull(3, key, pst);

            pst.execute();

            for (String s : friendIsFriendMap.get(key)) {

                pst = this.prepareStatement("INSERT INTO friendisfriend_friend (id, friendisfriend_id, friend_fb_id) VALUES (?, ?, ?)");

                this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
                this.setLongOrNull(2, nextId, pst);
                this.setStringOrNull(3, s, pst);

                pst.execute();
            }
        }
    }
}
