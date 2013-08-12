package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.AbstractSuperDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.IdProvider;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared.SharedActionSql;

public class SharedActionDAOSql extends AbstractSuperDAOSql{
		
	private static SharedActionDAOSql instance = null;

	protected SharedActionDAOSql() throws SQLException {
		super();
	}
	
    public static SharedActionDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new SharedActionDAOSql();
        }
        return instance;
    }
    
    
    public List<ISharedAction> getSharedActionList(long papaId, String papaName) throws SQLException {
        PreparedStatement pst = this.prepareStatement("SELECT name, link FROM shared_action WHERE papa_id = ? AND papa_name = ?");
        this.setLongOrNull(1, papaId, pst);
        this.setStringOrNull(2, papaName, pst);
        
        ResultSet rs = pst.executeQuery();
        
        List<ISharedAction> actionList = new LinkedList<>();
        
        while(rs.next()){
            actionList.add(new SharedActionSql(rs.getString("name"), rs.getString("link")));
        }
        
        return actionList;
    }
    
    public void saveSharedAction(List<ISharedAction> actionList, long papaId, String papaName) throws SQLException{
    	
    	if(actionList != null){      		
    		PreparedStatement pst = this.prepareStatement("INSERT INTO shared_action (id, papa_id, papa_name, name, link) VALUES (?, ?, ?, ?, ?)");
    		pst.clearBatch();
    		this.setLongOrNull(2, papaId, pst);
    		this.setStringOrNull(3, papaName, pst);
    		for(ISharedAction action : actionList){
    			             this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
    			this.setStringOrNull(4, action.getName(), pst);
    			this.setStringOrNull(5, action.getLink(), pst);
    			
    			pst.addBatch();
    		}
    		pst.executeBatch();
    	}
    }

}
