/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.parser.ParseException;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.DbConfiguration;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.DbMode;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IConnectionTest;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.NoSnapshotsException;

/**
 *
 * @author Robert K
 */
public class ConnectionTestSql implements IConnectionTest {

    @Override
    public boolean testCurrentConnection() throws ClassNotFoundException, SQLException, NoSnapshotsException, IOException, ParseException {

        return this.testNewConnection(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration());
    }

    @Override
    public boolean testNewConnection(DbConfiguration conf) throws ClassNotFoundException, SQLException, NoSnapshotsException, IOException, ParseException {


        if (ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode() == DbMode.POSTGRESQL) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                if (ConfigurationFileManager.getCurrentConfig().getOutputLevel() >= 2) {
                    System.out.println("Bro, where is your PostgreSQL JDBC Driver? "
                            + "Include in your library path!");
                    e.printStackTrace();
                }

                throw new ClassNotFoundException("The java-postgresql driver has not been found!");
            }

            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://" + conf.getHost() + ":"
                    + conf.getPort() + "/"
                    + conf.getDbName(), conf.getUser(), conf.getPwd());

            PreparedStatement pst = connection.prepareStatement("SELECT COUNT(*) FROM snapshotinfo");

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                throw new NoSnapshotsException("The postgresql database does not contain any snapshots!");
            }
        } else {
            return true;
        }
    }
}
