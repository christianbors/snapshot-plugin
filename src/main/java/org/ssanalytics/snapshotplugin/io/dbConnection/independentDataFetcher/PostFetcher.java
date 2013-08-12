/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sharedHelpers.WeekDayHelper;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.post.IPost;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLikeData;

/**
 *
 * @author chw
 */
public class PostFetcher {

    private PostFetcher() {
    }
    private static PostFetcher instance = null;

    public static PostFetcher getInstance() {
        if (instance == null) {
            instance = new PostFetcher();
        }
        return instance;
    }
    
    public Map<IPost, Integer> getPostCommentCountForProfileForSnapshotLatestVersion(String snapshot, String profileId) throws Exception {
        return this.getPostCommentCountForProfileForSnapshotSpecificVersion(snapshot, DaoFactory.getSnapshotInfoDAO().findHighestVersion(snapshot), profileId);
    }

    public Map<IPost, Integer> getPostCommentCountForProfileForSnapshotSpecificVersion(String snapshot, String version, String profileId) throws Exception {
        Map<IPost, Integer> retMap = new HashMap<>();

        List<IPost> postList = DaoFactory.getPostDAO().getPostListForProfileSnapshotSpecificVersion(profileId, snapshot, version);

        for (IPost post : postList) {
            ISharedComments comments = post.getComments();
            Integer comCount = 0;
            if (comments != null) {
                comCount = comments.getCount();
                if(comCount == null)
                    comCount = 0;
            }

            retMap.put(post, comCount);
        }

        return retMap;
    }

    public Map<IPost, Integer> getPostLikeCountForProfileForSnapshotLatestVersion(String snapshot, String profileId) throws Exception {
        return this.getPostLikeCountForProfileForSnapshotSpecificVersion(snapshot, DaoFactory.getSnapshotInfoDAO().findHighestVersion(snapshot), profileId);
    }

    public Map<IPost, Integer> getPostLikeCountForProfileForSnapshotSpecificVersion(String snapshot, String version, String profileId) throws Exception {
        Map<IPost, Integer> retMap = new HashMap<>();

        List<IPost> postList = DaoFactory.getPostDAO().getPostListForProfileSnapshotSpecificVersion(profileId, snapshot, version);

        for (IPost post : postList) {
            ISharedLike like = post.getLikes();
            Integer likeCount = 0;
            if (like != null) {
                likeCount = like.getCount();
                if(likeCount == null)
                    likeCount = 0;
            }

            retMap.put(post, likeCount);
        }

        return retMap;
    }
    
    public Map<Date, Integer> getPostCountPerWeekForSnapshotRootLatestVersion(String snapshot) throws Exception {
        return this.getPostCountPerWeekForSnapshotRootSpecificVersion(snapshot, DaoFactory.getSnapshotInfoDAO().findHighestVersion(snapshot));
    }
    
    public Map<Date, Integer> getPostCountPerWeekForSnapshotRootSpecificVersion(String snapshot, String version) throws Exception {
        return this.getPostCountPerWeekForProfileInSnapshotSpecificVersion(snapshot, version, DaoFactory.getSnapshotInfoDAO().getRootAccountFbId(snapshot, version));
    }
    
    public Map<Date, Integer> getPostCountPerWeekForProfileInSnapshotLatestVersion(String snapshot, String profileId) throws Exception {
        return this.getPostCountPerWeekForProfileInSnapshotSpecificVersion(snapshot, DaoFactory.getSnapshotInfoDAO().findHighestVersion(snapshot), profileId);
    }
    
    public Map<Date, Integer> getPostCountPerWeekForProfileInSnapshotSpecificVersion(String snapshot, String version, String profileId) throws Exception {
        List<IPost> postList = DaoFactory.getPostDAO().getPostListForProfileSnapshotSpecificVersion(profileId, snapshot, version);
        
        Map<Date, Integer> retMap = new HashMap<>();
        
        for(IPost post : postList){
            
            Date dt = WeekDayHelper.getInstance().getLastSunday(post.getCreated_time());
            if(dt == null)
                continue;
            Integer i = 1;
            if(retMap.containsKey(dt))
                i = 1 + retMap.get(dt);
            
            retMap.put(dt, i);            
        }
        
        return retMap;
    }
}
