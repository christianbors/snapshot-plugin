package org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.account;

import java.util.List;
import java.util.Map;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.feed.IFeed;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.inbox.IInbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.outbox.IOutbox;

public interface IRootAccount extends IAccount {

    public List<IOutbox> getOutboxList();

    public List<IInbox> getInboxList();

    public List<IFriend> getFriendList();

    public List<IFeed> getFeedList();

    public Map<String, List<String>> getFriendIsFriend();
}
