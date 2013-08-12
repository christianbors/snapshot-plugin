package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.AbstractSuperDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.IdProvider;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPrivacy;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared.SharedActionSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared.SharedPrivacySql;

public class SharedPrivacyDAOSql extends AbstractSuperDAOSql {

    private static SharedPrivacyDAOSql instance = null;

    protected SharedPrivacyDAOSql() throws SQLException {
        super();
    }

    public static SharedPrivacyDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new SharedPrivacyDAOSql();
        }
        return instance;
    }

    public ISharedPrivacy getSharedPrivacy(long papaId, String papaName) throws SQLException {


        PreparedStatement pst = this.prepareStatement("SELECT description, prv_value FROM shared_privacy WHERE papa_id = ? AND papa_name = ?");
        this.setLongOrNull(1, papaId, pst);
        this.setStringOrNull(2, papaName, pst);

        ResultSet rs = pst.executeQuery();


        if (rs.next()) {
            return new SharedPrivacySql(rs.getString("description"), rs.getString("prv_value"));
        }

        return null;
    }

    public void saveSharedPrivacy(ISharedPrivacy privacy, long papaId, String papaName) throws SQLException {

        if (privacy != null) {
            PreparedStatement pst = this.prepareStatement("INSERT INTO shared_privacy (id, papa_id, papa_name, description, prv_value) VALUES (?, ?, ?, ?, ?)");
            this.setLongOrNull(2, papaId, pst);
            this.setStringOrNull(3, papaName, pst);

            this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
            this.setStringOrNull(4, privacy.getDescription(), pst);
            this.setStringOrNull(5, privacy.getValue(), pst);

            pst.execute();
        }
    }
}
