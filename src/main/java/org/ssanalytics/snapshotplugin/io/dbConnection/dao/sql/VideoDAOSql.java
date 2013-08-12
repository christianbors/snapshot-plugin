/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedFromDAOSql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IVideoDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedCommentsDAOSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideoFormat;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideoTagData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideoTags;

/**
 *
 * @author chw
 */
public class VideoDAOSql extends AbstractSuperDAOSql implements IVideoDAO{

    
    public static long NumberOfOperations;
    public static long NumberOfVideos;
    public static long NumberOfAccounts;
    public static long totalTime;
    public static boolean withoutBatchInsert = false;
    
    private static VideoDAOSql instance = null;

    protected VideoDAOSql() throws SQLException {
        super();
        this.table_name = "video";
    }

    public static VideoDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new VideoDAOSql();
        }
        return instance;
    }
    
    
    public void saveVideo(IVideo toSave, long accountId) throws SQLException {
        
        if(toSave != null){
            List<IVideo> al = new ArrayList<>(1);
            al.add(toSave);
            this.saveVideoList(al, accountId);
        }
    }
    
     public void saveVideoList(List<IVideo> toSave, long accountId) throws SQLException{
        
         
         NumberOfAccounts++;
         
         if(withoutBatchInsert){
             totalTime -= System.currentTimeMillis();
             this.saveVideoListWithoutBatch(toSave, accountId);
             totalTime += System.currentTimeMillis();
             return;
         }
         
         totalTime -= System.currentTimeMillis();
         
         if(toSave == null)
            return;
        
        PreparedStatement pst = this.prepareStatement("INSERT INTO " + this.table_name + " (id, account_id, updated_time, created_time, fb_id, source, picture, icon, embed_html, description) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        pst.clearBatch();
        
        for(IVideo video : toSave) {
            
            NumberOfVideos++;
         
            long video_id = IdProvider.getInstance().getNextId();
            pst.clearParameters();
            
            this.setLongOrNull(1, video_id, pst);
            this.setLongOrNull(2, accountId, pst);
            this.setLongOrNull(3, video.getUpdated_time(), pst);
            this.setLongOrNull(4, video.getCreated_time(), pst);
            this.setStringOrNull(5, video.getId(), pst);
            this.setStringOrNull(6, video.getSource(), pst);
            this.setStringOrNull(7, video.getPicture(), pst);
            this.setStringOrNull(8, video.getIcon(), pst);
            this.setStringOrNull(9, video.getEmbed_html(), pst);
            this.setStringOrNull(10, video.getDescription(), pst);   
            
            pst.addBatch();
            
            
            // save video format            
            this.saveVideoFormatList(video_id, video.getFormatList());
            
            // save tags
            this.saveVideoTag(video_id, video.getTags());
            
            // save shared from
            SharedFromDAOSql.getInstance().saveSharedFrom(video.getFrom(), video_id, this.table_name);
            NumberOfOperations++;
            
            // save shared comments
            SharedCommentsDAOSql.getInstance().saveSharedComments(video.getComments(), video_id, this.table_name);
            NumberOfOperations++;
            
        }     
        pst.executeBatch();
        NumberOfOperations++;

        totalTime += System.currentTimeMillis();
    }
    
    public void saveVideoListWithoutBatch(List<IVideo> toSave, long accountId) throws SQLException{
        
         if(toSave == null)
            return;
        
        PreparedStatement pst = this.prepareStatement("INSERT INTO " + this.table_name + " (id, account_id, updated_time, created_time, fb_id, source, picture, icon, embed_html, description) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        
        for(IVideo video : toSave) {
            
            NumberOfVideos++;
         
            long video_id = IdProvider.getInstance().getNextId();
            
            this.setLongOrNull(1, video_id, pst);
            this.setLongOrNull(2, accountId, pst);
            this.setLongOrNull(3, video.getUpdated_time(), pst);
            this.setLongOrNull(4, video.getCreated_time(), pst);
            this.setStringOrNull(5, video.getId(), pst);
            this.setStringOrNull(6, video.getSource(), pst);
            this.setStringOrNull(7, video.getPicture(), pst);
            this.setStringOrNull(8, video.getIcon(), pst);
            this.setStringOrNull(9, video.getEmbed_html(), pst);
            this.setStringOrNull(10, video.getDescription(), pst);   
            
            pst.execute();
            NumberOfOperations++;
            
            // save video format            
            this.saveVideoFormatListWithoutBatch(video_id, video.getFormatList());
            
            // save tags
            this.saveVideoTagWithoutBatch(video_id, video.getTags());
            
            // save shared from
            SharedFromDAOSql.getInstance().saveSharedFrom(video.getFrom(), video_id, this.table_name);
            NumberOfOperations++;
            
            // save shared comments
            SharedCommentsDAOSql.getInstance().saveSharedCommentsWithoutBatch(video.getComments(), video_id, this.table_name);
            NumberOfOperations++;
            
            if((video.getComments() != null) && (video.getComments().getDataList() != null)){
                NumberOfOperations += video.getComments().getDataList().size();
            }
            
        }
    }    
    
    private void saveVideoFormatList(long video_id, List<IVideoFormat> formatList) throws SQLException{
        if(formatList == null)
            return;
        
        PreparedStatement pst = this.prepareStatement("INSERT INTO video_format (id, video_id, embed_html, filter, height, width, picture) VALUES(?, ?, ?, ?, ?, ?, ?)");
        pst.clearBatch();
        
        for (IVideoFormat format : formatList) {
            pst.clearParameters();
            this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
            this.setLongOrNull(2, video_id, pst);
            this.setStringOrNull(3, format.getEmbed_html(), pst);
            this.setStringOrNull(4, format.getFilter(), pst);
            this.setIntegerOrNull(5, format.getHeight(), pst);
            this.setIntegerOrNull(6, format.getWidth(), pst);
            this.setStringOrNull(7, format.getPicture(), pst);

            pst.addBatch();
        
        }
        
        pst.executeBatch();
        NumberOfOperations++;
    }
    
    private void saveVideoFormatListWithoutBatch(long video_id, List<IVideoFormat> formatList) throws SQLException{
        if(formatList == null)
            return;
        
        PreparedStatement pst = this.prepareStatement("INSERT INTO video_format (id, video_id, embed_html, filter, height, width, picture) VALUES(?, ?, ?, ?, ?, ?, ?)");
        
        for (IVideoFormat format : formatList) {
            this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
            this.setLongOrNull(2, video_id, pst);
            this.setStringOrNull(3, format.getEmbed_html(), pst);
            this.setStringOrNull(4, format.getFilter(), pst);
            this.setIntegerOrNull(5, format.getHeight(), pst);
            this.setIntegerOrNull(6, format.getWidth(), pst);
            this.setStringOrNull(7, format.getPicture(), pst);

            pst.execute();
            NumberOfOperations++;
        
        }
    }    
    
    private void saveVideoTag(long video_id, IVideoTags tag) throws SQLException{
                
        if(tag == null)
            return;        
        PreparedStatement pst = this.prepareStatement("INSERT INTO video_tag (id, video_id) VALUES(?, ?)");
        
        long tag_id = IdProvider.getInstance().getNextId();
        this.setLongOrNull(1, tag_id, pst);
        this.setLongOrNull(2, video_id, pst);
        
        pst.execute();
        
        pst = this.prepareStatement("INSERT INTO video_tag_data (id, video_tag_id, fb_id, fb_name, created_time) VALUES (?, ?, ?, ?, ?)");
        pst.clearBatch();
                
        List<IVideoTagData> dataList = tag.getDataList();
        if(dataList != null){
            
            for (IVideoTagData data : dataList){
                
                pst.clearParameters();
                
                this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
                this.setLongOrNull(2, tag_id, pst);
                this.setStringOrNull(3, data.getId(), pst);
                this.setStringOrNull(4, data.getName(), pst);
                this.setLongOrNull(5, data.getCreated_time(), pst);
                
                pst.addBatch();
            }      
                  
            pst.executeBatch();
            NumberOfOperations++;
        }        
    }
    
    private void saveVideoTagWithoutBatch(long video_id, IVideoTags tag) throws SQLException{
                
        if(tag == null)
            return;
        
        PreparedStatement pst = this.prepareStatement("INSERT INTO video_tag (id, video_id) VALUES(?, ?)");
        
        long tag_id = IdProvider.getInstance().getNextId();
        this.setLongOrNull(1, tag_id, pst);
        this.setLongOrNull(2, video_id, pst);
        
        pst.execute();
        NumberOfOperations++;
        
        pst = this.prepareStatement("INSERT INTO video_tag_data (id, video_tag_id, fb_id, fb_name, created_time) VALUES (?, ?, ?, ?, ?)");
                
        List<IVideoTagData> dataList = tag.getDataList();
        if(dataList != null){
            
            for (IVideoTagData data : dataList){
                
                this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
                this.setLongOrNull(2, tag_id, pst);
                this.setStringOrNull(3, data.getId(), pst);
                this.setStringOrNull(4, data.getName(), pst);
                this.setLongOrNull(5, data.getCreated_time(), pst);
                
                pst.execute();
                NumberOfOperations++;
            }      
        }        
    }

    @Override
    public List<IVideo> getAllVideosInSnapshotLatestVersion(String snapshot) throws SQLException {
        return this.getAllVideosInSnapshotSpecificVersion(snapshot, this.getHighestVersion(snapshot));
    }

    @Override
    public List<IVideo> getAllVideosInSnapshotSpecificVersion(String snapshot, String version) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ISharedComments> getAllVideoCommentsInSnapshotLatestVersion(String snapshot) throws Exception {
        return this.getAllVideoCommentsInSnapshotSpecificVersion(snapshot, this.getHighestVersion(snapshot));
    }

    @Override
    public List<ISharedComments> getAllVideoCommentsInSnapshotSpecificVersion(String snapshot, String version) throws Exception {
        
         return CommentDAOSql.getInstance().getCommentsForTableSnapshotSpecificVersion(this.table_name, snapshot, version);
    }
}
