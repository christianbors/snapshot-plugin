package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

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
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.activity.ActivityJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.book.BookJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.event.EventJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.group.GroupJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.home.HomeJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.interest.InterestJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.likes.LikeJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.link.LinkJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.movie.MovieJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.music.MusicJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.note.NoteJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.photo.PhotoJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.post.PostJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.profile.ProfileJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.status.StatusJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.tagged.TaggedJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.television.TelevisionJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.video.VideoJsonMongo;

public class AccountJsonMongo implements IAccount {
	
	public enum AccountComponents {
		music, books, home, television, interests, events, videos, posts, movies,
		links, statuses, photos, likes, activities, tagged, notes, groups, profile
	} 
	
	public List<String> getAccountComponents(){
		List<String> components= new ArrayList<String>();
		
		for (AccountComponents ac : AccountComponents.values())
			components.add(ac.toString());
		
		return components;
	}
	
	
	public int putAll(Map<String, Object> values){
		
		int count = 0;
		for (String s : values.keySet()){
			if(this.put(s, values.get(s))) {
				count++;				
			}
		}
		
		return count;
	}
	
	public boolean put(String key, Object value){
		
		switch(key){
			case "music": this.setMusicList((BasicDBList) value); return true;
			case "books": this.setBookList((BasicDBList) value); return true;
			case "home": this.setHomeList((BasicDBList) value); return true;
			case "television": this.setTelevisionList((BasicDBList) value); return true;
			case "interests": this.setInterestList((BasicDBList) value); return true;
			case "events": this.setEventList((BasicDBList) value); return true;
			case "videos": this.setVideoList((BasicDBList) value); return true;
			case "posts": this.setPostList((BasicDBList) value); return true;
			case "movies": this.setMovieList((BasicDBList) value); return true;
			case "links": this.setLinkList((BasicDBList) value); return true;
			case "statuses": this.setStatusList((BasicDBList) value); return true;
			case "photos": this.setPhotoList((BasicDBList) value); return true;
			case "likes": this.setLikeList((BasicDBList) value); return true;
			case "activities": this.setActivityList((BasicDBList) value); return true;
			case "tagged": this.setTaggedList((BasicDBList) value); return true;
			case "notes": this.setNoteList((BasicDBList) value); return true;
			case "groups": this.setGroupList((BasicDBList) value); return true;
			case "profile": this.setProfile((BasicDBObject) value); return true;
			default: return false;
		}
		
		
	}
	
	private String snapshotId;
	private String accountId;
	private String version;
	private BasicDBObject profile;
	private BasicDBList musicList;
	private BasicDBList bookList;
	private BasicDBList homeList;
	private BasicDBList televisionList;
	private BasicDBList activityList;
	private BasicDBList eventList;
	private BasicDBList groupList;
	private BasicDBList interestList;
	private BasicDBList videoList;
	private BasicDBList movieList;
	private BasicDBList postList;
	private BasicDBList linkList;
	private BasicDBList photoList;
	private BasicDBList statusList;
	private BasicDBList likeList;
	private BasicDBList taggedList;
	private BasicDBList noteList;

	
	public Long getTimestamp() {
		return null;
	}

	public String getChecksum() {
		return null;
	}


	public List<IHome> getHomeList() {	
		
		
		List<IHome> list = new ArrayList<IHome>();
		
		for (Object o : homeList){
			list.add(new HomeJsonMongo((DBObject) o));
		}
		
		return list;
	}

	public List<ITelevision> getTelevisionList() {
		
		List<ITelevision> list = new ArrayList<ITelevision>();
		
		for (Object o : televisionList){
			list.add(new TelevisionJsonMongo((DBObject) o));
		}
		
		return list;
	}

	public List<IInterest> getInterests() {
		List<IInterest> list = new ArrayList<IInterest>();
		
		for (Object o : interestList){
			list.add(new InterestJsonMongo((DBObject) o));
		}
		
		return list;
	}

	public List<IEvent> getEventList() {
		List<IEvent> list = new ArrayList<IEvent>();
		
		for (Object o : eventList){
			list.add(new EventJsonMongo((DBObject) o));
		}
		
		return list;
	}

	public List<IVideo> getVideoList() {
		List<IVideo> list = new ArrayList<IVideo>();
		
		for (Object o : videoList){
			list.add(new VideoJsonMongo((DBObject) o));
		}
		
		return list;
	}

	public List<IMovie> getMovieList() {
		List<IMovie> list = new ArrayList<IMovie>();
		
		for (Object o : movieList){
			list.add(new MovieJsonMongo((DBObject) o));
		}
		
		return list;
	}

	public List<IPost> getPostList() {
		List<IPost> list = new ArrayList<IPost>();
		
		for (Object o : postList){
			list.add(new PostJsonMongo((DBObject) o));
		}
		
		return list;
	}

	public List<ILink> getLinkList() {
		
		List<ILink> list = new ArrayList<ILink>();
		
		for (Object o : linkList){
			list.add(new LinkJsonMongo((DBObject) o));
		}
		
		return list;
	}

	public List<IStatus> getStatusList() {
		
		List<IStatus> list = new ArrayList<IStatus>();
		
		for (Object o : statusList){
			list.add(new StatusJsonMongo((DBObject) o));
		}
		
		return list;
	}

	public List<IPhoto> getPhotoList() {
		List<IPhoto> list = new ArrayList<IPhoto>();
		
		for (Object o : photoList){
			list.add(new PhotoJsonMongo((DBObject) o));
		}
		
		return list;
	}

	public List<ILike> getLikeList() {
		List<ILike> list = new ArrayList<ILike>();
		
		for (Object o : likeList){
			list.add(new LikeJsonMongo((DBObject) o));
		}
		
		return list;
	}

	public List<IActivity> getActivityList() {
		List<IActivity> list = new ArrayList<IActivity>();
		
		for (Object o : activityList){
			list.add(new ActivityJsonMongo((DBObject) o));
		}
		
		return list;
	}

	public List<ITagged> getTaggedList() {
		List<ITagged> list = new ArrayList<ITagged>();
		
		for (Object o : taggedList){
			list.add(new TaggedJsonMongo((DBObject) o));
		}
		
		return list;
	}

	public List<INote> getNoteList() {
		List<INote> list = new ArrayList<INote>();
		
		for (Object o : this.noteList){
			list.add(new NoteJsonMongo((DBObject) o));
		}
		
		return list;
	}

	public List<IGroup> getGroupList() {
		List<IGroup> list = new ArrayList<IGroup>();
		
		for (Object o : groupList){
			list.add(new GroupJsonMongo((DBObject) o));
		}
		
		return list;
	}

	public IProfile getProfile() {
		return new ProfileJsonMongo(this.profile);
	}

	
	public String getSnapshotId() {
		return this.snapshotId;
	}
	

	public String getAccountId() {
		return this.accountId;
	}
	
	public String getVersion(){
		return this.version;
	}
	

	public List<IMusic> getMusicList() {
		List<IMusic> list = new ArrayList<IMusic>();
		
		for (Object o : musicList){
			list.add(new MusicJsonMongo((DBObject) o));
		}
		
		return list;
	}

	public List<IBook> getBookList() {
		List<IBook> list = new ArrayList<IBook>();
		
		for (Object o : bookList){
			list.add(new BookJsonMongo((DBObject) o));
		}
		
		return list;
	}

	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}
	
	public void setVersion(String version){
		this.version = version;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setProfile(BasicDBObject profile) {
		this.profile = profile;
	}

	public void setMusicList(BasicDBList musicList) {
		this.musicList = musicList;
	}

	public void setBookList(BasicDBList bookList) {
		this.bookList = bookList;
	}

	public void setHomeList(BasicDBList homeList) {
		this.homeList = homeList;
	}

	public void setTelevisionList(BasicDBList televisionList) {
		this.televisionList = televisionList;
	}

	public void setActivityList(BasicDBList activityList) {
		this.activityList = activityList;
	}

	public void setEventList(BasicDBList eventList) {
		this.eventList = eventList;
	}

	public void setGroupList(BasicDBList groupList) {
		this.groupList = groupList;
	}

	public void setInterestList(BasicDBList interestList) {
		this.interestList = interestList;
	}

	public void setVideoList(BasicDBList videoList) {
		this.videoList = videoList;
	}

	public void setMovieList(BasicDBList movieList) {
		this.movieList = movieList;
	}

	public void setPostList(BasicDBList postList) {
		this.postList = postList;
	}

	public void setLinkList(BasicDBList linkList) {
		this.linkList = linkList;
	}

	public void setPhotoList(BasicDBList photoList) {
		this.photoList = photoList;
	}

	public void setStatusList(BasicDBList statusList) {
		this.statusList = statusList;
	}

	public void setLikeList(BasicDBList likeList) {
		this.likeList = likeList;
	}

	public void setTaggedList(BasicDBList taggedList) {
		this.taggedList = taggedList;
	}

	public void setNoteList(BasicDBList noteList) {
		this.noteList = noteList;
	}

//	public void SetAccountComponent(AccountComponents comp, BasicDBList value) {
//		if(comp.equals(RootAccountComponents.outbox))
//			setOutboxList(value);
//		else if(comp.equals(RootAccountComponents.inbox))
//			setInboxList(value);
//	}
}
