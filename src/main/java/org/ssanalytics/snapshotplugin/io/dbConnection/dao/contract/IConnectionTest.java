/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import org.json.simple.parser.ParseException;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.DbConfiguration;

/**
 *
 * @author Robert K
 */
public interface IConnectionTest {

    public boolean testCurrentConnection() throws ClassNotFoundException, SQLException, UnknownHostException, NoSnapshotsException, IOException, ParseException;
    //TODO add new method

    public boolean testNewConnection(DbConfiguration conf) throws ClassNotFoundException, SQLException, UnknownHostException, NoSnapshotsException, IOException, ParseException;
}
