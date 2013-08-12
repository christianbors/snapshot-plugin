package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedCommentsDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedFromDAOSql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.ILinkDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedPrivacyDAOSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.link.ILink;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedCommentData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPrivacy;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.link.LinkSql;

public class LinkDAOSql extends AbstractSuperDAOSql implements ILinkDAO {

    private static LinkDAOSql instance = null;

    protected LinkDAOSql() throws SQLException {
        super();
        this.table_name = "link";
    }

    public static LinkDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new LinkDAOSql();
        }
        return instance;
    }
    
    @Override
    public List<ILink> getAllLinksInSnapshotSpecificVersion(String snapshotName, String version) throws SQLException {
        PreparedStatement pst = this.prepareStatement("SELECT id, icon, link, picture, description, message, fb_id, created_time, fb_name FROM " + this.table_name + " WHERE account_id IN (SELECT id FROM account WHERE snapshotinfo_id = (SELECT id from snapshotinfo WHERE snapshotname = ? AND version = ?))");
        this.setStringOrNull(1, snapshotName, pst);
        this.setStringOrNull(2, version, pst);

        ResultSet rs = pst.executeQuery();
        
        List<ILink> linkList = new LinkedList<>();
                
        while(rs.next()){
            Long link_id = rs.getLong("id");
            String icon = rs.getString("icon");
            String link = rs.getString("link");
            String picture = rs.getString("picture");
            String description = rs.getString("description");
            String message = rs.getString("message");
            String fb_id = rs.getString("fb_id");
            String fb_name = rs.getString("fb_name");
            Long created_time = rs.getLong("created_time");
            
            ISharedPrivacy prv = SharedPrivacyDAOSql.getInstance().getSharedPrivacy(link_id, this.table_name);
            ISharedFrom from = SharedFromDAOSql.getInstance().getSharedFrom(link_id, this.table_name);
            ISharedComments comments = SharedCommentsDAOSql.getInstance().getComments(link_id, this.table_name);
            
            linkList.add(new LinkSql(fb_id, fb_name, created_time, from, icon, link, picture, description, message, comments, prv));            
        }
        
        return linkList;
    }
    

    public void saveLinkList(List<ILink> toSave, long accountId) throws SQLException {

        if (toSave == null) {
            return;
        }
        
        PreparedStatement pst = this.prepareStatement("INSERT INTO link (id, account_id, icon, link, picture, description, message, fb_id, fb_name, created_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        pst.clearBatch();

        for (ILink link : toSave) {

            long link_id = IdProvider.getInstance().getNextId();

            this.setLongOrNull(1, link_id, pst);
            this.setLongOrNull(2, accountId, pst);

            this.setStringOrNull(3, link.getIcon(), pst);
            this.setStringOrNull(4, link.getLink(), pst);
            this.setStringOrNull(5, link.getPicture(), pst);
            this.setStringOrNull(6, link.getDescription(), pst);
            this.setStringOrNull(7, link.getMessage(), pst);
            this.setStringOrNull(8, link.getId(), pst);
            this.setStringOrNull(9, link.getName(), pst);
            this.setLongOrNull(10, link.getCreated_time(), pst);

            pst.addBatch();

            //save from
            SharedFromDAOSql.getInstance().saveSharedFrom(link.getFrom(), link_id, this.table_name);

            //save comments    	
            SharedCommentsDAOSql.getInstance().saveSharedComments(link.getComments(), link_id, this.table_name);
            
            //save privacy
            SharedPrivacyDAOSql.getInstance().saveSharedPrivacy(link.getPrivacy(), link_id, table_name);
        }
        try{
        pst.executeBatch();
        }catch(SQLException sex){
            sex.getNextException().printStackTrace();
            throw sex;
        }



    }

    @Override
    public List<ILink> getAllLinksInSnapshotLatestVersion(String snapshotName) throws SQLException {
        return this.getAllLinksInSnapshotSpecificVersion(snapshotName, this.getHighestVersion(snapshotName));
    }

    @Override
    public List<ISharedComments> getAllCommentsInSnapshotLatestVersion(String snapshot) throws SQLException {
        return this.getAllCommentsInSnapshotSpecificVersion(snapshot, this.getHighestVersion(snapshot));
    }

    @Override
    public List<ISharedComments> getAllCommentsInSnapshotSpecificVersion(String snapshot, String version) throws SQLException {
        return CommentDAOSql.getInstance().getCommentsForTableSnapshotSpecificVersion(this.table_name, snapshot, version);
    }


}
