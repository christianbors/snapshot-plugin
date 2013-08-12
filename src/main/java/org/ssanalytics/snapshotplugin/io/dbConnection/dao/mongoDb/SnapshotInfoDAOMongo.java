package org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.ISnapshotInfoDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.snapshotInfo.SnapshotInfoJsonMongo;

public class SnapshotInfoDAOMongo extends AbstractSuperDAOMongo implements ISnapshotInfoDAO {

    private final String collection_name = "snapshotInfo";
    
    private SnapshotInfoDAOMongo() throws UnknownHostException, MongoException {
        super();
    }
    private static SnapshotInfoDAOMongo instance = null;

    public static SnapshotInfoDAOMongo getInstance() throws UnknownHostException, MongoException {
        if (instance == null) {
            instance = new SnapshotInfoDAOMongo();
        }
        return instance;
    }

    @Override
    public List<ISnapshotInfo> getSnapshotInfoList() throws UnknownHostException, MongoException {

        DB db = this.openDatabase();
        DBCollection snapCollection = db.getCollection(this.collection_name);
        DBCursor dbCursor = snapCollection.find();

        List<ISnapshotInfo> returnList = new ArrayList<ISnapshotInfo>();

        while (dbCursor.hasNext()) {
            returnList.add(new SnapshotInfoJsonMongo((DBObject) dbCursor.next()));
        }
        return returnList;
    }

    @Override
    public String findHighestVersion(String snapshotinfo) throws MongoException, UnknownHostException {
        return this.findHighestVersionForSnapshot(snapshotinfo);
    }

    @Override
    public String getRootAccountFbId(String snapshotinfo, String version) throws MongoException, UnknownHostException {
        BasicDBObject params = new BasicDBObject();
        params.append("value", snapshotinfo);
        
        DBCursor dbc = this.findInCollection(this.collection_name, params);
        
        while(dbc.hasNext()){
            DBObject dbo = dbc.next();
            
            String s = dbo.get("value").toString();
        
            dbo = (DBObject) dbo.get(s);
            
            if(dbo.get("version").toString().equals(version))
                return dbo.get("root").toString();
            
        }
        
        return null;
    }

    @Override
    public Date getFirstDateForEvidenceDomainsInSnapshotLatestVersion(String snapshotinfo) throws MongoException, UnknownHostException {
        return this.getFirstDateForEvidenceDomainsInSnapshotSpecificVersion(snapshotinfo, this.findHighestVersion(snapshotinfo));
    }

    @Override
    public Date getFirstDateForEvidenceDomainsInSnapshotSpecificVersion(String snapshotinfo, String version) throws MongoException, UnknownHostException {
        
        Long minDate = System.currentTimeMillis();
        Long temp = null;
        
        //check activity
        temp = CategoryItemDAOMongo.getInstance().getFirstDate(snapshotinfo, version, "activity");
        if(temp != null)
            if(temp < minDate)
                minDate = temp;
        
        //check interest
        temp = CategoryItemDAOMongo.getInstance().getFirstDate(snapshotinfo, version, "interest");
        if(temp != null)
            if(temp < minDate)
                minDate = temp;
        
        //check book
        temp = CategoryItemDAOMongo.getInstance().getFirstDate(snapshotinfo, version, "book");
        if(temp != null)
            if(temp < minDate)
                minDate = temp;
        
        //check likes
        temp = CategoryItemDAOMongo.getInstance().getFirstDate(snapshotinfo, version, "fb_like");
        if(temp != null)
            if(temp < minDate)
                minDate = temp;
        
        //check movie
        temp = CategoryItemDAOMongo.getInstance().getFirstDate(snapshotinfo, version, "movie");
        if(temp != null)
            if(temp < minDate)
                minDate = temp;
        
        //check music
        temp = CategoryItemDAOMongo.getInstance().getFirstDate(snapshotinfo, version, "music");
        if(temp != null)
            if(temp < minDate)
                minDate = temp;
        
        //check television
        temp = CategoryItemDAOMongo.getInstance().getFirstDate(snapshotinfo, version, "television");
        if(temp != null)
            if(temp < minDate)
                minDate = temp;
        
        
        return new Date(minDate);
    }

    @Override
    public Date getLastDateForEvidenceDomainsInSnapshotLatestVersion(String snapshotinfo) throws Exception {
        return this.getLastDateForEvidenceDomainsInSnapshotSpecificVersion(snapshotinfo, this.findHighestVersionForSnapshot(snapshotinfo));
    }

    @Override
    public Date getLastDateForEvidenceDomainsInSnapshotSpecificVersion(String snapshotinfo, String version) throws Exception {
        
        Long maxDate = 0l;
        Long temp = null;
        
        //check activity
        temp = CategoryItemDAOMongo.getInstance().getFirstDate(snapshotinfo, version, "activity");
        if(temp != null)
            if(temp > maxDate)
                maxDate = temp;
        
        //check interest
        temp = CategoryItemDAOMongo.getInstance().getFirstDate(snapshotinfo, version, "interest");
        if(temp != null)
            if(temp > maxDate)
                maxDate = temp;
        
        //check book
        temp = CategoryItemDAOMongo.getInstance().getFirstDate(snapshotinfo, version, "book");
        if(temp != null)
            if(temp > maxDate)
                maxDate = temp;
        
        //check likes
        temp = CategoryItemDAOMongo.getInstance().getFirstDate(snapshotinfo, version, "fb_like");
        if(temp != null)
            if(temp > maxDate)
                maxDate = temp;
        
        //check movie
        temp = CategoryItemDAOMongo.getInstance().getFirstDate(snapshotinfo, version, "movie");
        if(temp != null)
            if(temp > maxDate)
                maxDate = temp;
        
        //check music
        temp = CategoryItemDAOMongo.getInstance().getFirstDate(snapshotinfo, version, "music");
        if(temp != null)
            if(temp > maxDate)
                maxDate = temp;
        
        //check television
        temp = CategoryItemDAOMongo.getInstance().getFirstDate(snapshotinfo, version, "television");
        if(temp != null)
            if(temp > maxDate)
                maxDate = temp;
        
        
        return new Date(maxDate);  
    }
}
