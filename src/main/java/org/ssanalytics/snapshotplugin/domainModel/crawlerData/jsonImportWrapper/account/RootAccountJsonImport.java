package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.account.IRootAccount;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.feed.IFeed;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.inbox.IInbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.outbox.IOutbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.feed.FeedJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.friend.FriendJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.inbox.InboxJsonImport;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.outbox.OutboxJsonImport;

public class RootAccountJsonImport extends AccountJsonImport implements IRootAccount {

	public RootAccountJsonImport(JSONObject job) {
		super(job);
	}

	public List<IOutbox> getOutboxList() {
		
		JSONArray jar = getJar("outbox");
		
		List<IOutbox> list = new ArrayList<IOutbox>();
		
		for(Object o : jar){
			list.add(new OutboxJsonImport((JSONObject) o));
		}
		
		return list;
	}

	public List<IInbox> getInboxList() {
		
		JSONArray jar = getJar("inbox");
		
		List<IInbox> list = new ArrayList<IInbox>();
		
		for(Object o : jar){
			list.add(new InboxJsonImport((JSONObject) o));
		}
		
		return list;
	}

	public List<IFriend> getFriendList() {

		JSONArray jar = getJar("friends");
		
		List<IFriend> list = new ArrayList<IFriend>();
		
		for(Object o : jar){
			list.add(new FriendJsonImport((JSONObject) o));
		}
		
		return list;
	}

	public List<IFeed> getFeedList() {
		
		JSONArray jar = getJar("feed");
		
		List<IFeed> list = new ArrayList<IFeed>();
		
		for(Object o : jar){
			list.add(new FeedJsonImport((JSONObject) o));
		}
		
		return list;
	}

	public Map<String, List<String>> getFriendIsFriend() {
		
		JSONObject fif = getJob("friendisfriend");
		
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list;
		
		for (Object s : fif.keySet()){
			
			JSONArray jar = (JSONArray) fif.get(s.toString());
			
			list = new ArrayList<String>();	
			
			for (Object p : jar){
				list.add(p.toString());
			}
			
			map.put(s.toString(), list);					
		}
		
		return map;		
	}

}
