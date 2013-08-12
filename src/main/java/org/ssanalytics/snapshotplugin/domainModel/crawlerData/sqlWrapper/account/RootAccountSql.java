package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.account;

import java.util.List;
import java.util.Map;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.account.IRootAccount;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.activity.IActivity;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.book.IBook;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.event.IEvent;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.feed.IFeed;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.group.IGroup;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.home.IHome;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.inbox.IInbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.interest.IInterest;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.likes.ILike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.link.ILink;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.movie.IMovie;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.music.IMusic;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.note.INote;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.outbox.IOutbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhoto;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.post.IPost;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.status.IStatus;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged.ITagged;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.television.ITelevision;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.video.IVideo;

public class RootAccountSql extends AccountSql implements IRootAccount{

	
	private List<IOutbox> outboxList;
	private List<IInbox> inboxList;
	private List<IFriend> friendList;
	private List<IFeed> feedList;

	private Map<String, List<String>> friendIsFriend;
	
	public RootAccountSql(List<IMusic> musicList,
			List<IBook> bookList, List<IHome> home,
			List<ITelevision> televisionList, List<IInterest> interests,
			List<IEvent> eventList, List<IVideo> videoList,
			List<IMovie> movieList, List<IPost> postList, List<ILink> linkList,
			List<IStatus> statusList, List<IPhoto> photoList,
			List<ILike> likeList, List<IActivity> activityList,
			List<ITagged> taggedList, List<INote> noteList,
			List<IGroup> groupList, IProfile profile, List<IOutbox> outboxList, List<IInbox> inboxList, List<IFriend> friendList, List<IFeed> feedList, Map<String, List<String>> friendIsFriend) {
		super(musicList, bookList, home, televisionList, interests, eventList,
				videoList, movieList, postList, linkList, statusList, photoList,
				likeList, activityList, taggedList, noteList, groupList, profile);
	
		this.outboxList = outboxList;
		this.inboxList = inboxList;
		this.friendIsFriend = friendIsFriend;
		this.friendList = friendList;
		this.feedList = feedList;
	}

	public List<IOutbox> getOutboxList() {
		return outboxList;
	}

	public List<IInbox> getInboxList() {
		return inboxList;
	}

	public List<IFriend> getFriendList() {
		return friendList;
	}

	public List<IFeed> getFeedList() {
		return feedList;
	}

	public Map<String, List<String>> getFriendIsFriend() {
		return friendIsFriend;
	}

}
