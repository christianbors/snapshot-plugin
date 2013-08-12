package org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory;

import com.mongodb.MongoException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import org.json.simple.parser.ParseException;
import org.openide.util.NotImplementedException;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.DbConfiguration;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IActivityDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IBookDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IEventDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IExpandedArchiveDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IFeedDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IFriendDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IGroupDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IHomeDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IInboxDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IInterestDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.ILikeDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.ILinkDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IMovieDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IMusicDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.INoteDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IOutboxDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IPhotoDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IPostDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IProfileDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IRootAccountDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.ISnapshotInfoDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IStatusDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.ITelevisionDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.NoSnapshotsException;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.ActivityDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.BookDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.ConnectionTestMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.EventDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.ExpandedArchiveDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.FeedDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.FriendDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.GroupDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.HomeDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.InboxDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.InterestDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.LikeDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.LinkDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.MovieDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.MusicDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.NoteDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.OutboxDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.PhotoDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.PostDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.ProfileDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.RootAccountDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.SnapshotInfoDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.StatusDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb.TelevisionDAOMongo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.ActivityDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.BookDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.ConnectionTestSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.EventDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.ExpandedArchiveDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.FeedDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.FriendDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.GroupDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.HomeDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.InboxDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.InterestDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.LikeDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.LinkDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.MovieDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.MusicDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.NoteDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.OutboxDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.PhotoDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.PostDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.ProfileDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.RootAccountDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.SnapshotInfoDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.StatusDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.TelevisionDAOSql;

public class DaoFactory {

    public static boolean testCurrentConnection() throws ClassNotFoundException, SQLException, NoSnapshotsException, UnknownHostException, MongoException, IOException, ParseException {
        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case POSTGRESQL:
                return new ConnectionTestSql().testCurrentConnection();
            case MYSQL:
                return new ConnectionTestSql().testCurrentConnection();
            case MONGODB:
                return new ConnectionTestMongo().testCurrentConnection();
            default:
                throw new NotImplementedException("ConnectionTest for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }

    public static boolean testNewConnection(DbConfiguration config) throws ClassNotFoundException, SQLException, NoSnapshotsException, UnknownHostException, IOException, ParseException {
        switch (config.getDbMode()) {
            case POSTGRESQL:
                return new ConnectionTestSql().testNewConnection(config);
            case MYSQL:
                return new ConnectionTestSql().testNewConnection(config);
            case MONGODB:
                return new ConnectionTestMongo().testNewConnection(config);
            default:
                throw new NotImplementedException("ConnectionTest for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }

    public static IProfileDAO getProfileDAO() throws SQLException, UnknownHostException, MongoException {

        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case POSTGRESQL:
                return ProfileDAOSql.getInstance();
            case MYSQL:
                return ProfileDAOSql.getInstance();
            case MONGODB:
                return ProfileDAOMongo.getInstance();
            default:
                throw new NotImplementedException("ProfileDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }
    
    public static IExpandedArchiveDAO getExpandedArchiveDAO() throws SQLException, UnknownHostException, MongoException {

        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case POSTGRESQL:
                return ExpandedArchiveDAOSql.getInstance();
            case MYSQL:
                throw new NotImplementedException("ExpandedArchiveDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
            case MONGODB:
                return ExpandedArchiveDAOMongo.getInstance();
            default:
                throw new NotImplementedException("ExpandedArchiveDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }

    public static IStatusDAO getStatusDAO() throws SQLException, UnknownHostException, MongoException {

        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case POSTGRESQL:
                return StatusDAOSql.getInstance();
            case MYSQL:
                return StatusDAOSql.getInstance();
            case MONGODB:
                return StatusDAOMongo.getInstance();
            default:
                throw new NotImplementedException("StatusDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }
    
    public static INoteDAO getNoteDAO() throws SQLException, UnknownHostException, MongoException {

        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case POSTGRESQL:
                return NoteDAOSql.getInstance();
            case MYSQL:
                return NoteDAOSql.getInstance();
            case MONGODB:
                return NoteDAOMongo.getInstance();
            default:
                throw new NotImplementedException("NoteDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }
    
    public static ILinkDAO getLinkDAO() throws SQLException, UnknownHostException, MongoException {

        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case POSTGRESQL:
                return LinkDAOSql.getInstance();
            case MYSQL:
                return LinkDAOSql.getInstance();
            case MONGODB:
                return LinkDAOMongo.getInstance();
            default:
                throw new NotImplementedException("LinkDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }
    
    public static IHomeDAO getHomeDAO() throws SQLException, UnknownHostException, MongoException {

        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case POSTGRESQL:
                return HomeDAOSql.getInstance();
            case MYSQL:
                return HomeDAOSql.getInstance();
            case MONGODB:
                return HomeDAOMongo.getInstance();
            default:
                throw new NotImplementedException("HomeDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }

    public static ILikeDAO getLikeDAO() throws SQLException, UnknownHostException, MongoException {

        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case POSTGRESQL:
                return LikeDAOSql.getInstance();
            case MYSQL:
                return LikeDAOSql.getInstance();
            case MONGODB:
                return LikeDAOMongo.getInstance();
            default:
                throw new NotImplementedException("LikeDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }

    public static IInterestDAO getInterestDAO() throws SQLException, UnknownHostException, MongoException {

        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case POSTGRESQL:
                return InterestDAOSql.getInstance();
            case MYSQL:
                return InterestDAOSql.getInstance();
            case MONGODB:
                return InterestDAOMongo.getInstance();
            default:
                throw new NotImplementedException("InterestDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }

    public static IPostDAO getPostDAO() throws SQLException, UnknownHostException, MongoException {

        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case POSTGRESQL:
                return PostDAOSql.getInstance();
            case MYSQL:
                return PostDAOSql.getInstance();
            case MONGODB:
                return PostDAOMongo.getInstance();
            default:
                throw new NotImplementedException("PostDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }

    public static IEventDAO getEventDAO() throws UnknownHostException, MongoException, SQLException {

        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case POSTGRESQL:
                return EventDAOSql.getInstance();
            case MYSQL:
                return EventDAOSql.getInstance();
            case MONGODB:
                return EventDAOMongo.getInstance();
            default:
                throw new NotImplementedException("EventDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }

    public static IFriendDAO getFriendDAO() throws UnknownHostException, MongoException, SQLException {

        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case POSTGRESQL:
                return FriendDAOSql.getInstance();
            case MYSQL:
                return FriendDAOSql.getInstance();
            case MONGODB:
                return FriendDAOMongo.getInstance();
            default:
                throw new NotImplementedException("FriendDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }

    public static IMovieDAO getMovieDAO() throws UnknownHostException, MongoException, SQLException {
        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case POSTGRESQL:
                return MovieDAOSql.getInstance();
            case MYSQL:
                return MovieDAOSql.getInstance();
            case MONGODB:
                return MovieDAOMongo.getInstance();
            default:
                throw new NotImplementedException("MovieDao for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }

    public static IRootAccountDAO getRootAccountDAO() throws UnknownHostException, MongoException, SQLException {

        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case POSTGRESQL:
                return RootAccountDAOSql.getInstance();
            case MYSQL:
                return RootAccountDAOSql.getInstance();
            case MONGODB:
                return RootAccountDAOMongo.getInstance();
            default:
                throw new NotImplementedException("RootAccountDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }

    public static ISnapshotInfoDAO getSnapshotInfoDAO() throws UnknownHostException, MongoException, SQLException {

        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case POSTGRESQL:
                return SnapshotInfoDAOSql.getInstance();
            case MYSQL:
                return SnapshotInfoDAOSql.getInstance();
            case MONGODB:
                return SnapshotInfoDAOMongo.getInstance();
            default:
                throw new NotImplementedException("SnapshotInfoDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }

    public static IInboxDAO getInboxDAO() throws UnknownHostException, MongoException, SQLException {

        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case MONGODB:
                return InboxDAOMongo.getInstance();
            case POSTGRESQL:
                return InboxDAOSql.getInstance();
            case MYSQL:
                return InboxDAOSql.getInstance();
            default:
                throw new NotImplementedException("InboxDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }

    public static IOutboxDAO getOutboxDAO() throws UnknownHostException, MongoException, SQLException {

        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case MONGODB:
                return OutboxDAOMongo.getInstance();
            case POSTGRESQL:
                return OutboxDAOSql.getInstance();
            case MYSQL:
                return OutboxDAOSql.getInstance();
            default:
                throw new NotImplementedException("OutboxDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }

    public static IFeedDAO getFeedDAO() throws UnknownHostException, MongoException, SQLException {

        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case MONGODB:
                return FeedDAOMongo.getInstance();
            case POSTGRESQL:
                return FeedDAOSql.getInstance();
            case MYSQL:
                return FeedDAOSql.getInstance();
            default:
                throw new NotImplementedException("FeedDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }

    public static IPhotoDAO getPhotoDAO() throws UnknownHostException, MongoException, SQLException {

        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case MONGODB:
                return PhotoDAOMongo.getInstance();
            case POSTGRESQL:
                return PhotoDAOSql.getInstance();
            case MYSQL:
                return PhotoDAOSql.getInstance();
            default:
                throw new NotImplementedException("PhotoDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }

    public static ITelevisionDAO getTelevisionDAO() throws UnknownHostException, MongoException, SQLException {

        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case POSTGRESQL:
                return TelevisionDAOSql.getInstance();
            case MYSQL:
                return TelevisionDAOSql.getInstance();
            case MONGODB:
                return TelevisionDAOMongo.getInstance();
            default:
                throw new NotImplementedException("TelevisionDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }

    public static IMusicDAO getMusicDAO() throws UnknownHostException, MongoException, SQLException {

        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case POSTGRESQL:
                return MusicDAOSql.getInstance();
            case MYSQL:
                return MusicDAOSql.getInstance();
            case MONGODB:
                return MusicDAOMongo.getInstance();
            default:
                throw new NotImplementedException("MusicDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }

    public static IGroupDAO getGroupDAO() throws UnknownHostException, MongoException, SQLException {

        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case POSTGRESQL:
                return GroupDAOSql.getInstance();
            case MYSQL:
                return GroupDAOSql.getInstance();
            case MONGODB:
                return GroupDAOMongo.getInstance();
            default:
                throw new NotImplementedException("GroupDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }

    public static IBookDAO getBookDAO() throws UnknownHostException, MongoException, SQLException {

        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case MONGODB:
                return BookDAOMongo.getInstance();
            case POSTGRESQL:
                return BookDAOSql.getInstance();
            case MYSQL:
                return BookDAOSql.getInstance();
            default:
                throw new NotImplementedException("BookDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }

    public static IActivityDAO getActivityDAO() throws UnknownHostException, MongoException, SQLException {

        switch (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode()) {
            case MONGODB:
                return ActivityDAOMongo.getInstance();
            case POSTGRESQL:
                return ActivityDAOSql.getInstance();
            case MYSQL:
                return ActivityDAOSql.getInstance();
            default:
                throw new NotImplementedException("ActivityDAO for " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " does not exist!");
        }
    }
}
