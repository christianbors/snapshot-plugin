package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedCommentsDAOSql;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql.sharedSqlDaos.SharedFromDAOSql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.INoteDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.link.ILink;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.note.INote;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.note.NoteSql;

public class NoteDAOSql extends AbstractSuperDAOSql implements INoteDAO{

    private static NoteDAOSql instance = null;

    protected NoteDAOSql() throws SQLException {
        super();
        this.table_name = "note";
    }

    public static NoteDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new NoteDAOSql();
        }
        return instance;
    }

    public void saveNoteList(List<INote> toSave, long accountId) throws SQLException {

        if (toSave == null) {
            return;
        }

        PreparedStatement pst = this.prepareStatement("INSERT INTO note (id, account_id, created_time, updated_time, icon, subject, message, fb_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        pst.clearBatch();

        for (INote note : toSave) {

            long note_id = IdProvider.getInstance().getNextId();

            this.setLongOrNull(1, note_id, pst);
            this.setLongOrNull(2, accountId, pst);
            this.setLongOrNull(3, note.getCreated_time(), pst);
            this.setLongOrNull(4, note.getUpdated_time(), pst);
            this.setStringOrNull(5, note.getIcon(), pst);
            this.setStringOrNull(6, note.getSubject(), pst);
            this.setStringOrNull(7, note.getMessage(), pst);
            this.setStringOrNull(8, note.getId(), pst);

            pst.addBatch();

            // save comments
            SharedCommentsDAOSql.getInstance().saveSharedComments(note.getComments(), note_id, this.table_name);

            // save from
            SharedFromDAOSql.getInstance().saveSharedFrom(note.getFrom(), note_id, this.table_name);

        }

        pst.executeBatch();

    }

    @Override
    public List<INote> getAllNotesInSnapshotLatestVersion(String snapshotName) throws SQLException {
       return this.getAllNotesInSnapshotSpecificVersion(snapshotName, this.getHighestVersion(snapshotName));
    }

    @Override
    public List<INote> getAllNotesInSnapshotSpecificVersion(String snapshotName, String version) throws SQLException {
        PreparedStatement pst = this.prepareStatement("SELECT id, created_time, updated_time, icon, subject, message, fb_id FROM " + this.table_name + " WHERE account_id IN (SELECT id FROM account WHERE snapshotinfo_id = (SELECT id from snapshotinfo WHERE snapshotname = ? AND version = ?))");
        
        this.setStringOrNull(1, snapshotName, pst);
        this.setStringOrNull(2, version, pst);

        ResultSet rs = pst.executeQuery();
        
        List<INote> noteList = new LinkedList<>();
                
        while(rs.next()){
            Long note_id = rs.getLong("id");
            Long created_time = rs.getLong("created_time");
            Long updated_time = rs.getLong("updated_time");
            String icon = rs.getString("icon");
            String subject = rs.getString("subject");
            String message = rs.getString("message");
            String fb_id = rs.getString("fb_id");
            
            ISharedComments comments = SharedCommentsDAOSql.getInstance().getComments(note_id, this.table_name);
            ISharedFrom from = SharedFromDAOSql.getInstance().getSharedFrom(note_id, this.table_name);
            
            noteList.add(new NoteSql(fb_id, comments, from, created_time, updated_time, icon, subject, message));
        }
        
        return noteList;
    }

    @Override
    public List<ISharedComments> getAllCommentsInSnapshotLatestVersion(String snapshot) throws Exception {
        return this.getAllCommentsInSnapshotSpecificVersion(snapshot, this.getHighestVersion(snapshot));
    }

    @Override
    public List<ISharedComments> getAllCommentsInSnapshotSpecificVersion(String snapshot, String version) throws Exception {
        return CommentDAOSql.getInstance().getCommentsForTableSnapshotSpecificVersion(this.table_name, snapshot, version);
    }
}
