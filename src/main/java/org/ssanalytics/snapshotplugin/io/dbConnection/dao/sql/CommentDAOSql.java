/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedCommentsDAOSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;

/**
 *
 * @author chw
 */
public class CommentDAOSql extends AbstractSuperDAOSql{
    
        private static CommentDAOSql instance = null;

    protected CommentDAOSql() throws SQLException {
        super();
    }

    public static CommentDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new CommentDAOSql();
        }
        return instance;
    }
    
    public List<ISharedComments> getCommentsForTableSnapshotSpecificVersion(String table_name, String snapshot, String version) throws SQLException{
        PreparedStatement pst = this.prepareStatement("SELECT id FROM " + table_name + " WHERE account_id IN (SELECT id FROM account WHERE snapshotinfo_id = (SELECT id FROM snapshotinfo WHERE snapshotname = ? AND version = ?))");
        this.setStringOrNull(1, snapshot, pst);
        this.setStringOrNull(2, version, pst);
        
        List<ISharedComments> commentList = new LinkedList<>();
        
        ResultSet rs = pst.executeQuery();
        
        while(rs.next()){
            ISharedComments c = SharedCommentsDAOSql.getInstance().getComments(rs.getLong("id"), table_name);
            if(c != null)
                commentList.add(c);
        }
        
        return commentList;
    }
    
}
