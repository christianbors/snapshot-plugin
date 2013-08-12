package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.ISnapshotInfoDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.snapshotInfo.SnapshotInfoSql;

public class SnapshotInfoDAOSql extends AbstractSuperDAOSql implements ISnapshotInfoDAO {

    protected SnapshotInfoDAOSql() throws SQLException {
        super();
        this.table_name = "snapshotinfo";
    }
    private static SnapshotInfoDAOSql instance = null;

    public static SnapshotInfoDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new SnapshotInfoDAOSql();
        }
        return instance;
    }

    @Override
    public List<ISnapshotInfo> getSnapshotInfoList() throws Exception {
        PreparedStatement pst = this.prepareStatement("SELECT id , snapshotname , timestamp , root , version FROM " + this.table_name);
        ResultSet shots = pst.executeQuery();

        List<ISnapshotInfo> returnList = new ArrayList<ISnapshotInfo>();

        while (shots.next()) {

            long id = shots.getLong(1);
            String snapshotname = shots.getString(2);
            long timestamp = shots.getLong(3);
            String root = shots.getString(4);
            String version = shots.getString(5);

            pst = this.prepareStatement("SELECT friend_fb_id FROM snapshotinfo_friends WHERE snapshotinfo_id = ?");
            this.setLongOrNull(1, id, pst);

            ResultSet friends = pst.executeQuery();

            List<String> friendList = new ArrayList<String>();
            while (friends.next()) {
                friendList.add(friends.getString(1));
            }

            returnList.add(new SnapshotInfoSql(snapshotname, timestamp, root, version, friendList));
        }
        return returnList;
    }

    public void updateSnapshotInfo(long snapshotDbId, String rootId, List<IFriend> friends, long rootAccountDbId) throws SQLException {

        PreparedStatement pst = this.prepareStatement("UPDATE " + this.table_name + " SET root = ? WHERE id = ?");
        this.setStringOrNull(1, rootId, pst);
        this.setLongOrNull(2, snapshotDbId, pst);

        pst.execute();

        pst = this.prepareStatement("UPDATE " + this.table_name + " SET root_db_id = ? WHERE id = ?");
        this.setLongOrNull(1, rootAccountDbId, pst);
        this.setLongOrNull(2, snapshotDbId, pst);

        pst.execute();

        long friendDbId;
        for (IFriend friend : friends) {
            friendDbId = IdProvider.getInstance().getNextId();
            pst = this.prepareStatement("INSERT INTO snapshotinfo_friends (id, snapshotinfo_id, friend_fb_id) VALUES (?, ?, ?)");
            this.setLongOrNull(1, friendDbId, pst);
            this.setLongOrNull(2, snapshotDbId, pst);
            this.setStringOrNull(3, friend.getId(), pst);
            pst.execute();
        }


    }

    public long createSnapshotInfo(String snapshotname, long timestamp, String version) throws SQLException {


        long snapshotDbId = IdProvider.getInstance().getNextId();

        PreparedStatement pst = this.prepareStatement("INSERT INTO " + this.table_name + " (id, snapshotname, timestamp, version) VALUES (?, ?, ?, ?)");

        this.setLongOrNull(1, snapshotDbId, pst);
        this.setStringOrNull(2, snapshotname, pst);
        this.setLongOrNull(3, timestamp, pst);
        this.setStringOrNull(4, version, pst);

        pst.execute();

        return snapshotDbId;
    }

    public long getNextVersionNumber(String snapshotname) throws SQLException {

        // SECTION: Determine version number of snapshotName

        PreparedStatement pst = this.prepareStatement("SELECT version FROM " + this.table_name + " where snapshotname = ?");
        this.setStringOrNull(1, snapshotname, pst);
        ResultSet rs = pst.executeQuery();

        int highestVersion = 0;
        while (rs.next()) {
            String version = rs.getString("version");
            if (version != null) {
                int tempVersion = Integer.parseInt(version);
                if (tempVersion > highestVersion) {
                    highestVersion = tempVersion;
                }
            }
        }

        return highestVersion + 1;
    }

    @Override
    public String findHighestVersion(String snapshotinfo) throws Exception {
        return this.getHighestVersion(snapshotinfo);
    }

    @Override
    public String getRootAccountFbId(String snapshotinfo, String version) throws SQLException {
        PreparedStatement pst = this.prepareStatement("SELECT root FROM snapshotinfo WHERE snapshotname = ? AND version = ?");
        this.setStringOrNull(1, snapshotinfo, pst);
        this.setStringOrNull(2, version, pst);
        
        ResultSet rs = pst.executeQuery();
        if(rs.next())
            return rs.getString("root");
        else
            return null;
    }

    @Override
    public Date getFirstDateForEvidenceDomainsInSnapshotLatestVersion(String snapshotinfo) throws Exception {
        return this.getFirstDateForEvidenceDomainsInSnapshotSpecificVersion(snapshotinfo, this.getHighestVersion(snapshotinfo));
    }

    @Override
    public Date getFirstDateForEvidenceDomainsInSnapshotSpecificVersion(String snapshotinfo, String version) throws Exception {

        Long minDate = System.currentTimeMillis();
        Long temp = null;
        
        //check activity
        temp = CategoryItemDAOSql.getInstance().getFirstDate(snapshotinfo, version, "activity");
        if(temp != null)
            if(temp < minDate)
                minDate = temp;
        
        //check interest
        temp = CategoryItemDAOSql.getInstance().getFirstDate(snapshotinfo, version, "interest");
        if(temp != null)
            if(temp < minDate)
                minDate = temp;
        
        //check book
        temp = CategoryItemDAOSql.getInstance().getFirstDate(snapshotinfo, version, "book");
        if(temp != null)
            if(temp < minDate)
                minDate = temp;
        
        //check likes
        temp = CategoryItemDAOSql.getInstance().getFirstDate(snapshotinfo, version, "fb_like");
        if(temp != null)
            if(temp < minDate)
                minDate = temp;
        
        //check movie
        temp = CategoryItemDAOSql.getInstance().getFirstDate(snapshotinfo, version, "movie");
        if(temp != null)
            if(temp < minDate)
                minDate = temp;
        
        //check music
        temp = CategoryItemDAOSql.getInstance().getFirstDate(snapshotinfo, version, "music");
        if(temp != null)
            if(temp < minDate)
                minDate = temp;
        
        //check television
        temp = CategoryItemDAOSql.getInstance().getFirstDate(snapshotinfo, version, "television");
        if(temp != null)
            if(temp < minDate)
                minDate = temp;
        
        
        return new Date(minDate);        
    }

    @Override
    public Date getLastDateForEvidenceDomainsInSnapshotLatestVersion(String snapshotinfo) throws Exception {
        return this.getLastDateForEvidenceDomainsInSnapshotSpecificVersion(snapshotinfo, this.getHighestVersion(snapshotinfo));
    }

    @Override
    public Date getLastDateForEvidenceDomainsInSnapshotSpecificVersion(String snapshotinfo, String version) throws Exception {
        Long maxDate = 0l;
        Long temp = null;
        
        //check activity
        temp = CategoryItemDAOSql.getInstance().getFirstDate(snapshotinfo, version, "activity");
        if(temp != null)
            if(temp > maxDate)
                maxDate = temp;
        
        //check interest
        temp = CategoryItemDAOSql.getInstance().getFirstDate(snapshotinfo, version, "interest");
        if(temp != null)
            if(temp > maxDate)
                maxDate = temp;
        
        //check book
        temp = CategoryItemDAOSql.getInstance().getFirstDate(snapshotinfo, version, "book");
        if(temp != null)
            if(temp > maxDate)
                maxDate = temp;
        
        //check likes
        temp = CategoryItemDAOSql.getInstance().getFirstDate(snapshotinfo, version, "fb_like");
        if(temp != null)
            if(temp > maxDate)
                maxDate = temp;
        
        //check movie
        temp = CategoryItemDAOSql.getInstance().getFirstDate(snapshotinfo, version, "movie");
        if(temp != null)
            if(temp > maxDate)
                maxDate = temp;
        
        //check music
        temp = CategoryItemDAOSql.getInstance().getFirstDate(snapshotinfo, version, "music");
        if(temp != null)
            if(temp > maxDate)
                maxDate = temp;
        
        //check television
        temp = CategoryItemDAOSql.getInstance().getFirstDate(snapshotinfo, version, "television");
        if(temp != null)
            if(temp > maxDate)
                maxDate = temp;
        
        
        return new Date(maxDate);   
    }
}
