package org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract;

import java.util.List;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.event.IEvent;

public interface IEventDAO {

    public List<IEvent> getEventListForProfileAndSnapshotSpecificVersion(String snapshotInfoname, String profile_fb_id, String version) throws Exception;

    public List<IEvent> getEventListForProfileAndSnapshotLatestVersion(String snapshotInfoname, String profile_fb_id) throws Exception;

    public List<IEvent> getEventListForSnapshotLatestVersion(String snapshotInfoName) throws Exception;

    public List<IEvent> getEventListForSnapshotSpecificVersion(String snapshotInfoName, String version) throws Exception;
}
