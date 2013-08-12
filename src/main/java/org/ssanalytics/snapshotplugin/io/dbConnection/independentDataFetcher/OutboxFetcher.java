/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sharedHelpers.WeekDayHelper;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.outbox.IOutbox;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.post.IPost;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedCommentData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;

/**
 *
 * @author chw
 */
public class OutboxFetcher {
    
        private OutboxFetcher() {
    }
    private static OutboxFetcher instance = null;

    public static OutboxFetcher getInstance() {
        if (instance == null) {
            instance = new OutboxFetcher();
        }
        return instance;
    }
    
    public float getAverageMessagesSentPerWeekForRootOfSnapshotLatestVersion(String snapshot) throws Exception {
        
        return this.getAverageMessagesSentPerWeekForRootOfSnapshotSpecificVersion(snapshot, DaoFactory.getSnapshotInfoDAO().findHighestVersion(snapshot));
    }
    
    
    
    public float getAverageMessagesSentPerWeekForRootOfSnapshotSpecificVersion(String snapshot, String version) throws Exception{
        
        List<IOutbox> outboxList = DaoFactory.getOutboxDAO().getOutboxListForRootAccountOfSnapshotSpecificVersion(snapshot, version);
        String root_fb_id = DaoFactory.getSnapshotInfoDAO().getRootAccountFbId(snapshot, version);
        
        if(root_fb_id == null)
            throw new Exception("Did not find the account id for snapshot root!\nSnapshot: " + snapshot + " version: " + version);
            
        
        long sentCount = 0;
        
        long minTime = System.currentTimeMillis();
        long maxTime = 0;
        
        for(IOutbox o : outboxList){
            
            ISharedComments c = o.getComments();
            if(c != null){
                List<ISharedCommentData> cdl = c.getDataList();
                if(cdl != null){
                    for(ISharedCommentData cd : cdl){
                        
                        ISharedFrom from = cd.getFrom();
                        if(from == null)
                            continue;
                        
                        if(root_fb_id.equals(from.getId())){
                            ++ sentCount;
                            if(cd.getCreatedTime() < minTime)
                                minTime = cd.getCreatedTime();
                            if(cd.getCreatedTime() > maxTime)
                                maxTime = cd.getCreatedTime();                            
                        }
                    }
                }
            }            
        }
        
        long span = maxTime - minTime;

        float milisecondsPerWeek = 1000 * 60 * 60 * 24 * 7;
        float numberOfWeeks = span / milisecondsPerWeek;
        
        float messagesPerWeek = sentCount / numberOfWeeks;
        
        return messagesPerWeek;        
    }
    
    
    
    public Map<Date, Integer> getOutboxCountPerWeekForSnapshotRootLatestVersion(String snapshot) throws Exception {
        return this.getOutboxCountPerWeekForSnapshotRootSpecificVersion(snapshot, DaoFactory.getSnapshotInfoDAO().findHighestVersion(snapshot));
    }
    
    public Map<Date, Integer> getOutboxCountPerWeekForSnapshotRootSpecificVersion(String snapshot, String version) throws Exception {
    
        
    
           List<IOutbox> outboxList = DaoFactory.getOutboxDAO().getOutboxListForRootAccountOfSnapshotSpecificVersion(snapshot, version);
        
        Map<Date, Integer> retMap = new HashMap<>();
        
        for(IOutbox outbox : outboxList){
            
            Date dt = WeekDayHelper.getInstance().getLastSunday(outbox.getUpdated_time());
            if(dt == null)
                continue;
            Integer i = 1;
            if(retMap.containsKey(dt))
                i = 1 + retMap.get(dt);
            
            retMap.put(dt, i);            
        }
        
        return retMap;
    }
    
}
