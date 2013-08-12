/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.generator.snapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.gephi.io.generator.spi.Generator;
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
public class WhoLikesWhatGenerator extends AbstractCategorizedEvidenceCountGenerator implements SnapshotGenerator{

    public static String NAME = "Likes Graph";
    
    @Override
    protected Map<CategoryItem, Integer> getEvidenceMap() {
        try {
            return DaoFactory.getLikeDAO().getEvidenceCountForSnapshotLatestVersion(snapshot.getValue());
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    @Override
    protected void addAttributeColumns() {
        //no additional attributes
    }

    @Override
    public List<String> getDistinctCategoryList() {
        try {
            return DaoFactory.getLikeDAO().getDistinctLikeCategories();
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    @Override
    public String getName() {
        return NAME;
    }
    
}
