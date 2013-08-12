/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.generator.snapshot;

import java.util.Map;
import org.gephi.io.generator.spi.Generator;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;

/**
 *
 * @author christian
 */
@ServiceProviders(value={
@ServiceProvider(service = Generator.class),
@ServiceProvider(service = SnapshotGenerator.class)})
public class OutboxWordCloudGenerator extends AbstractWordCloudGenerator implements SnapshotGenerator{

    public static String NAME = "Outbox Word Cloud";
    
    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected Map<String, Integer> getWordMap() {
        try {
            return DaoFactory.getOutboxDAO().getWordCountForOutboxOfRootAccountSnapshotLatestVersion(snapshot.getValue());
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }
    
}
