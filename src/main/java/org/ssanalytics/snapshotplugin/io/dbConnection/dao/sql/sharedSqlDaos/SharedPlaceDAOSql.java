/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.AbstractSuperDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.IdProvider;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLocation;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared.SharedFromSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared.SharedLocationSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared.SharedPlaceSql;

/**
 *
 * @author chw
 */
public class SharedPlaceDAOSql extends AbstractSuperDAOSql {

    private static SharedPlaceDAOSql instance = null;

    protected SharedPlaceDAOSql() throws SQLException {
        super();
    }

    public static SharedPlaceDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new SharedPlaceDAOSql();
        }
        return instance;
    }

    public void saveSharedPlace(ISharedPlace place, long papaId, String papaName) throws SQLException {

        if (place != null) {

            if (place.getId() == null || place.getId().equals("")) {
                return;
            }

            PreparedStatement pst = this.prepareStatement("INSERT INTO shared_place (id, papa_id, papa_name, fb_id, fb_name) VALUES (?, ?, ?, ?, ?)");
            Long place_id = IdProvider.getInstance().getNextId();
            this.setLongOrNull(1, place_id, pst);
            this.setLongOrNull(2, papaId, pst);
            this.setStringOrNull(3, papaName, pst);
            this.setStringOrNull(4, place.getId(), pst);
            this.setStringOrNull(5, place.getName(), pst);

            pst.execute();

            ISharedLocation loc = place.getLocation();

            if (loc != null) {
                pst = this.prepareStatement("INSERT INTO shared_location (id, shared_place_id, longitude, latitude, city, street, country, zip, location_state) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
                this.setLongOrNull(2, place_id, pst);
                this.setDoubleOrNull(3, loc.getLongitude(), pst);
                this.setDoubleOrNull(4, loc.getLatitude(), pst);
                this.setStringOrNull(5, loc.getCity(), pst);
                this.setStringOrNull(6, loc.getStreet(), pst);
                this.setStringOrNull(7, loc.getCountry(), pst);
                this.setStringOrNull(8, loc.getZip(), pst);
                this.setStringOrNull(9, loc.getState(), pst);

                pst.execute();

            }
        }
    }

    public ISharedPlace getSharedPlace(long papaId, String papaName) throws SQLException {

        PreparedStatement pst = this.prepareStatement("SELECT id, fb_id, fb_name FROM shared_place WHERE papa_id = ? AND papa_name = ?");
        this.setLongOrNull(1, papaId, pst);
        this.setStringOrNull(2, papaName, pst);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            Long place_id = rs.getLong("id");
            String place_fb_id = rs.getString("fb_id");
            String place_name = rs.getString("fb_name");

            pst = this.prepareStatement("SELECT longitude, latitude, city, street, country, zip, location_state FROM shared_location WHERE shared_place_id = ?");
            this.setLongOrNull(1, place_id, pst);
            ResultSet rsInner = pst.executeQuery();

            ISharedLocation loc = null;

            if (rsInner.next()) {
                Double longi = rsInner.getDouble("longitude");
                Double lati = rsInner.getDouble("latitude");
                String city = rsInner.getString("city");
                String zip = rsInner.getString("zip");
                String street = rsInner.getString("street");
                String location_state = rsInner.getString("location_state");
                String country = rsInner.getString("country");

                loc = new SharedLocationSql(longi, lati, city, street, zip, country, street);
            }

            return new SharedPlaceSql(place_fb_id, place_name, loc);

        }

        return null;
    }
}
