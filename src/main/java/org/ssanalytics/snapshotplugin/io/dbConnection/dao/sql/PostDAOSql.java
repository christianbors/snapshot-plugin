package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedActionDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedLikeDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedCommentsDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedFromDAOSql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IPostDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedPlaceDAOSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.post.IPost;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.post.IPostApplication;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.post.PostApplicationSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.post.PostSql;

public class PostDAOSql extends AbstractSuperDAOSql implements IPostDAO {

    private static PostDAOSql instance = null;

    protected PostDAOSql() throws SQLException {
        super();
        this.table_name = "post";
    }

    public static PostDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new PostDAOSql();
        }
        return instance;
    }

    public void savePost(IPost toSave, long accountId) throws SQLException {

        if (toSave != null) {
            ArrayList<IPost> postList = new ArrayList<>();
            postList.add(toSave);
            this.savePostList(postList, accountId);
        }
    }

    private List<IPost> getPosts(ResultSet rs) throws SQLException {
         List<IPost> postList = new LinkedList<>();

        if (rs != null) {
            while (rs.next()) {

                Long postId = rs.getLong("id");
                String icon = rs.getString("icon");
                Long created_time = rs.getLong("created_time");
                Long updated_time = rs.getLong("updated_time");
                String fb_id = rs.getString("fb_id");
                String link = rs.getString("link");
                String fb_object_id = rs.getString("fb_object_id");
                String picture = rs.getString("picture");
                String fb_type = rs.getString("fb_type");
                String story = rs.getString("story");
                String caption = rs.getString("caption");
                String source = rs.getString("source");
                String description = rs.getString("description");
                String name = rs.getString("fb_name");

                if (postId != null) {

                    List<ISharedAction> actionList = SharedActionDAOSql.getInstance().getSharedActionList(postId, this.table_name);
                    ISharedComments comments = SharedCommentsDAOSql.getInstance().getComments(postId, this.table_name);

                    ISharedFrom from = SharedFromDAOSql.getInstance().getSharedFrom(postId, this.table_name);

                    ISharedLike likes = SharedLikeDAOSql.getInstance().getSharedLike(postId, this.table_name);

                    ISharedPlace place = SharedPlaceDAOSql.getInstance().getSharedPlace(postId, this.table_name);

                    IPostApplication application = this.getPostApplication(postId);

                    postList.add(new PostSql(fb_id, actionList, comments, from, icon, created_time, updated_time, likes, picture, fb_type, story, link, fb_object_id, place, null, name, description, source, caption, application));
                }
            }
        }

        return postList;
    }

    @Override
    public List<IPost> getPostListForProfileSnapshotSpecificVersion(String profile_id, String snapshot, String version) throws SQLException {

        PreparedStatement pst = this.prepareStatement("SELECT id, icon, created_time, updated_time, fb_id, link, fb_object_id, picture, fb_type, story, caption, source, description, fb_name FROM " + this.table_name + " WHERE account_id = ?");
        Long id = this.getAccountDbId(profile_id, snapshot, version);



        if (id == null) {
            throw new SQLException("Couldn't find an entry for profile_id: " + profile_id + " in snapshot: " + snapshot + " version " + version);
        }

        this.setLongOrNull(1, id, pst);

        ResultSet rs = pst.executeQuery();

        return this.getPosts(rs);
    }

    private IPostApplication getPostApplication(Long postId) throws SQLException {

        if (postId == null) {
            return null;
        }

        PreparedStatement pst = this.prepareStatement("SELECT fb_id, fb_name FROM post_application WHERE post_id = ?");
        pst.setLong(1, postId);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return new PostApplicationSql(rs.getString("fb_id"), rs.getString("fb_name"));
        } else {
            return null;
        }
    }

    private void saveApplication(Long postId, IPostApplication application) throws SQLException {
        if (application == null) {
            return;
        }

        PreparedStatement pst = this.prepareStatement("INSERT INTO post_application (id, post_id, fb_id, fb_name) VALUES (?, ?, ?, ?)");
        this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
        this.setLongOrNull(2, postId, pst);
        this.setStringOrNull(3, application.getId(), pst);
        this.setStringOrNull(4, application.getName(), pst);

        pst.execute();
    }

    public void savePostList(List<IPost> toSave, long accountId) throws SQLException {

        if (toSave == null) {
            return;
        }

        PreparedStatement pst = this.prepareStatement("INSERT INTO post (id, account_id, icon, created_time, updated_time, fb_id, link, fb_object_id, picture, fb_type, story, fb_name, caption, source, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        pst.clearBatch();

        for (IPost post : toSave) {

            long postId = IdProvider.getInstance().getNextId();

            this.setLongOrNull(1, postId, pst);
            this.setLongOrNull(2, accountId, pst);
            this.setStringOrNull(3, post.getIcon(), pst);
            this.setLongOrNull(4, post.getCreated_time(), pst);
            this.setLongOrNull(5, post.getUpdated_time(), pst);
            this.setStringOrNull(6, post.getId(), pst);
            this.setStringOrNull(7, post.getLink(), pst);
            this.setStringOrNull(8, post.getObject_id(), pst);
            this.setStringOrNull(9, post.getPicture(), pst);
            this.setStringOrNull(10, post.getType(), pst);
            this.setStringOrNull(11, post.getStory(), pst);
            this.setStringOrNull(12, post.getName(), pst);
            this.setStringOrNull(13, post.getCaption(), pst);
            this.setStringOrNull(14, post.getSource(), pst);
            this.setStringOrNull(15, post.getDescription(), pst);

            pst.addBatch();

            // save actions
            SharedActionDAOSql.getInstance().saveSharedAction(post.getActionList(), postId, this.table_name);

            // save comments
            SharedCommentsDAOSql.getInstance().saveSharedComments(post.getComments(), postId, this.table_name);

            // save from
            SharedFromDAOSql.getInstance().saveSharedFrom(post.getFrom(), postId, this.table_name);

            // save likes
            SharedLikeDAOSql.getInstance().saveSharedLike(post.getLikes(), postId, this.table_name);

            // save place
            SharedPlaceDAOSql.getInstance().saveSharedPlace(post.getPlace(), postId, this.table_name);

            // save application
            this.saveApplication(postId, post.getApplication());

            // save privacy
            // TODO
        }

        try {
            pst.executeBatch();
        } catch (SQLException sex) {
            sex.printStackTrace();
            sex.getNextException().printStackTrace();
        }
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
    public List<IPost> getPostListForProfileSnapshotLatestVersion(String profile_id, String snapshot) throws Exception {
        return this.getPostListForProfileSnapshotSpecificVersion(profile_id, snapshot, this.getHighestVersion(snapshot));
    }

    @Override
    public List<IPost> getAllPostsInSnapshotLatestVersion(String snapshot) throws Exception {
        return this.getAllPostsInSnapshotSpecificVersion(snapshot, this.getHighestVersion(snapshot));
    }

    @Override
    public List<IPost> getAllPostsInSnapshotSpecificVersion(String snapshot, String version) throws Exception {
        
        PreparedStatement pst = this.prepareStatement("SELECT id, icon, created_time, updated_time, fb_id, link, fb_object_id, picture, fb_type, story, caption, source, description, fb_name FROM " + this.table_name + " WHERE account_id IN (SELECT id FROM account WHERE snapshotinfo_id = (SELECT id FROM snapshotinfo WHERE snapshotinfoName = ? AND version = ?))");
        this.setStringOrNull(1, snapshot, pst);
        this.setStringOrNull(2, version, pst);

        
        ResultSet rs = pst.executeQuery();

        return this.getPosts(rs);
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
