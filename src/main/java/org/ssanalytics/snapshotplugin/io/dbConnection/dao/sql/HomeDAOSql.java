package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedActionDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedCommentsDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedFromDAOSql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IHomeDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedPrivacyDAOSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.home.IHome;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPrivacy;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.home.HomeSql;

public class HomeDAOSql extends AbstractSuperDAOSql implements IHomeDAO{

    private static HomeDAOSql instance = null;

    protected HomeDAOSql() throws SQLException {
        super();
        this.table_name = "home";
    }

    public static HomeDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new HomeDAOSql();
        }
        return instance;
    }

    public void saveHomeList(List<IHome> toSave, long account_id) throws SQLException {


        if (toSave == null) {
            return;
        }
        
        PreparedStatement pst = this.prepareStatement("INSERT INTO home (id, account_id, fb_id, created_time, message, fb_type, updated_time) VALUES(?, ?, ?, ?, ?, ?, ?)");
        pst.clearBatch();

        for (IHome home : toSave) {

            long home_id = IdProvider.getInstance().getNextId();

            this.setLongOrNull(1, home_id, pst);
            this.setLongOrNull(2, account_id, pst);
            this.setStringOrNull(3, home.getId(), pst);

            this.setLongOrNull(4, home.getCreated_time(), pst);

            this.setStringOrNull(5, home.getMessage(), pst);
            this.setStringOrNull(6, home.getType(), pst);

            this.setLongOrNull(7, home.getUpdated_time(), pst);

            pst.addBatch();

            // save actions
            SharedActionDAOSql.getInstance().saveSharedAction(
                    home.getActionList(), home_id, this.table_name);

            // save comments
            SharedCommentsDAOSql.getInstance().saveSharedComments(
                    home.getComments(), home_id, this.table_name);

            // save from
            SharedFromDAOSql.getInstance().saveSharedFrom(home.getFrom(),
                    home_id, this.table_name);
            
            // save privacy
            SharedPrivacyDAOSql.getInstance().saveSharedPrivacy(home.getPricacy(), home_id, this.table_name);

        }
        
        pst.executeBatch();
    }

    @Override
    public List<IHome> getHomeListForRootAccountOfSnapshotLatestVersion(String snapshotName) throws SQLException {
        return this.getHomeListForRootAccountOfSnapshotSpecificVersion(snapshotName, this.getHighestVersion(snapshotName));
    }

    @Override
    public List<IHome> getHomeListForRootAccountOfSnapshotSpecificVersion(String snapshotName, String version) throws SQLException {
        
        Long racDbId = this.getRootAccountDbId(snapshotName, version);
        
        if(racDbId == null){
            throw new SQLException("Root account for snapshot: " + snapshotName + " and version: " + version + " not found!");
        }
        
        PreparedStatement pst = this.prepareStatement("SELECT id, fb_id, created_time, message, fb_type, updated_time FROM " + this.table_name + " WHERE account_id = ?");
        this.setLongOrNull(1, racDbId, pst);
        
        List<IHome> homeList = new LinkedList<>();
        
        ResultSet rs = pst.executeQuery();
        
        while(rs.next()){
            Long homeId = rs.getLong("id");
            String fb_id = rs.getString("fb_id");
            Long created_time = rs.getLong("created_time");
            Long updated_time = rs.getLong("updated_time");
            String message = rs.getString("message");
            String fb_type = rs.getString("fb_type");
            
            List<ISharedAction> actionList = SharedActionDAOSql.getInstance().getSharedActionList(homeId, this.table_name);
            ISharedComments comments = SharedCommentsDAOSql.getInstance().getComments(homeId, this.table_name);
            ISharedFrom from = SharedFromDAOSql.getInstance().getSharedFrom(homeId, this.table_name);
            ISharedPrivacy prv = SharedPrivacyDAOSql.getInstance().getSharedPrivacy(homeId, this.table_name);
            
            homeList.add(new HomeSql(fb_id, actionList, comments, created_time, from, message, fb_type, updated_time, prv));
        }
        
        return homeList;        
    }

    @Override
    public List<ISharedComments> getAllCommentsInSnapshotLatestVersion(String snapshot) throws Exception {
        return this.getAllCommentsInSnapshotSpecificVersion(snapshot, this.getHighestVersion(snapshot));
    }

    @Override
    public List<ISharedComments> getAllCommentsInSnapshotSpecificVersion(String snapshot, String version) throws Exception {
        return CommentDAOSql.getInstance().getCommentsForTableSnapshotSpecificVersion(this.table_name, snapshot, version);
    }
}
