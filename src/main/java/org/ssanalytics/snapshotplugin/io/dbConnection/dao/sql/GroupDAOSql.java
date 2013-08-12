package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IGroupDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.ProfileDAOMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.group.IGroup;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account.RootAccountJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.group.GroupJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.group.GroupSql;

public class GroupDAOSql extends AbstractSuperDAOSql implements IGroupDAO {

    private static GroupDAOSql instance = null;

    protected GroupDAOSql() throws SQLException {
        super();
        this.table_name = "fb_group";
    }

    public static GroupDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new GroupDAOSql();
        }
        return instance;
    }

    public void saveGroupList(List<IGroup> toSave, long account_id) throws SQLException {

        if (toSave == null) {
            return;
        }



        PreparedStatement pst = this.prepareStatement("INSERT INTO fb_group (id, account_id, fb_id, fb_name, version) VALUES(?, ?, ?, ?, ?)");
        pst.clearBatch();

        for (IGroup group : toSave) {

            long group_id = IdProvider.getInstance().getNextId();
            this.setLongOrNull(1, group_id, pst);
            this.setLongOrNull(2, account_id, pst);
            this.setStringOrNull(3, group.getId(), pst);
            this.setStringOrNull(4, group.getName(), pst);
            this.setLongOrNull(5, group.getVersion(), pst);
            pst.addBatch();
        }

        pst.executeBatch();

    }

    @Override
    public Map<IGroup, List<NamedItem>> getWhoIsInTheSameGroup(String snapshotName, String profileId) throws Exception {
        Map<IGroup, List<NamedItem>> map = new HashMap<>();

        PreparedStatement pst = this.prepareStatement("SELECT fb_id, fb_name, version FROM " + this.table_name + " WHERE account_id IN (SELECT id FROM account WHERE fb_id = ? AND snapshotinfo_id IN (SELECT id FROM snapshotinfo WHERE snapshotname = ?))");
        if (profileId == null || profileId.equals("")) {
            return map;
        }

        this.setStringOrNull(1, profileId, pst);
        this.setStringOrNull(2, snapshotName, pst);

        ResultSet rs = pst.executeQuery();
        List<NamedItem> items;

        while (rs.next()) {
            GroupSql g = new GroupSql(rs.getString("fb_id"), rs.getString("fb_name"), rs.getLong("version"));
            g.setAccountId(profileId);
            g.setSnapshotId(snapshotName);

            PreparedStatement pstInner = this.prepareStatement("SELECT account_id, fb_id, fb_name FROM " + this.table_name + " WHERE fb_id = ? AND account_id IN (SELECT id FROM account WHERE fb_id != ? AND snapshotinfo_id IN (SELECT id FROM snapshotinfo WHERE snapshotname = ?))");
            pstInner.setString(1, g.getId());
            pstInner.setString(2, profileId);
            pstInner.setString(3, snapshotName);

            ResultSet rsInner = pstInner.executeQuery();
            if (rsInner.next()) {
                items = new LinkedList<>();
                items.add(ProfileDAOSql.getInstance().getNameItemForAccountId(rsInner.getLong("account_id")));
                while (rsInner.next()) {
                    items.add(ProfileDAOSql.getInstance().getNameItemForAccountId(rsInner.getLong("account_id")));
                }
                map.put(g, items);
            }
        }

        return map;
    }
}
