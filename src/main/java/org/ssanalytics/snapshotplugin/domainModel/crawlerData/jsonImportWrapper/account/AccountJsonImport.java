package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.account;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.activity.ActivityJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.book.BookJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.event.EventJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.group.GroupJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.home.HomeJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.interest.InterestJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.likes.LikeJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.link.LinkJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.movie.MovieJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.music.MusicJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.note.NoteJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.photo.PhotoJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.post.PostJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.profile.ProfileJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.status.StatusJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses.AbstractBaseDomainJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.tagged.TaggedJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.television.TelevisionJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.video.VideoJsonImport;

public class AccountJsonImport extends AbstractBaseDomainJsonImport implements IAccount {

	public AccountJsonImport(JSONObject job) {
		super(job);
	}

	public List<IMusic> getMusicList() {
		JSONArray jar = getJar("music");
		
		List<IMusic> list = new ArrayList<IMusic>();
		
		if (jar != null) {
			for (Object o : jar) {
				list.add(new MusicJsonImport((JSONObject) o));
			}
		}
		
		return list;
	}

	public List<IBook> getBookList() {
		JSONArray jar = getJar("books");
		
		List<IBook> list = new ArrayList<IBook>();
		
		if (jar != null) {
			for (Object o : jar) {
				list.add(new BookJsonImport((JSONObject) o));
			}
		}
		
		return list;
	}

	public List<IHome> getHomeList() {
		JSONArray jar = getJar("home");
		
		List<IHome> list = new ArrayList<IHome>();
		
		if(jar != null){		
			for(Object o : jar){
				list.add(new HomeJsonImport((JSONObject) o));
			}
		}
		
		return list;
	}

	public List<ITelevision> getTelevisionList() {
		JSONArray jar = getJar("television");
		
		List<ITelevision> list = new ArrayList<ITelevision>();
		
		if (jar != null) {
			for (Object o : jar) {
				list.add(new TelevisionJsonImport((JSONObject) o));
			}
		}
		
		return list;
	}

	public List<IInterest> getInterests() {
		JSONArray jar = getJar("interests");
		
		List<IInterest> list = new ArrayList<IInterest>();
		
		if (jar != null) {
			for (Object o : jar) {
				list.add(new InterestJsonImport((JSONObject) o));
			}
		}
		
		return list;
	}

    public List<IEvent> getEventList() {

        JSONArray jar = getJar("events");

        List<IEvent> list = new ArrayList<IEvent>();

        if (jar != null) {
            for (Object o : jar) {
                list.add(new EventJsonImport((JSONObject) o));
            }
        }

        return list;
    }

	public List<IVideo> getVideoList() {
		
		JSONArray jar = getJar("videos");
		
		List<IVideo> list = new ArrayList<IVideo>();
		
		if (jar != null) {
			for (Object o : jar) {
				list.add(new VideoJsonImport((JSONObject) o));
			}
		}
		
		return list;
	}

	public List<IMovie> getMovieList() {
		
		JSONArray jar = getJar("movies");
		
		List<IMovie> list = new ArrayList<IMovie>();
		
		if(jar != null)
			for(Object o : jar){
				if(o != null)
					list.add(new MovieJsonImport((JSONObject) o));
			}
			
		
		return list;
	}

	public List<IPost> getPostList() {
		
		JSONArray jar = getJar("posts");
		
		List<IPost> list = new ArrayList<IPost>();
		
		if(jar!=null)
			for(Object o : jar){
				list.add(new PostJsonImport((JSONObject) o));
			}
		
		return list;
	}

	public List<ILink> getLinkList() {
		
		JSONArray jar = getJar("links");
		
		List<ILink> list = new ArrayList<ILink>();
		
		if (jar != null) {
			for (Object o : jar) {
				list.add(new LinkJsonImport((JSONObject) o));
			}
		}
		
		return list;
	}

	public List<IStatus> getStatusList() {
		
		JSONArray jar = getJar("statuses");
		
		List<IStatus> list = new ArrayList<IStatus>();
		
		if (jar != null) {
			for (Object o : jar) {
				list.add(new StatusJsonImport((JSONObject) o));
			}
		}
		
		return list;
	}

	public List<IPhoto> getPhotoList() {
		
		JSONArray jar = getJar("photos");
		
		List<IPhoto> list = new ArrayList<IPhoto>();
		
		if (jar != null) {
			for (Object o : jar) {
				list.add(new PhotoJsonImport((JSONObject) o));
			}
		}
		
		return list;
	}

	public List<ILike> getLikeList() {

		JSONArray jar = getJar("likes");
		
		List<ILike> list = new ArrayList<ILike>();
		
		if (jar != null) {
			for (Object o : jar) {
				list.add(new LikeJsonImport((JSONObject) o));
			}
		}
		
		return list;
	}

	public List<IActivity> getActivityList() {
		
		JSONArray jar = getJar("activities");
		
		List<IActivity> list = new ArrayList<IActivity>();
		
		if (jar != null) {
			for (Object o : jar) {
				list.add(new ActivityJsonImport((JSONObject) o));
			}
		}
		
		return list;
	}

	public List<ITagged> getTaggedList() {
		
		JSONArray jar = getJar("tagged");
		
		List<ITagged> list = new ArrayList<ITagged>();
		
		if (jar != null) {
			for (Object o : jar) {
				list.add(new TaggedJsonImport((JSONObject) o));
			}
		}
		
		return list;
	}

	public List<INote> getNoteList() {
		
		JSONArray jar = getJar("notes");
				
		List<INote> list = new ArrayList<INote>();
		
		if (jar != null) {
			for (Object o : jar) {
				list.add(new NoteJsonImport((JSONObject) o));
			}
		}
		
		return list;
	}

	public List<IGroup> getGroupList() {

		JSONArray jar = getJar("groups");
		
		List<IGroup> list = new ArrayList<IGroup>();
		
		if (jar != null) {
			for (Object o : jar) {
				list.add(new GroupJsonImport((JSONObject) o));
			}
		}
		
		return list;
	}

	public IProfile getProfile() {

		JSONObject temp = getJob("profile");
		return temp == null ? null : new ProfileJsonImport(temp);
	}

}
