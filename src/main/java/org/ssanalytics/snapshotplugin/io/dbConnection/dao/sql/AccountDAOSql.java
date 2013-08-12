package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;


import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.account.IAccount;

public class AccountDAOSql extends AbstractSuperDAOSql  {

    private static AccountDAOSql instance = null;

    protected AccountDAOSql() throws SQLException {
        super();

        table_name = "account";
    }

    public static AccountDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new AccountDAOSql();
        }
        return instance;
    }

    public long saveAccount(long snapshotInfoDbId, IAccount toSave) throws SQLException {


        //save account itself
        long accountId = IdProvider.getInstance().getNextId();
        PreparedStatement pst = this.prepareStatement("INSERT INTO " + this.table_name + " (id, fb_id, snapshotinfo_id) VALUES (?, ?, ?)");

        this.setLongOrNull(1, accountId, pst);
        this.setStringOrNull(2, toSave.getProfile().getId(), pst);
        this.setLongOrNull(3, snapshotInfoDbId, pst);


        pst.execute();

        //save profile
        ProfileDAOSql.getInstance().saveProfile(toSave.getProfile(), accountId);


        //save activities
        ActivityDAOSql.getInstance().saveActivityList(toSave.getActivityList(), accountId);

        //save books
        BookDAOSql.getInstance().saveBookList(toSave.getBookList(), accountId);

        //save events
        EventDAOSql.getInstance().saveEventList(toSave.getEventList(), accountId);


        //save groups
        GroupDAOSql.getInstance().saveGroupList(toSave.getGroupList(), accountId);

        //save home
        HomeDAOSql.getInstance().saveHomeList(toSave.getHomeList(), accountId);

        //save interests
        InterestDAOSql.getInstance().saveInterestList(toSave.getInterests(), accountId);

        //save likes
        LikeDAOSql.getInstance().saveLikeList(toSave.getLikeList(), accountId);

        //save links
        LinkDAOSql.getInstance().saveLinkList(toSave.getLinkList(), accountId);

        //save movies
        MovieDAOSql.getInstance().saveMovieList(toSave.getMovieList(), accountId);

        //save music
        MusicDAOSql.getInstance().saveMusicList(toSave.getMusicList(), accountId);

        //save notes
        NoteDAOSql.getInstance().saveNoteList(toSave.getNoteList(), accountId);



        //save photos
        PhotoDAOSql.getInstance().savePhotoList(toSave.getPhotoList(), accountId);

        //save posts    	
        PostDAOSql.getInstance().savePostList(toSave.getPostList(), accountId);

        //save status
        StatusDAOSql.getInstance().saveStatusList(toSave.getStatusList(), accountId);

        //save tagged - not working
        TaggedDAOSql.getInstance().saveTaggedList(toSave.getTaggedList(), accountId);

        //save television
        TelevisionDAOSql.getInstance().saveTelevisionList(toSave.getTelevisionList(), accountId);

        //save video
        VideoDAOSql.getInstance().saveVideoList(toSave.getVideoList(), accountId);

        return accountId;
    }
}
