package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.AbstractSuperDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.IdProvider;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedCommentData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPaging;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared.SharedCommentDataSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared.SharedCommentSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared.SharedPagingSql;

public class SharedCommentsDAOSql extends AbstractSuperDAOSql {

    private static SharedCommentsDAOSql instance = null;

    protected SharedCommentsDAOSql() throws SQLException {
        super();
    }

    public static SharedCommentsDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new SharedCommentsDAOSql();
        }
        return instance;
    }
    
    
    public ISharedComments getComments(long papaId, String papaName) throws SQLException {
        
        PreparedStatement pst = this.prepareStatement("SELECT id, comments_count FROM shared_comments WHERE papa_id = ? AND papa_name = ?");
        this.setLongOrNull(1, papaId, pst);
        this.setStringOrNull(2, papaName, pst);
        
        ResultSet rs = pst.executeQuery();
        
        if(rs.next()){
            Integer comments_count = rs.getInt("comments_count");
            Long comments_id = rs.getLong("id");
            
            pst = this.prepareStatement("SELECT id, message, created_time, fb_id, likes FROM shared_comment_data WHERE comment_id = ?");
            this.setLongOrNull(1, comments_id, pst);
            
            rs = pst.executeQuery();
            
            List<ISharedCommentData> commentDataList = new LinkedList<>();
            
            while(rs.next()){                
                commentDataList.add(new SharedCommentDataSql(rs.getString("fb_id"), rs.getLong("created_time"), rs.getString("message"), SharedFromDAOSql.getInstance().getSharedFrom(rs.getLong("id"), "shared_comment_data"), rs.getInt("likes")));                
            }
            
            pst = this.prepareStatement("SELECT paging_next, paging_previous FROM shared_paging WHERE papa_id = ? AND papa_name = ?");
            this.setLongOrNull(1, comments_id, pst);
            this.setStringOrNull(2, "shared_comments", pst);
            
            rs = pst.executeQuery();
            
            ISharedPaging paging = null;
            
            if(rs.next()){
                paging = new SharedPagingSql(rs.getString("paging_next"), rs.getString("paging_previous"));
            }
            
            return new SharedCommentSql(paging, commentDataList, comments_count);
        }
        
        return null;
        
        
    }
    public void saveSharedCommentsWithoutBatch(ISharedComments comments, long papaId, String papaName) throws SQLException {

        if (comments != null) {
            PreparedStatement pst = this.prepareStatement("INSERT INTO shared_comments (id, papa_id, papa_name, comments_count) VALUES (?, ?, ?, ?)");
            long comments_id = IdProvider.getInstance().getNextId();
            this.setLongOrNull(1, comments_id, pst);
            this.setLongOrNull(2, papaId, pst);
            this.setStringOrNull(3, papaName, pst);

            setIntegerOrNull(4, comments.getCount(), pst);
            
            pst.execute();

            List<ISharedCommentData> commentDataList = comments.getDataList();

            if (commentDataList != null) {
                pst = this.prepareStatement("INSERT INTO shared_comment_data (id, comment_id, message, fb_id, created_time, likes) VALUES (?, ?, ?, ?, ?, ?)");
                this.setLongOrNull(2, comments_id, pst);
                for (ISharedCommentData commentData : commentDataList) {
                    long commentData_id = IdProvider.getInstance().getNextId();
                    this.setLongOrNull(1, commentData_id, pst);
                    this.setStringOrNull(3, commentData.getMessage(), pst);
                    this.setStringOrNull(4, commentData.getId(), pst);
                    this.setLongOrNull(5, commentData.getCreatedTime(), pst);
                    this.setIntegerOrNull(6, commentData.getLikes(), pst);
                    
                    SharedFromDAOSql.getInstance().saveSharedFrom(commentData.getFrom(), commentData_id, "shared_comment_data");

                    pst.execute();
                }

            }

            ISharedPaging paging = comments.getPaging();

            if (paging != null) {

                pst = this.prepareStatement("INSERT INTO shared_paging (id, papa_id, papa_name, paging_next, paging_previous) VALUES (?, ?, ?, ?, ?)");

                this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
                this.setLongOrNull(2, comments_id, pst);
                this.setStringOrNull(3, "shared_comments", pst);

                this.setStringOrNull(4, paging.getNext(), pst);

                this.setStringOrNull(5, paging.getPrevious(), pst);

                pst.execute();
            }
        }
    }
    

    public void saveSharedComments(ISharedComments comments, long papaId, String papaName) throws SQLException {

        if (comments != null) {
            PreparedStatement pst = this.prepareStatement("INSERT INTO shared_comments (id, papa_id, papa_name, comments_count) VALUES (?, ?, ?, ?)");
            long comments_id = IdProvider.getInstance().getNextId();
            this.setLongOrNull(1, comments_id, pst);
            this.setLongOrNull(2, papaId, pst);
            this.setStringOrNull(3, papaName, pst);

            setIntegerOrNull(4, comments.getCount(), pst);
            
            pst.execute();

            List<ISharedCommentData> commentDataList = comments.getDataList();

            if (commentDataList != null) {
                pst = this.prepareStatement("INSERT INTO shared_comment_data (id, comment_id, message, fb_id, created_time, likes) VALUES (?, ?, ?, ?, ?, ?)");
                pst.clearBatch();
                this.setLongOrNull(2, comments_id, pst);
                for (ISharedCommentData commentData : commentDataList) {
                    long commentData_id = IdProvider.getInstance().getNextId();
                    this.setLongOrNull(1, commentData_id, pst);
                    this.setStringOrNull(3, commentData.getMessage(), pst);
                    this.setStringOrNull(4, commentData.getId(), pst);
                    this.setLongOrNull(5, commentData.getCreatedTime(), pst);
                    this.setIntegerOrNull(6, commentData.getLikes(), pst);
                    
                    SharedFromDAOSql.getInstance().saveSharedFrom(commentData.getFrom(), commentData_id, "shared_comment_data");

                    pst.addBatch();
                }

                pst.executeBatch();
            }

            ISharedPaging paging = comments.getPaging();

            if (paging != null) {

                pst = this.prepareStatement("INSERT INTO shared_paging (id, papa_id, papa_name, paging_next, paging_previous) VALUES (?, ?, ?, ?, ?)");

                this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
                this.setLongOrNull(2, comments_id, pst);
                this.setStringOrNull(3, "shared_comments", pst);

                this.setStringOrNull(4, paging.getNext(), pst);

                this.setStringOrNull(5, paging.getPrevious(), pst);

                pst.execute();
            }
        }
    }
}
