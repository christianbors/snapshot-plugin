package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.account;

import java.util.List;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.activity.IActivity;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.book.IBook;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.event.IEvent;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.group.IGroup;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.home.IHome;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.interest.IInterest;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.likes.ILike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.link.ILink;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.movie.IMovie;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.music.IMusic;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.note.INote;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhoto;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.post.IPost;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.status.IStatus;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IBaseDomain;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged.ITagged;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.television.ITelevision;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideo;

public interface IAccount extends IBaseDomain {

    public List<IMusic> getMusicList();

    public List<IBook> getBookList();

    public List<IHome> getHomeList();

    public List<ITelevision> getTelevisionList();

    public List<IInterest> getInterests();

    public List<IEvent> getEventList();

    public List<IVideo> getVideoList();

    public List<IMovie> getMovieList();

    public List<IPost> getPostList();

    public List<ILink> getLinkList();

    public List<IStatus> getStatusList();

    public List<IPhoto> getPhotoList();

    public List<ILike> getLikeList();

    public List<IActivity> getActivityList();

    public List<ITagged> getTaggedList();

    public List<INote> getNoteList();

    public List<IGroup> getGroupList();

    public IProfile getProfile();
}
