/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.generator.snapshot;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.io.generator.spi.Generator;
import org.gephi.io.importer.api.NodeDraft;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.ui.helper.ranking.AbstractSnapshotRankingController;

/**
 *
 * @author christian
 */
@ServiceProviders(value={
@ServiceProvider(service = Generator.class),
@ServiceProvider(service = SnapshotGenerator.class)})
public class CategorizedLikesGenerator extends AbstractCategorizedEvidenceCountGenerator implements SnapshotGenerator{

    public static String NAME = "Categorized Likes Graph";
    Map<String, Color> categoryColors = new HashMap<>();
    
    @Override
    protected Map<CategoryItem, Integer> getEvidenceMap() {
        try {
            Map<CategoryItem, Integer> categoryMap = DaoFactory.getMovieDAO().getEvidenceCountForSnapshotLatestVersion(snapshot.getValue());
            categoryMap.putAll(DaoFactory.getTelevisionDAO().getTelevisionEvidenceCountForSnapshotLatestVersion(snapshot.getValue()));
            categoryMap.putAll(DaoFactory.getMusicDAO().getEvidenceCountForSnapshotLatestVersion(snapshot.getValue()));
            return categoryMap;
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    @Override
    protected void addAttributeColumns() {
    }

    @Override
    public List<String> getDistinctCategoryList() {
        try {
            List<String> categoryList = DaoFactory.getMovieDAO().getDistinctMovieCategories();
            categoryList.addAll(DaoFactory.getTelevisionDAO().getDistinctTelevisionCategories());
            categoryList.addAll(DaoFactory.getMusicDAO().getDistinctMusicCategories());
            return categoryList;
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
