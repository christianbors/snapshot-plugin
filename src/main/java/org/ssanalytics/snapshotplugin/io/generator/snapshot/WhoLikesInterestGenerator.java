/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.generator.snapshot;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.gephi.io.generator.spi.Generator;
import org.gephi.io.importer.api.NodeDraft;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;

/**
 *
 * @author christian
 */
@ServiceProviders(value={
@ServiceProvider(service = Generator.class),
@ServiceProvider(service = SnapshotGenerator.class)})
public class WhoLikesInterestGenerator extends AbstractCategorizedEvidenceCountGenerator implements SnapshotGenerator{
    
    public static String NAME = "Interest Favorites Graph";
    
    @Override
    protected Map<CategoryItem, Integer> getEvidenceMap() {
        try {
            return DaoFactory.getInterestDAO().getEvidenceCountForSnapshotLatestVersion(snapshot.getValue());
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    @Override
    protected void addAttributeColumns() {
        // everything has been initialized in super class
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public List<String> getDistinctCategoryList() {
        try {
            return DaoFactory.getInterestDAO().getDistinctInterestCategories();
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }
    
}
