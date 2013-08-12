package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.AbstractSuperDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.IdProvider;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLikeData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared.SharedLikeDataSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared.SharedLikeSql;

public class SharedLikeDAOSql extends AbstractSuperDAOSql {

    private static SharedLikeDAOSql instance = null;

    protected SharedLikeDAOSql() throws SQLException {
        super();
    }

    public static SharedLikeDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new SharedLikeDAOSql();
        }
        return instance;
    }
    
    public void saveSharedLikeWihtoutBatch(ISharedLike likes, long papaId, String papaName) throws SQLException {

        if (likes != null) {
            PreparedStatement pst = this.prepareStatement("INSERT INTO shared_like (id, papa_id, papa_name, like_count) VALUES (?, ?, ?, ?)");
            long like_id = IdProvider.getInstance().getNextId();
            this.setLongOrNull(1, like_id, pst);
            this.setLongOrNull(2, papaId, pst);
            this.setStringOrNull(3, papaName, pst);

            setIntegerOrNull(4, likes.getCount(), pst);

            pst.execute();

            List<ISharedLikeData> likeDataList = likes.getDataList();

            if (likeDataList != null) {
                pst = this.prepareStatement("INSERT INTO shared_like_data (id, like_id, fb_id, name) VALUES (?, ?, ?, ?)");
                this.setLongOrNull(2, like_id, pst);
                for (ISharedLikeData likeData : likeDataList) {
                    this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
                    this.setStringOrNull(3, likeData.getId(), pst);
                    this.setStringOrNull(4, likeData.getName(), pst);
                    pst.execute();
                }
            }
        }
    }

    public void saveSharedLike(ISharedLike likes, long papaId, String papaName) throws SQLException {

        if (likes != null) {
            PreparedStatement pst = this.prepareStatement("INSERT INTO shared_like (id, papa_id, papa_name, like_count) VALUES (?, ?, ?, ?)");
            long like_id = IdProvider.getInstance().getNextId();
            this.setLongOrNull(1, like_id, pst);
            this.setLongOrNull(2, papaId, pst);
            this.setStringOrNull(3, papaName, pst);

            setIntegerOrNull(4, likes.getCount(), pst);

            pst.execute();

            List<ISharedLikeData> likeDataList = likes.getDataList();

            if (likeDataList != null) {
                pst = this.prepareStatement("INSERT INTO shared_like_data (id, like_id, fb_id, name) VALUES (?, ?, ?, ?)");
                pst.clearBatch();
                this.setLongOrNull(2, like_id, pst);
                for (ISharedLikeData likeData : likeDataList) {
                    this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
                    this.setStringOrNull(3, likeData.getId(), pst);
                    this.setStringOrNull(4, likeData.getName(), pst);
                    pst.addBatch();
                }

                pst.executeBatch();
            }
        }
    }

    public ISharedLike getSharedLike(long papaId, String papaName) throws SQLException {
        PreparedStatement pstSharedLike = this.prepareStatement("SELECT id, like_count FROM shared_like WHERE papa_id = ? AND papa_name = ?");
        this.setLongOrNull(1, papaId, pstSharedLike);
        this.setStringOrNull(2, papaName, pstSharedLike);

        ResultSet rsSharedLike = pstSharedLike.executeQuery();

        if (rsSharedLike.next()) {

            PreparedStatement pstSharedLikeData = this.prepareStatement("SELECT fb_id, name FROM shared_like_data WHERE like_id = ?");
            this.setLongOrNull(1, rsSharedLike.getLong("id"), pstSharedLikeData);

            ResultSet rsSharedLikeData = pstSharedLikeData.executeQuery();

            List<ISharedLikeData> dataList = new LinkedList<>();
            while (rsSharedLikeData.next()) {
                dataList.add(new SharedLikeDataSql(rsSharedLikeData.getString("name"), rsSharedLikeData.getString("fb_id")));
            }

           return new SharedLikeSql(rsSharedLike.getInt("like_count"), dataList, null);
        }

        return null;      
    }
}
