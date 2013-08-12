/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedLikeDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedCommentsDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedFromDAOSql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IStatusDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedPlaceDAOSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.status.IStatus;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.status.StatusSql;

/**
 *
 * @author Robert K
 */
public class StatusDAOSql extends AbstractSuperDAOSql implements IStatusDAO {

    private static StatusDAOSql instance = null;

    protected StatusDAOSql() throws SQLException {
        super();
        this.table_name = "status";
    }

    public static StatusDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new StatusDAOSql();
        }
        return instance;
    }

    public void saveStatus(IStatus toSave, long accountId) throws SQLException {
        if (toSave != null) {
            ArrayList<IStatus> al = new ArrayList<IStatus>(1);
            al.add(toSave);
            saveStatusList(al, accountId);
        }
    }

    public void saveStatusList(List<IStatus> toSave, long accountId) throws SQLException {

        if (toSave == null) {
            return;
        }

        PreparedStatement pst = this.prepareStatement("INSERT INTO " + this.table_name + " (id, account_id, fb_id, message, updated_time) VALUES (?, ?, ?, ?, ?)");
        pst.clearBatch();

        for (IStatus status : toSave) {

            long status_id = IdProvider.getInstance().getNextId();

            this.setLongOrNull(1, status_id, pst);
            this.setLongOrNull(2, accountId, pst);

            this.setStringOrNull(3, status.getId(), pst);
            this.setStringOrNull(4, status.getMessage(), pst);
            this.setLongOrNull(5, status.getUpdated_time(), pst);

            pst.addBatch();
            pst.clearParameters();

            // save comments
            SharedCommentsDAOSql.getInstance().saveSharedComments(status.getComments(), status_id, this.table_name);

            // save from
            SharedFromDAOSql.getInstance().saveSharedFrom(status.getFrom(), status_id, this.table_name);

            // save likes
            SharedLikeDAOSql.getInstance().saveSharedLike(status.getLikes(), status_id, this.table_name);

            // save place
            SharedPlaceDAOSql.getInstance().saveSharedPlace(status.getPlace(), status_id, this.table_name);
        }
        pst.executeBatch();

    }

    @Override
    public List<ISharedPlace> getLocationsForProfileSnapshotLatestVersion(String profile_id, String snapshot) throws SQLException {
        return this.getLocationsForProfileSnapshotSpecificVersion(profile_id, snapshot, this.getHighestVersion(snapshot));
    }

    @Override
    public List<ISharedPlace> getLocationsForProfileSnapshotSpecificVersion(String profile_id, String snapshot, String version) throws SQLException {

        PreparedStatement pst = this.prepareStatement("SELECT id FROM " + this.table_name + " WHERE account_id = ?");

        List<ISharedPlace> list = new LinkedList<>();

        Long accountId = this.getAccountDbId(profile_id, snapshot, version);
        if (accountId == null) {
            throw new SQLException("Profile with id " + profile_id + " was not found in snapshot " + snapshot + " version " + version);
        }

        this.setLongOrNull(1, accountId, pst);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {

            ISharedPlace place = SharedPlaceDAOSql.getInstance().getSharedPlace(rs.getLong("id"), this.table_name);
            if (place != null) {
                list.add(place);
            }
        }

        return list;
    }

    @Override
    public List<IStatus> getAllSatusInSnapshotLatestVersion(String snapshot) throws SQLException {
        return this.getAllSatusInSnapshotSpecificVersion(snapshot, this.getHighestVersion(snapshot));
    }

    @Override
    public List<IStatus> getAllSatusInSnapshotSpecificVersion(String snapshot, String version) throws SQLException {
        PreparedStatement pst = this.prepareStatement("SELECT id, fb_id, message, updated_time FROM " + this.table_name + " WHERE account_id IN (SELECT id FROM account WHERE snapshotinfo_id = (SELECT id FROM snapshotinfo WHERE snapshotName = ? AND version = ?))");
        this.setStringOrNull(1, snapshot, pst);
        this.setStringOrNull(2, version, pst);
        
        List<IStatus> statusList = new LinkedList<>();
        
        ResultSet rs = pst.executeQuery();
        
        while(rs.next()){
            Long status_id = rs.getLong("id");
            String fb_id = rs.getString("fb_id");
            String message = rs.getString("message");
            Long updated_time = rs.getLong("updated_time");
            
            ISharedPlace place = SharedPlaceDAOSql.getInstance().getSharedPlace(status_id, this.table_name);
            ISharedFrom from = SharedFromDAOSql.getInstance().getSharedFrom(status_id, this.table_name);
            ISharedLike like = SharedLikeDAOSql.getInstance().getSharedLike(status_id, this.table_name);
            ISharedComments comments = SharedCommentsDAOSql.getInstance().getComments(status_id, this.table_name);
            
            statusList.add(new StatusSql(fb_id, comments, from, like, message, updated_time, place));
        }
        
        return statusList;
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
