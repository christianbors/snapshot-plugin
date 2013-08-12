package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.AbstractSuperDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.IdProvider;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared.SharedFromSql;

public class SharedFromDAOSql extends AbstractSuperDAOSql {

    private static SharedFromDAOSql instance = null;

    protected SharedFromDAOSql() throws SQLException {
        super();
    }

    public static SharedFromDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new SharedFromDAOSql();
        }
        return instance;
    }

    public void saveSharedFrom(ISharedFrom from, long papaId, String papaName) throws SQLException {

        if (from != null) {

            PreparedStatement pst = this.prepareStatement("INSERT INTO shared_from (id, papa_id, papa_name, fb_id, fb_name) VALUES (?, ?, ?, ?, ?)");
            this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
            this.setLongOrNull(2, papaId, pst);
            this.setStringOrNull(3, papaName, pst);
            this.setStringOrNull(4, from.getId(), pst);
            this.setStringOrNull(5, from.getName(), pst);

            pst.execute();
        }
    }

    public ISharedFrom getSharedFrom(long papaId, String papaName) throws SQLException {

        PreparedStatement pst = this.prepareStatement("SELECT fb_id, fb_name FROM shared_from WHERE papa_id = ? AND papa_name = ?");
        this.setLongOrNull(1, papaId, pst);
        this.setStringOrNull(2, papaName, pst);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return new SharedFromSql(rs.getString("fb_id"), rs.getString("fb_name"));
        }
        
        return null;
    }
}
