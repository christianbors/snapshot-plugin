package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.account.IRootAccount;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.feed.IFeed;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.inbox.IInbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.outbox.IOutbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.feed.FeedJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.friend.FriendJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.inbox.InboxJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.outbox.OutboxJsonMongo;

public class RootAccountJsonMongo extends AccountJsonMongo implements IRootAccount{

	
	public enum RootAccountComponents {
		friendisfriend, inbox, outbox, friends, feeds
	}
	
	public List<String> getAccountComponents(){
		List<String> rootComponents = new ArrayList<String>();
		
		try{
		rootComponents.addAll(super.getAccountComponents());
		} catch(Exception ex){
			ex.printStackTrace();
		}
		
		for(RootAccountComponents rac : RootAccountComponents.values())
			rootComponents.add(rac.toString());
		
		return rootComponents;
	}	
	
	public boolean put(String key, Object value){
		
		switch(key){
			case "friendisfriend": this.setFriendIsFriend((BasicDBObject) value); return true;
			case "inbox": this.setInboxList((BasicDBList) value); return true;
			case "outbox": this.setOutboxList((BasicDBList) value); return true;
			case "friends": this.setFriendList((BasicDBList) value); return true;
			case "feeds": this.setFeedList((BasicDBList) value); return true;
			default: return super.put(key, value);
		}		
	}
	
	private BasicDBList outboxList;
	private BasicDBList inboxList;
	private BasicDBList friendList;
	private BasicDBList feedList;
	private DBObject friendIsFriend;
	
	public List<IOutbox> getOutboxList() {
		
		List<IOutbox> list = new ArrayList<IOutbox>();
		
		if(outboxList != null){		
			for (Object o : outboxList){
				list.add(new OutboxJsonMongo((DBObject) o));
			}
		}
		
		return list;
	}

	public List<IInbox> getInboxList() {
		
		List<IInbox> list = new ArrayList<IInbox>();
		
		for (Object o : inboxList){
			list.add(new InboxJsonMongo((DBObject) o));
		}
		
		return list;
	}

	public List<IFriend> getFriendList() {
		
		List<IFriend> list = new ArrayList<IFriend>();
		
		for (Object o : friendList){
			list.add(new FriendJsonMongo((DBObject) o));
		}
		
		return list;
	}

	public List<IFeed> getFeedList() {
		
		List<IFeed> list = new ArrayList<IFeed>();
		
		for (Object o : feedList){
			list.add(new FeedJsonMongo((DBObject) o));
		}
		
		return list;
	}

	public Map<String, List<String>> getFriendIsFriend() {
		Map<String, List<String>> returnMap = new HashMap<String, List<String>>();
		
		if(this.friendIsFriend == null)
			return returnMap;
		
		DBObject friendIsFriendData = (DBObject) this.friendIsFriend.get("data");
		
		for (String s : friendIsFriendData.keySet()){
			BasicDBList temp = ((BasicDBList) friendIsFriendData.get(s));
			List<String> returnList = new ArrayList<String>();
			for (Object o : temp){
				returnList.add(o.toString());
			}
			
			returnMap.put(s, returnList);
		}		
		
		return returnMap;
	}

	public void setOutboxList(BasicDBList outboxList) {
		this.outboxList = outboxList;
	}

	public void setInboxList(BasicDBList inboxList) {
		this.inboxList = inboxList;
	}

	public void setFriendList(BasicDBList friendList) {
		this.friendList = friendList;
	}

	public void setFeedList(BasicDBList feedList) {
		this.feedList = feedList;
	}

	public void setFriendIsFriend(DBObject friendIsFriend) {
		this.friendIsFriend = friendIsFriend;
	}
//
//	public void SetRootAccountComponent(RootAccountComponents comp, BasicDBList value) {
//		if(comp.equals(RootAccountComponents.outbox))
//			setOutboxList(value);
//		else if(comp.equals(RootAccountComponents.inbox))
//			setInboxList(value);
//		else if(comp.equals(RootAccountComponents.friendList)) 
//			setFriendList(value);
//		else if(comp.equals(RootAccountComponents.feedList))
//			setFeedList(value);
//	}
}
