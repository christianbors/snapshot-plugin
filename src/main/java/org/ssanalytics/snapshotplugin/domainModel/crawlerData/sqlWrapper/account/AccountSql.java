package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.account;

import java.util.List;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.account.IAccount;
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
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged.ITagged;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.television.ITelevision;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractBaseDomainSql;

public class AccountSql extends AbstractBaseDomainSql implements IAccount{

	
	private List<IMusic> musicList;
	private List<IBook> bookList;
	private List<IHome> homeList;
	private List<ITelevision> televisionList;
	private List<IInterest> interests;
	private List<IEvent> eventList;
	private List<IVideo> videoList;
	private List<IMovie> movieList;
	private List<IPost> postList;
	private List<ILink> linkList;
	private List<IStatus> statusList;
	private List<IPhoto> photoList;
	private List<ILike> likeList;
	private List<IActivity> activityList;
	private List<ITagged> taggedList;
	private List<INote> noteList;
	private List<IGroup> groupList;
	private IProfile profile;
	
	public AccountSql(List<IMusic> musicList, List<IBook> bookList, List<IHome> homeList, List<ITelevision> televisionList, List<IInterest> interests, List<IEvent> eventList, List<IVideo> videoList, List<IMovie> movieList, List<IPost> postList, List<ILink> linkList, List<IStatus> statusList, List<IPhoto> photoList, List<ILike> likeList, List<IActivity> activityList, List<ITagged> taggedList, List<INote> noteList, List<IGroup> groupList, IProfile profile) {
		super();
		
		this.musicList = musicList;
		this.bookList = bookList;
		this.homeList = homeList;
		this.televisionList = televisionList;
		this.interests = interests;
		this.eventList = eventList;
		this.videoList = videoList;
		this.movieList = movieList;
		this.postList = postList;
		this.linkList = linkList;
		this.statusList = statusList;
		this.photoList = photoList;
		this.likeList = likeList;
		this.activityList = activityList;
		this.taggedList = taggedList;
		this.noteList = noteList;
		this.groupList = groupList;
		this.profile = profile;
	}

	public List<IMusic> getMusicList() {
		return musicList;
	}

	public List<IBook> getBookList() {
		return bookList;
	}

	public List<IHome> getHomeList() {
		return homeList;
	}

	public List<ITelevision> getTelevisionList() {
		return televisionList;
	}

	public List<IInterest> getInterests() {
		return interests;
	}

	public List<IEvent> getEventList() {
		return eventList;
	}

	public List<IVideo> getVideoList() {
		return videoList;
	}

	public List<IMovie> getMovieList() {
		return movieList;
	}

	public List<IPost> getPostList() {
		return postList;
	}

	public List<ILink> getLinkList() {
		return linkList;
	}

	public List<IStatus> getStatusList() {
		return statusList;
	}

	public List<IPhoto> getPhotoList() {
		return photoList;
	}

	public List<ILike> getLikeList() {
		return likeList;
	}

	public List<IActivity> getActivityList() {
		return activityList;
	}

	public List<ITagged> getTaggedList() {
		return taggedList;
	}

	public List<INote> getNoteList() {
		return noteList;
	}

	public List<IGroup> getGroupList() {
		return groupList;
	}

	public IProfile getProfile() {
		return profile;
	}

}
