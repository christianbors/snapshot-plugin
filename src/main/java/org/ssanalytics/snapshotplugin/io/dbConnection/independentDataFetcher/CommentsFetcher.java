/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.feed.IFeed;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.home.IHome;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.link.ILink;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.note.INote;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhoto;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.post.IPost;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedCommentData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;

/**
 *
 * @author chw
 */
public class CommentsFetcher {

    private CommentsFetcher() {
    }
    private static CommentsFetcher instance = null;

    public static CommentsFetcher getInstance() {
        if (instance == null) {
            instance = new CommentsFetcher();
        }
        return instance;
    }

    private Map<NamedItem, Integer> addCommentCountToMap(ISharedComments comments, Map<NamedItem, Integer> map, String racId, IFriend friend) {

        if (comments != null) {
            List<ISharedCommentData> commentDataList = comments.getDataList();
            if (commentDataList != null) {

                for (ISharedCommentData cd : comments.getDataList()) {
                    if (cd.getFrom() != null) {
                        if (cd.getFrom().getId() != null) {
                            if (cd.getFrom().getId().equals(racId)) {
                                NamedItem ni = new NamedItem(friend.getId(), friend.getName());
                                Integer cnt = 1;
                                if (map.containsKey(ni)) {
                                    cnt += map.get(ni);
                                }
                                map.put(ni, cnt);
                            }
                        }
                    }
                }
            }
        }

        return map;
    }

    public Map<NamedItem, Integer> getCommentedOnCountFriendsActivitiesForSnapshotSpecificVersion(String snapshotId, String version) throws Exception {

        String racId = DaoFactory.getSnapshotInfoDAO().getRootAccountFbId(snapshotId, version);
        List<IFriend> friendList = DaoFactory.getFriendDAO().getFriendListSpecificVersion(snapshotId, version);

        Map<NamedItem, Integer> retMap = new HashMap<>();

        for (IFriend friend : friendList) {

            //check photos:
            List<IPhoto> photoList = DaoFactory.getPhotoDAO().getPhotoListForProfileInSnapshotSpecificVersion(snapshotId, version, friend.getId());
            if (photoList != null) {
                for (IPhoto photo : photoList) {

                    retMap = this.addCommentCountToMap(photo.getComments(), retMap, racId, friend);
                }
            }

            //check posts
            /* disabled because only the root has posts ...
             List<IPost> postList = DaoFactory.getPostDAO().getPostListForProfileSnapshotSpecificVersion(friend.getId(), snapshotId, version);
             if (postList != null) {
             for (IPost post : postList) {

             retMap = this.addCommentCountToMap(post.getComments(), retMap, racId, friend);
             }
             }
             */
        }

        return retMap;
    }

    /**
     * returns a map that says how often the root account of the snapshot has
     * commented on a friend's photo/...
     *
     * @param snapshotId
     * @return
     * @throws Exception
     */
    public Map<NamedItem, Integer> getCommentedOnCountFriendsActivitiesForSnapshotLatestVersion(String snapshotId) throws Exception {
        return this.getCommentedOnCountFriendsActivitiesForSnapshotSpecificVersion(snapshotId, DaoFactory.getSnapshotInfoDAO().findHighestVersion(snapshotId));

    }

    /**
     * returns a map that says how often each profile has commented on anything.
     *
     * @param snapshotId
     * @return
     * @throws Exception
     */
    public Map<NamedItem, Integer> getCommentCountForEveryProfileInSnapshotLatestVersion(String snapshotId) throws Exception {

        return this.getCommentCountForEveryProfileInSnapshotSpecificVersion(snapshotId, DaoFactory.getSnapshotInfoDAO().findHighestVersion(snapshotId));
    }

    private Map<NamedItem, Integer> countCommentsToMap(ISharedComments comments, Map<NamedItem, Integer> map, List<String> friendIds) {

        if (comments != null) {
            List<ISharedCommentData> cDataList = comments.getDataList();
            if (cDataList != null) {
                for (ISharedCommentData cd : cDataList) {
                    ISharedFrom from = cd.getFrom();
                    if (from != null) {
                        if (from.getId() != null) {
                            if (friendIds.contains(from.getId())) {
                                NamedItem ni = new NamedItem(from.getId(), from.getName());
                                Integer cnt = 1;
                                if (map.containsKey(ni)) {
                                    cnt += map.get(ni);
                                }
                                map.put(ni, cnt);
                            }
                        }
                    }
                }
            }
        }

        return map;
    }

    /**
     * returns a map that says how often each profile has commented on anything.
     *
     * @param snapshotId
     * @return
     * @throws Exception
     */
    public Map<NamedItem, Integer> getCommentCountForEveryProfileInSnapshotSpecificVersion(String snapshotId, String version) throws Exception {


        Map<NamedItem, Integer> map = new HashMap<>();

        List<String> idList = DaoFactory.getFriendDAO().getFriendIdListSpecificVersion(snapshotId, version);

        //check root feed
        List<ISharedComments> feedComments = DaoFactory.getFeedDAO().getAllCommentsInSnapshotSpecificVersion(snapshotId, version);
        for (ISharedComments feedComment : feedComments) {
            map = this.countCommentsToMap(feedComment, map, idList);
        }

        //check root home
        List<ISharedComments> homeComments = DaoFactory.getHomeDAO().getAllCommentsInSnapshotSpecificVersion(snapshotId, version);
        for (ISharedComments homeComment : homeComments) {
            map = this.countCommentsToMap(homeComment, map, idList);
        }
        
        //check links
        List<ISharedComments> linkComments = DaoFactory.getLinkDAO().getAllCommentsInSnapshotSpecificVersion(snapshotId, version);
        for (ISharedComments linkComment : linkComments) {
            map = this.countCommentsToMap(linkComment, map, idList);
        }
        
        //check notes
        List<ISharedComments> noteComments = DaoFactory.getNoteDAO().getAllCommentsInSnapshotSpecificVersion(snapshotId, version);
        for (ISharedComments noteComment : noteComments) {
            map = this.countCommentsToMap(noteComment, map, idList);
        }
        
        //check photos
        List<ISharedComments> photoComments = DaoFactory.getPhotoDAO().getAllCommentsInSnapshotSpecificVersion(snapshotId, version);
        for (ISharedComments photoComment : photoComments) {
            map = this.countCommentsToMap(photoComment, map, idList);
        }
        
        //check posts
        List<ISharedComments> postComments = DaoFactory.getPostDAO().getAllCommentsInSnapshotSpecificVersion(snapshotId, version);
        for (ISharedComments postComment : postComments) {
            map = this.countCommentsToMap(postComment, map, idList);
        }       

        return map;
    }
}
