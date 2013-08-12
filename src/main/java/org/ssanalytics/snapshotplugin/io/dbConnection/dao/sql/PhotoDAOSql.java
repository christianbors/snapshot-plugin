package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedLikeDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedFromDAOSql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IPhotoDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedCommentsDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedPlaceDAOSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhoto;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoImage;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoTag;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoTagData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.photo.PhotoImageSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.photo.PhotoSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.photo.PhotoTagDataSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.photo.PhotoTagSql;

public class PhotoDAOSql extends AbstractSuperDAOSql implements IPhotoDAO {

    
    public static long NumberOfOperations;
    public static long NumberOfPhotos;
    public static long NumberOfAccounts;
    public static long totalTime;
    public static boolean withoutBatchInsert = false;
    
    
    private static PhotoDAOSql instance = null;

    protected PhotoDAOSql() throws SQLException {
        super();
        this.table_name = "photo";
    }

    public static PhotoDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new PhotoDAOSql();
        }
        return instance;
    }

    public void savePhotoList(List<IPhoto> toSave, long accountId) throws SQLException {
        
        if(withoutBatchInsert){
            this.savePhotoListWithoutBatchInsert(toSave, accountId);
            return;
        }         
        
        NumberOfAccounts++;
        
        totalTime -= System.currentTimeMillis();
        
        if (toSave == null) {
            return;
        }
        
        PreparedStatement pstOuter = this
                    .prepareStatement("INSERT INTO photo (id, account_id, fb_id, fb_name, created_time, updated_time, link, height, width, position, icon, picture, source) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        for (IPhoto photo : toSave) { 
            
            NumberOfPhotos++;

            long photo_id = IdProvider.getInstance().getNextId();

            this.setLongOrNull(1, photo_id, pstOuter);
            this.setLongOrNull(2, accountId, pstOuter);

            this.setStringOrNull(3, photo.getId(), pstOuter);
            this.setStringOrNull(4, photo.getName(), pstOuter);
            this.setLongOrNull(5, photo.getCreated_time(), pstOuter);
            this.setLongOrNull(6, photo.getUpdated_time(), pstOuter);
            this.setStringOrNull(7, photo.getLink(), pstOuter);
            this.setIntegerOrNull(8, photo.getHeight(), pstOuter);
            this.setIntegerOrNull(9, photo.getWidth(), pstOuter);
            this.setIntegerOrNull(10, photo.getPosition(), pstOuter);
            this.setStringOrNull(11, photo.getIcon(), pstOuter);
            this.setStringOrNull(12, photo.getPicture(), pstOuter);
            this.setStringOrNull(13, photo.getSource(), pstOuter);

            pstOuter.addBatch();

            // save photo tags
            IPhotoTag tags = photo.getPhotoTags();

            if (tags != null) {

                PreparedStatement pst = this.prepareStatement("INSERT INTO photo_tag (id, photo_id) VALUES (?, ?)");
                long tag_id = IdProvider.getInstance().getNextId();

                this.setLongOrNull(1, tag_id, pst);
                this.setLongOrNull(2, photo_id, pst);

                pst.execute();
                NumberOfOperations++;


                List<IPhotoTagData> tagDataList = tags.getTagDataList();

                if (tagDataList != null && tagDataList.size() > 0) {

                    pst = this.prepareStatement("INSERT INTO photo_tag_data (id, tag_id, x, y, fb_id, fb_name, created_time) VALUES (?, ?, ?, ?, ?, ?, ?)");
                    pst.clearBatch();

                    for (IPhotoTagData tagData : tagDataList) {
                        this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
                        this.setLongOrNull(2, tag_id, pst);
                        this.setDoubleOrNull(3, tagData.getX(), pst);
                        this.setDoubleOrNull(4, tagData.getY(), pst);

                        this.setStringOrNull(5, tagData.getId(), pst);
                        this.setStringOrNull(6, tagData.getName(), pst);
                        this.setLongOrNull(7, tagData.getCreated_time(), pst);

                        pst.addBatch();
                    }

                    pst.executeBatch();
                    NumberOfOperations++;
                }
            }

            //save photo images

            List<IPhotoImage> imageList = photo.getImageList();

            if (imageList != null && imageList.size() > 0) {

                PreparedStatement pst = this.prepareStatement("INSERT INTO photo_image (id, photo_id, height, width, source) VALUES (?, ?, ?, ?, ?)");
                pst.clearBatch();

                for (IPhotoImage image : imageList) {

                    this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
                    this.setLongOrNull(2, photo_id, pst);

                    this.setIntegerOrNull(3, image.getHeight(), pst);
                    this.setIntegerOrNull(4, image.getWidth(), pst);
                    this.setStringOrNull(5, image.getSource(), pst);

                    pst.addBatch();
                }

                pst.executeBatch();
                NumberOfOperations++;
            }

            //save place
            SharedPlaceDAOSql.getInstance().saveSharedPlace(photo.getPlace(), photo_id, this.table_name);
            NumberOfOperations++;

            //save from
            SharedFromDAOSql.getInstance().saveSharedFrom(photo.getFrom(), photo_id, this.table_name);
            NumberOfOperations++;

            //save likes
            SharedLikeDAOSql.getInstance().saveSharedLike(photo.getLikes(), photo_id, this.table_name);
            NumberOfOperations += 2;


            //save place
            SharedPlaceDAOSql.getInstance().saveSharedPlace(photo.getPlace(), photo_id, this.table_name);
            NumberOfOperations++;

            //save comments
            SharedCommentsDAOSql.getInstance().saveSharedComments(photo.getComments(), photo_id, table_name);
            NumberOfOperations += 2;
        }
        
        pstOuter.executeBatch();
        NumberOfOperations++;
        
        totalTime += System.currentTimeMillis();
        
    }

    public void savePhotoListWithoutBatchInsert(List<IPhoto> toSave, long accountId) throws SQLException {

        totalTime -= System.currentTimeMillis();
        NumberOfAccounts++;
        
        if (toSave != null) {
            for (IPhoto photo : toSave) {
                this.savePhoto(photo, accountId);
                NumberOfPhotos++;
            }
        }
        
        totalTime += System.currentTimeMillis();
    }

    public void savePhoto(IPhoto toSave, long accountId) throws SQLException {

        if (toSave == null) {
            return;
        }

        PreparedStatement pst = this
                .prepareStatement("INSERT INTO photo (id, account_id, fb_id, fb_name, created_time, updated_time, link, height, width, position, icon, picture, source) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        long photo_id = IdProvider.getInstance().getNextId();

        this.setLongOrNull(1, photo_id, pst);
        this.setLongOrNull(2, accountId, pst);

        this.setStringOrNull(3, toSave.getId(), pst);
        this.setStringOrNull(4, toSave.getName(), pst);
        this.setLongOrNull(5, toSave.getCreated_time(), pst);
        this.setLongOrNull(6, toSave.getUpdated_time(), pst);
        this.setStringOrNull(7, toSave.getLink(), pst);
        this.setIntegerOrNull(8, toSave.getHeight(), pst);
        this.setIntegerOrNull(9, toSave.getWidth(), pst);
        this.setIntegerOrNull(10, toSave.getPosition(), pst);
        this.setStringOrNull(11, toSave.getIcon(), pst);
        this.setStringOrNull(12, toSave.getPicture(), pst);
        this.setStringOrNull(13, toSave.getSource(), pst);

        pst.execute();
        NumberOfOperations++;

        // save photo tags
        IPhotoTag tags = toSave.getPhotoTags();

        if (tags != null) {

            pst = this.prepareStatement("INSERT INTO photo_tag (id, photo_id) VALUES (?, ?)");
            long tag_id = IdProvider.getInstance().getNextId();

            this.setLongOrNull(1, tag_id, pst);
            this.setLongOrNull(2, photo_id, pst);

            pst.execute();
            NumberOfOperations++;


            List<IPhotoTagData> tagDataList = tags.getTagDataList();

            if (tagDataList != null) {

                pst = this.prepareStatement("INSERT INTO photo_tag_data (id, tag_id, x, y, fb_id, fb_name, created_time) VALUES (?, ?, ?, ?, ?, ?, ?)");

                for (IPhotoTagData tagData : tagDataList) {
                    this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
                    this.setLongOrNull(2, tag_id, pst);
                    this.setDoubleOrNull(3, tagData.getX(), pst);
                    this.setDoubleOrNull(4, tagData.getY(), pst);

                    this.setStringOrNull(5, tagData.getId(), pst);
                    this.setStringOrNull(6, tagData.getName(), pst);
                    this.setLongOrNull(7, tagData.getCreated_time(), pst);

                    pst.execute();
                    NumberOfOperations++;
                }

            }
        }

        //save photo images

        List<IPhotoImage> imageList = toSave.getImageList();

        if (imageList != null) {

            pst = this.prepareStatement("INSERT INTO photo_image (id, photo_id, height, width, source) VALUES (?, ?, ?, ?, ?)");
         

            for (IPhotoImage image : imageList) {

                this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
                this.setLongOrNull(2, photo_id, pst);

                this.setIntegerOrNull(3, image.getHeight(), pst);
                this.setIntegerOrNull(4, image.getWidth(), pst);
                this.setStringOrNull(5, image.getSource(), pst);

                pst.execute();
                NumberOfOperations++;
            }
        }

        //save place
        SharedPlaceDAOSql.getInstance().saveSharedPlace(toSave.getPlace(), photo_id, this.table_name);
        NumberOfOperations++;

        //save from
        SharedFromDAOSql.getInstance().saveSharedFrom(toSave.getFrom(), photo_id, this.table_name);
        NumberOfOperations++;

        //save likes
        SharedLikeDAOSql.getInstance().saveSharedLikeWihtoutBatch(toSave.getLikes(), photo_id, this.table_name);
        NumberOfOperations++;
        if((toSave.getLikes() != null) && (toSave.getLikes().getDataList() != null)){
            NumberOfOperations += toSave.getLikes().getDataList().size();
        }

        //save place
        SharedPlaceDAOSql.getInstance().saveSharedPlace(toSave.getPlace(), photo_id, this.table_name);
        NumberOfOperations++;

        //save comments
        SharedCommentsDAOSql.getInstance().saveSharedCommentsWithoutBatch(toSave.getComments(), photo_id, table_name);
        NumberOfOperations++;
        if((toSave.getComments() != null) && (toSave.getComments().getDataList() != null)){
            NumberOfOperations += toSave.getComments().getDataList().size();
        }
    }

    private List<IPhotoImage> getPhotoImageList(long photo_id) throws SQLException {
        PreparedStatement pst = this.prepareStatement("SELECT height, width, source FROM photo_image WHERE photo_id = ?");
        this.setLongOrNull(1, photo_id, pst);

        List<IPhotoImage> imageList = new LinkedList<>();

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            imageList.add(new PhotoImageSql(rs.getInt("height"), rs.getInt("width"), rs.getString("source")));
        }


        return imageList;
    }

    private IPhotoTag getPhotoTag(long photo_id) throws SQLException {

        PreparedStatement pst = this.prepareStatement("SELECT tag_id, x, y, fb_id, fb_name, created_time FROM photo_tag_data WHERE tag_id IN (SELECT id FROM photo_tag WHERE photo_id = ?)");
        this.setLongOrNull(1, photo_id, pst);

        ResultSet rs = pst.executeQuery();

        List<IPhotoTagData> dataList = new LinkedList<>();

        while (rs.next()) {
            dataList.add(new PhotoTagDataSql(rs.getString("fb_id"), rs.getString("fb_name"), rs.getLong("created_time"), rs.getDouble("x"), rs.getDouble("y")));
        }

        return new PhotoTagSql(dataList);
    }

    private List<IPhoto> getPhotoList(PreparedStatement pst) throws SQLException {
        ResultSet rs = pst.executeQuery();

        List<IPhoto> retList = new LinkedList<>();

        while (rs.next()) {

            long photo_id = rs.getLong("id");

            ISharedFrom from = SharedFromDAOSql.getInstance().getSharedFrom(photo_id, this.table_name);
            ISharedLike like = SharedLikeDAOSql.getInstance().getSharedLike(photo_id, this.table_name);

            List<IPhotoImage> imageList = this.getPhotoImageList(photo_id);
            IPhotoTag tags = this.getPhotoTag(photo_id);



            PhotoSql photo = new PhotoSql(rs.getString("fb_id"), rs.getString("fb_name"), rs.getLong("created_time"), rs.getString("link"), rs.getLong("updated_time"), rs.getInt("height"), rs.getInt("width"), rs.getInt("position"), rs.getString("icon"), rs.getString("picture"), rs.getString("source"), imageList, from, like, tags, SharedPlaceDAOSql.getInstance().getSharedPlace(photo_id, this.table_name), SharedCommentsDAOSql.getInstance().getComments(photo_id, table_name));

            photo.setAccountId(this.getAccountInfoForTableDbId(photo_id)[0]);
            photo.setSnapshotId(this.getSnapshotNameForDbId(photo_id));

            retList.add(photo);
        }

        return retList;
    }

    @Override
    public List<IPhoto> getPhotoListForProfileInSnapshotSpecificVersion(String snapshotName, String version, String profile_id) throws SQLException {

        PreparedStatement pst = this.prepareStatement("SELECT id, fb_id, fb_name, created_time, updated_time, link, height, width, position, icon, picture, source FROM photo WHERE account_id IN (SELECT id FROM account WHERE fb_id = ? AND snapshotinfo_id = (SELECT id FROM snapshotinfo WHERE snapshotname = ? AND version = ?))");
        this.setStringOrNull(1, profile_id, pst);
        this.setStringOrNull(2, snapshotName, pst);
        this.setStringOrNull(3, version, pst);

        return this.getPhotoList(pst);
    }

    @Override
    public List<IPhoto> getPhotoListForProfileInSnapshotLatestVersion(String snapsthoName, String profile_id) throws Exception {
        String latestVersion = this.getHighestVersion(snapsthoName);
        return this.getPhotoListForProfileInSnapshotSpecificVersion(snapsthoName, latestVersion, profile_id);
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
    public List<IPhoto> getPhotoListSnapshotSpecificVersion(String snapshotName, String version) throws SQLException {

        PreparedStatement pst = this.prepareStatement("SELECT id, fb_id, fb_name, created_time, updated_time, link, height, width, position, icon, picture, source FROM photo WHERE account_id IN (SELECT id FROM account WHERE snapshotinfo_id = (SELECT id FROM snapshotinfo WHERE snapshotname = ? AND version = ?))");
        this.setStringOrNull(1, snapshotName, pst);
        this.setStringOrNull(2, version, pst);

        return this.getPhotoList(pst);
    }

    @Override
    public List<IPhoto> getPhotoListSnapshotLatestVersion(String snapsthoName) throws SQLException {
        return this.getPhotoListSnapshotSpecificVersion(snapsthoName, this.getHighestVersion(snapsthoName));
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
