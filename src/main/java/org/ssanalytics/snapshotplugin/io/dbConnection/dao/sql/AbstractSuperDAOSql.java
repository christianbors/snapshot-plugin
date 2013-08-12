package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.openide.util.Exceptions;
import org.openide.util.NotImplementedException;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.DbMode;

public abstract class AbstractSuperDAOSql {

    private Connection con;    

    public AbstractSuperDAOSql() throws SQLException {
        this.con = getNewConnection();
    }
    protected String table_name = null;

    private Connection getNewConnection() throws SQLException {

        if (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() == DbMode.POSTGRESQL) {
            try {

                Class.forName("org.postgresql.Driver");

            } catch (ClassNotFoundException e) {

                if (ConfigurationFileManager.getCurrentConfig().getOutputLevel() >= 2) {
                    System.out
                            .println("Bro, where is your PostgreSQL JDBC Driver? "
                            + "Include in your library path!");
                    e.printStackTrace();
                }
            }

            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://" + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getHost() + ":"
                    + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getPort() + "/"
                    + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbName(), ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getUser(),
                    ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getPwd());

            return connection;
        }
        
        if (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() == DbMode.MYSQL) {
            
            try {
                Class.forName("com.mysql.jdbc.Driver");

            } catch (ClassNotFoundException e) {

                if (ConfigurationFileManager.getCurrentConfig().getOutputLevel() >= 2) {
                    System.out
                            .println("Bro, where is your MySQL JDBC Driver? "
                            + "Include in your library path!");
                    e.printStackTrace();
                }
            }

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://" + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getHost() + ":"
                    + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getPort() + "/"
                    + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbName(), ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getUser(),
                    ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getPwd());

            return connection;
        }
        
        throw new SQLException("The DBMode " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() + " is not supported by the SQL Dao");
    }

    protected Long getSnapshotDbId(String snapshotname, String version) throws SQLException {

        PreparedStatement pst = this.prepareStatement("SELECT id FROM snapshotinfo WHERE snapshotname = ? AND version = ?");
        this.setStringOrNull(1, snapshotname, pst);
        this.setStringOrNull(2, version, pst);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return rs.getLong("id");
        } else {
            return null;
        }
    }

    protected Long getRootAccountDbId(String snapshotname, String version) throws SQLException {

        PreparedStatement pst = this.prepareStatement("SELECT root_db_id FROM snapshotinfo WHERE snapshotname = ? AND version = ?");
        this.setStringOrNull(1, snapshotname, pst);
        this.setStringOrNull(2, version, pst);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return rs.getLong("root_db_id");
        } else {
            return null;
        }
    }

    protected Long getAccountDbId(String fb_id, String snapshotname, String version) throws SQLException {

        PreparedStatement pst = this.prepareStatement("SELECT id FROM account WHERE fb_id = ? AND snapshotinfo_id = (SELECT id FROM snapshotinfo WHERE snapshotname = ? AND version = ?)");
        this.setStringOrNull(1, fb_id, pst);
        this.setStringOrNull(2, snapshotname, pst);
        this.setStringOrNull(3, version, pst);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return rs.getLong("id");
        } else {
            return null;
        }
    }

    protected List<Long> getNextid(int count) throws SQLException {
        
        if (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() == DbMode.POSTGRESQL) {
            PreparedStatement pst = this.prepareStatement("select nextval('ssdb_sequence') from generate_series(1,?);");
            this.setIntegerOrNull(1, count, pst);
            ResultSet rs = pst.executeQuery();

            List<Long> returnList = new ArrayList<>(count);
            while (rs.next()) {
                returnList.add(rs.getLong(1));
            }

            return returnList;
        }
        
        if (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() == DbMode.MYSQL) {
            PreparedStatement pst = this.prepareStatement("select nextval(?)");
            this.setIntegerOrNull(1, count, pst);
            ResultSet rs = pst.executeQuery();

            rs.next();
            long idStart = rs.getLong(1);
            
            
            List<Long> returnList = new ArrayList<Long>(count);
            for(long l = idStart; l < idStart + count; l++){
                returnList.add(l);
            }
            
            return returnList;
        }
        
        throw new NotImplementedException("Wrong DB Mode!");
    }

    protected PreparedStatement prepareStatement(String stmt) throws SQLException {
        return this.con.prepareStatement(stmt);
    }

    //HELPER METHODS:
    protected void setDoubleOrNull(int pos, Double value, PreparedStatement pst) throws SQLException {

        if (value == null) {
            pst.setNull(pos, java.sql.Types.DOUBLE);
        } else {
            pst.setDouble(pos, value);
        }
    }

    protected void setIntegerOrNull(int pos, Integer value, PreparedStatement pst) throws SQLException {

        if (value == null) {
            pst.setNull(pos, java.sql.Types.INTEGER);
        } else {
            pst.setInt(pos, value);
        }
    }

    protected void setStringOrNull(int pos, String value, PreparedStatement pst) throws SQLException {

        if (value == null) {
            pst.setNull(pos, java.sql.Types.VARCHAR);
        } else {
            pst.setString(pos, value);
        }
    }

    protected void setLongOrNull(int pos, Long value, PreparedStatement pst) throws SQLException {

        if (value == null) {
            pst.setNull(pos, java.sql.Types.BIGINT);
        } else {
            pst.setLong(pos, value);
        }
    }

    protected String getHighestVersion(String snapshotname) throws SQLException {

        if(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() == DbMode.MYSQL){
            PreparedStatement pst = this.prepareStatement("SELECT version FROM snapshotinfo WHERE snapshotname = ?");
            pst.setString(1, snapshotname);
            
            ResultSet rs = pst.executeQuery();
            
            String maxVersion = "0";
            
            while(rs.next()){
                String s = rs.getString(1);
                if(Integer.parseInt(s) > Integer.parseInt(maxVersion)){
                    maxVersion = s;
                }
            }
            
            return maxVersion;
        }
        
        PreparedStatement pst = this.prepareStatement("SELECT MAX(version) FROM snapshotinfo WHERE snapshotname = ?");
        pst.setString(1, snapshotname);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String s = rs.getString(1);
            return rs.getString(1);
        } else {
            return null;
        }

    }

    /**
     *
     * @param db_id the id of the item the account_id is searched for.
     * @return returns a string array, where [0] is the fb_id and [1] is the
     * fb_name
     */
    protected String[] getAccountInfoForTableDbId(Long db_id) {

        String[] ret = new String[2];

        if ((db_id == null) || ((this.table_name == null) || this.table_name.equals(""))) {
            return ret;
        }

        try {
            PreparedStatement pst = this.prepareStatement("SELECT fb_id, fb_name FROM account WHERE id = (SELECT account_id FROM " + this.table_name + " WHERE id = ?)");
            pst.setLong(1, db_id);
            ResultSet rs = pst.executeQuery();

            rs.next();
            ret[0] = rs.getString("fb_id");
            ret[1] = rs.getString("fb_name");

        } catch (Exception ex) {
        }

        return ret;

    }

    protected String getSnapshotNameForDbId(Long db_id) {

        if ((db_id == null) || ((this.table_name == null) || this.table_name.equals(""))) {
            return "";
        }

        try {
            PreparedStatement pst = this.prepareStatement("SELECT snapshotname FROM snapshotinfo WHERE id = (SELECT snapshotinfo_id FROM account WHERE id = (SELECT account_id FROM " + this.table_name + " WHERE id = ?))");
            pst.setLong(1, db_id);
            ResultSet rs = pst.executeQuery();

            return rs.getString("snapshotname");


        } catch (Exception ex) {
            return "";
        }
    }
}
