package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.DbMode;

public class IdProvider extends AbstractSuperDAOSql {

    private static IdProvider instance = null;
    private static int BUFFER_SIZE = 1000;
    private static int temp_id = 0;

    public static void setNewBufferSize(int bufferSize) throws SQLException {
        BUFFER_SIZE = bufferSize;
        instance = new IdProvider();
    }

    protected IdProvider() throws SQLException {
        super();        
        this.idBuffer = this.getNextid(BUFFER_SIZE);

    }

    public static IdProvider getInstance() throws SQLException {
        if (instance == null) {
            instance = new IdProvider();
        }
        return instance;
    }
    private List<Long> idBuffer;
    private int idPosition;

    public long getNextId() throws SQLException {
        /*
        if(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() == DbMode.MYSQL){
            temp_id++;
            return temp_id;
        }*/

        
        if (this.idPosition == this.BUFFER_SIZE) {
            this.idBuffer = this.getNextid(this.BUFFER_SIZE);
            this.idPosition = 0;
        }

        return this.idBuffer.get(this.idPosition++);
        
        //        throw new SQLException("Could not get next ID for db mode " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode().toString());
    }
}
