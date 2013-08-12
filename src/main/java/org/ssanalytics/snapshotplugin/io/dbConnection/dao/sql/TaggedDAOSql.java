/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedToDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedActionDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedCommentsDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedFromDAOSql;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged.ITagged;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged.ITaggedApplication;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged.ITaggedProperties;

/**
 *
 * @author Robert K
 */
public class TaggedDAOSql extends AbstractSuperDAOSql {

    private static TaggedDAOSql instance = null;

    protected TaggedDAOSql() throws SQLException {
        super();
        this.table_name = "tagged";
    }

    public static TaggedDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new TaggedDAOSql();
        }
        return instance;
    }

    public void saveTaggedList(List<ITagged> toSave, long accountId) throws SQLException {

        if (toSave == null) {
            return;
        }

        PreparedStatement pst = this.prepareStatement("INSERT INTO " + this.table_name + " (id, account_id, caption, icon, link, picture, type, updated_time, description, message, created_time, fb_id, fb_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        pst.clearBatch();

        for (ITagged tagged : toSave) {

            long tagged_id = IdProvider.getInstance().getNextId();
            pst.clearParameters();

            this.setLongOrNull(1, tagged_id, pst);
            this.setLongOrNull(2, accountId, pst);
            this.setStringOrNull(3, tagged.getCaption(), pst);
            this.setStringOrNull(4, tagged.getIcon(), pst);
            this.setStringOrNull(5, tagged.getLink(), pst);
            this.setStringOrNull(6, tagged.getPicture(), pst);
            this.setStringOrNull(7, tagged.getType(), pst);
            this.setLongOrNull(8, tagged.getUpdated_time(), pst);
            this.setStringOrNull(9, tagged.getDescription(), pst);
            this.setStringOrNull(10, tagged.getMessage(), pst);
            this.setLongOrNull(11, tagged.getCreated_time(), pst);
            this.setStringOrNull(12, tagged.getId(), pst);
            this.setStringOrNull(13, tagged.getName(), pst);

            pst.addBatch();

            //save tagged application
            this.saveTaggedApplication(tagged.getApplication(), tagged_id);

            //save tagged properties
            this.saveTaggedPropertyList(tagged.getProperties(), tagged_id);

            //save actions
            SharedActionDAOSql.getInstance().saveSharedAction(tagged.getActions(), tagged_id, this.table_name);

            //save comments
            SharedCommentsDAOSql.getInstance().saveSharedComments(tagged.getComments(), tagged_id, this.table_name);

            //save from
            SharedFromDAOSql.getInstance().saveSharedFrom(tagged.getFrom(), tagged_id, this.table_name);

            //save to
            SharedToDAOSql.getInstance().saveSharedTo(tagged.getTo(), tagged_id, this.table_name);

        }
    }

    private void saveTagged(ITagged toSave, long accountid) throws SQLException {
        if (toSave != null) {
            ArrayList<ITagged> al = new ArrayList<>(1);
            al.add(toSave);
            this.saveTaggedList(al, accountid);
        }
    }

    private void saveTaggedPropertyList(List<ITaggedProperties> tosave, long tagged_id) throws SQLException {
        if (tosave == null) {
            return;
        }

        PreparedStatement pst = this.prepareStatement("INSERT INTO tagged_properties (id, tagged_id, href, fb_name, text) VALUES (?, ?, ?, ? ,?)");
        pst.clearBatch();

        for (ITaggedProperties tp : tosave) {
            pst.clearParameters();

            this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
            this.setLongOrNull(2, tagged_id, pst);
            this.setStringOrNull(3, tp.getHref(), pst);
            this.setStringOrNull(4, tp.getName(), pst);
            this.setStringOrNull(5, tp.getText(), pst);

            pst.addBatch();;
        }

        pst.executeBatch();
    }

    private void saveTaggedApplication(ITaggedApplication toSave, long tagged_id) throws SQLException {
        if (toSave == null) {
            return;
        }

        PreparedStatement pst = this.prepareStatement("INSERT INTO tagged_application (id, tagged_id, fb_id, fb_name, namespace) VALUES (?, ?, ?, ?, ?)");

        this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
        this.setLongOrNull(2, tagged_id, pst);
        this.setStringOrNull(3, toSave.getId(), pst);
        this.setStringOrNull(4, toSave.getName(), pst);
        this.setStringOrNull(5, toSave.getNamespace(), pst);

        pst.execute();
    }
}
