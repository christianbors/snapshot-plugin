package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.superClasses;



import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import java.util.Date;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IBaseDomain;

public abstract class AbstractBaseDomainJsonMongo implements IBaseDomain {
	
	protected DBObject dbo;
	
	public AbstractBaseDomainJsonMongo(DBObject job)
	{
		this.dbo = job;
	}
        
        private long getTimeFromString(String s){
            try{
                Date d = new Date();
                String y = s.substring(0,4);
                Integer year = Integer.parseInt(s.substring(0, 4));
                Integer mon = Integer.parseInt(s.substring(5,7));
                Integer day = Integer.parseInt(s.substring(8,10));
                
                Integer hour = Integer.parseInt(s.substring(11,13));
                Integer min = Integer.parseInt(s.substring(14,16));
                Integer sec = Integer.parseInt(s.substring(17,19));
                
                d.setYear(year - 1900);
                d.setMonth(mon - 1);
                d.setDate(day);
                d.setHours(hour);
                d.setMinutes(min);
                d.setSeconds(sec);
                
                return d.getTime();
            }
            catch(Exception ex){
                return 0;
            }
        }
        
        protected long getTime(String key){
            return this.getTimeFromString(this.getString(key));
        }
        
        protected long getDataTime(String key){
            
            return this.getTimeFromString(this.getDataString(key));
        }
	
	protected Long getDataLong(String key){
		try{
			return new Long(((DBObject) this.dbo.get("data")).get(key).toString());
		} catch(Exception ex) {
			return null;
		}
	}
        
        protected Long getLong(String key){
		try{
			return new Long(this.dbo.get(key).toString());
		} catch(Exception ex) {
			return null;
		}
	}
	
	protected Double getDataDouble(String key){
		try{
			return new Double(((DBObject) this.dbo.get("data")).get(key).toString());
		} catch(Exception ex) {
			return null;
		}
	}
        
        protected Double getDouble(String key){
		try{
			return new Double( this.dbo.get(key).toString());
		} catch(Exception ex) {
			return null;
		}
	}
        
        protected Integer getInteger(String key){
		try{
			return new Integer( this.dbo.get(key).toString());
		} catch(Exception ex) {
			return null;
		}
	}
	
	protected Integer getDataInteger(String key){
		try{
			return new Integer(((DBObject) this.dbo.get("data")).get(key).toString());
		} catch(Exception ex) {
			return null;
		}
	}
		
	protected String getDataString(String key){
		
            try{
		if(this.dbo == null)
			return null;
		
		DBObject temp = (DBObject) this.dbo.get("data");
		
		if(temp == null)
			return null;
		
		return (String) temp.get(key);
            }catch(Exception ex){
               return null;
            }
            
	}
        
        protected String getString(String key){
            try{
                return this.dbo.get(key).toString();
            }
            catch(Exception ex){
                return null;
            }
        }
	
	protected DBObject getDataDbo(String key){
		return this.dbo == null ? null : (DBObject) ((DBObject) this.dbo.get("data")).get(key);
	}
        
        protected DBObject getDbo(String key){
		return this.dbo == null ? null : (DBObject) this.dbo.get(key);
	}
	
	protected BasicDBList getDataList(String key){
		
		try {
			return this.dbo == null ? new BasicDBList() : (BasicDBList) ((DBObject) this.dbo.get("data")).get(key);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new BasicDBList();
		}
	}
        
        protected BasicDBList getList(String key){
		
		try {
			return this.dbo == null ? new BasicDBList() : (BasicDBList) this.dbo.get(key);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new BasicDBList();
		}
	}

	public Long getTimestamp() {
		try{
			return new Long(this.dbo.get("timestamp").toString());
		} catch(Exception ex) {
			return null;
		}
	}

	public String getSnapshotId() {
		return this.dbo == null ? null : (String) this.dbo.get("snapshot");
	}

	public String getChecksum() {
		return this.dbo == null ? null : this.dbo.get("checksum").toString();
	}

	public String getAccountId() {
		return this.dbo == null ? null : this.dbo.get("accountId").toString();
	}
	
	public String getSnapshotVersion() {
		return this.dbo == null ? null : this.dbo.get("version").toString();
	}
}
