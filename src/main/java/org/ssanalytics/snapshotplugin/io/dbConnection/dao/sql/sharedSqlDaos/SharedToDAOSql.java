package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.AbstractSuperDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.IdProvider;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedToData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared.SharedToDataSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared.SharedToSql;

public class SharedToDAOSql extends AbstractSuperDAOSql {

    private static SharedToDAOSql instance = null;

    protected SharedToDAOSql() throws SQLException {
        super();
    }

    public static SharedToDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new SharedToDAOSql();
        }
        return instance;
    }
    
    public ISharedTo getSharedTo(long papaId, String papaName) throws SQLException {
        PreparedStatement pst = this.prepareStatement("SELECT id, fb_id, fb_name FROM shared_to WHERE papa_id = ? AND papa_name = ?");
        this.setLongOrNull(1, papaId, pst);
        this.setStringOrNull(2, papaName, pst);
        
        ResultSet rs = pst.executeQuery();
        
        List<ISharedToData> dataList = new LinkedList<>();
        
        while(rs.next()){
            
            long to_id = rs.getLong("id");
            String fb_id = rs.getString("fb_id");
            String fb_name = rs.getString("fb_name");
            
            ISharedToData toData = new SharedToDataSql(fb_name, fb_id);
            
            dataList.add(toData);
        }
        
        return new SharedToSql(dataList);
    }

    public void saveSharedTo(ISharedTo to, long papaId, String papaName) throws SQLException {

        if (to != null) {

            List<ISharedToData> dataList = to.getToDataList();

            if (dataList != null) {
                PreparedStatement pst = this.prepareStatement("INSERT INTO shared_to (id, papa_id, papa_name, fb_id, fb_name) VALUES (?, ?, ?, ?, ?)");
                pst.clearBatch();
                this.setLongOrNull(2, papaId, pst);
                this.setStringOrNull(3, papaName, pst);
                for (ISharedToData data : dataList) {

                    if (data != null) {
                        this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
                        this.setStringOrNull(4, data.getId(), pst);
                        this.setStringOrNull(5, data.getName(), pst);

                        pst.addBatch();
                    }
                }
                pst.executeBatch();
            }
        }
    }
}
