/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract;

/**
 *
 * @author Robert
 */
public class NoSnapshotsException extends Exception {

    public NoSnapshotsException(String s) {
        super(s);
    }
}
