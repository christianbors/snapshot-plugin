/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import org.json.simple.parser.ParseException;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.DbConfiguration;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IConnectionTest;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.NoSnapshotsException;

/**
 *
 * @author Robert
 */
public class ConnectionTestMongo implements IConnectionTest {

    @Override
    public boolean testCurrentConnection() throws NoSnapshotsException, UnknownHostException, MongoException, IOException, ParseException {

        return this.testNewConnection(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration());

    }

    @Override
    public boolean testNewConnection(DbConfiguration conf) throws UnknownHostException, NoSnapshotsException, IOException, ParseException {

        Mongo mongo = null;

        try {
            mongo = new Mongo(conf.getHost(), Integer.parseInt(conf.getPort()));
        } catch (UnknownHostException uhex) {
            throw uhex;
        }

        try {
            mongo.getDatabaseNames();
        } catch (MongoException mex) {
            throw new MongoException("Could not connect to the chosen mongoDB");
        }

        DB db = null;
        try {
            db = mongo.getDB(conf.getDbName());
        } catch (MongoException mex) {
            throw mex;
        }

        DBCollection dbc = null;
        try {
            db.getCollectionNames();
            dbc = db.getCollection("snapshotInfo");
        } catch (MongoException mex) {
            throw new MongoException("Could not find collection \"snapshotinfo\" maybe the chosen DB does not exist? (" + conf.getDbName() + ")");
        }

        boolean shots = true;

        try {
            DBCursor curse = dbc.find();
            if (curse.count() == 0) {
                shots = false;
            }
        } catch (MongoException mex) {
            shots = false;
        }

        if (shots) {
            return true;
        } else {
            throw new NoSnapshotsException("No snapshots found in the chosen Mongo DB");
        }
    }
}
