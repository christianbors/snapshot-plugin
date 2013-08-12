package org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonImportWrapper.superClasses;



import java.util.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.IBaseDomain;

public abstract class AbstractBaseDomainJsonImport implements IBaseDomain {
	
	protected JSONObject job;
	protected String hash;
	protected String snapshotId;
	protected String accountId;
	protected String version;
	
	public AbstractBaseDomainJsonImport(JSONObject job)
	{
		this.job = job;
	}
	
        protected long getTime(String key){
            
        
            try{
                String s = this.getString(key);
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
        
        protected Double getDouble(String key){
		try{
			return new Double(this.job.get(key).toString());
		} catch(Exception ex) {
			return null;
		}
        }
        
	protected Long getLong(String key){
		try{
			return new Long(this.job.get(key).toString());
		} catch(Exception ex) {
			return null;
		}
	}
	
	protected Integer getInteger(String key){
		try{
			return new Integer(this.job.get(key).toString());
		} catch(Exception ex) {
			return null;
		}
	}
	
	protected String getString(String key){
            try{
		return (String) this.job.get(key);
            }catch (Exception ex){
                if(ConfigurationFileManager.getCurrentConfig().getOutputLevel() > 2)
                    System.out.println("Exception in AbstractBaseDomainJsonImport.getString()");

                return "";
            }
	}
	
	protected JSONObject getJob(String key){
            try{
		return (JSONObject) this.job.get(key);
            }catch(NullPointerException nex){
                return null;
            }
	}
	
	protected JSONArray getJar(String key){
		try{
			return (JSONArray) this.job.get(key);
		} catch(ClassCastException exception){
			//this should only happen, if f.e. the JSOAN Array "home" is "false" instead of an arary.
			if(ConfigurationFileManager.getCurrentConfig().getOutputLevel() > 2){
				System.out.println(exception.getMessage() + " (AbstractBaseDomainJsonImport.getJar())");
			}
			return null;
		}
	}

	public String getSnapshotId() {
		return this.snapshotId;
	}

	public String getChecksum() {
		return this.hash;
	}

	public String getAccountId() {
		return this.accountId;
	}
	
	public String getSnapshotVersion() {
		return this.version;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	
}
