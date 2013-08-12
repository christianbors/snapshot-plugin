package org.ssanalytics.snapshotplugin.ui.standalone.controller;

import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;

public class SnapshotController {
	
	public enum ProfileVisualization {
		EVENT {
                    @Override
			public Visualization toVisualization() {
				return Visualization.EVENT;
			}
		},
		TESTPROFILE {
                    @Override
			public Visualization toVisualization() {
				return Visualization.TESTPROFILE;
			}
		};
		public Visualization toVisualization() {
			return this.toVisualization();
		}
		
	}
	
	public enum SnapshotVisualization {
		EVENT {
                    @Override
			public Visualization toVisualization() {
				return Visualization.EVENT;
			}
		}, 
		FRIENDISFRIEND {
                    @Override
			public Visualization toVisualization() {
				return Visualization.FRIENDISFRIEND;
			}
		};
		public Visualization toVisualization() {
			return this.toVisualization();
		}
		
	}
	
	public enum Visualization {
		EVENT {
                    @Override
			public String toString() {
				return "Event";
			}
		}, 
		FRIENDISFRIEND {
                    @Override
			public String toString() {
				return "Friend is Friend";
			}
		}, 
		TESTPROFILE {
                    @Override
			public String toString() {
				return "TESTPROFILE";
			}
		};
	}
	
	
	public enum SourceOption {
		SNAPSHOT {
                    @Override
			public String toString() {
				return "Snapshots";
			}
		},
		PROFILE {
                    @Override
			public String toString() {
				return "Profiles";
			}
		},
		BOTH {
                    @Override
			public String toString() {
				return "Snapshots and Profiles";
			}
		};
	}
	
	public List<Visualization> getVisOptions(SourceOption srcOption){
		List<Visualization> returnList = new LinkedList<>();
		
		switch(srcOption){
		case PROFILE:
			for(ProfileVisualization vis : ProfileVisualization.values()) {
				returnList.add(vis.toVisualization());
			}
			break;
//		case BOTH:
//			returnList.add("TESTBOTH");
//			break;
		case SNAPSHOT:
			for(SnapshotVisualization vis : SnapshotVisualization.values()) {
				returnList.add(vis.toVisualization());
			}
			break;
		default:
			break;		
		}
		
		return returnList;
		
	}

	public List<ISnapshotInfo> getSnapshotList() throws UnknownHostException, MongoException, SQLException, Exception {
		return DaoFactory.getSnapshotInfoDAO().getSnapshotInfoList();
	}
	
	public List<IProfile> getProfileList(ISnapshotInfo snapshot) throws UnknownHostException, MongoException, SQLException, Exception  {
		return DaoFactory.getProfileDAO().getProfileListForSnapshotLatestVersion(snapshot.getValue());
	}
	
	/*
	 * get Visualization type selection - Account - Snapshot - etc? change
	 * available combo boxes etc. to what is available
	 */

	/*
	 * change model -> set graph view to selected (FriendIsFriend, EventsGraph,
	 * etc.) notify update of view
	 */

}
