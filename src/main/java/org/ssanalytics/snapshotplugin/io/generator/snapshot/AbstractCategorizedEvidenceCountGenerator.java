/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.generator.snapshot;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.io.generator.spi.GeneratorUI;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.EdgeDraft;
import org.gephi.io.importer.api.NodeDraft;
import org.gephi.ranking.api.RankingController;
import org.openide.util.Lookup;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.CategoryItem;
import org.ssanalytics.snapshotplugin.ui.generator.snapshot.EvidenceCountGraphUI;
import org.ssanalytics.snapshotplugin.ui.helper.layout.AbstractSnapshotLayoutController;
import org.ssanalytics.snapshotplugin.ui.helper.layout.CategorizedEvidenceLayoutController;
import org.ssanalytics.snapshotplugin.ui.helper.ranking.AbstractSnapshotRankingController;
import org.ssanalytics.snapshotplugin.ui.helper.ranking.CategorizedEvidenceRankingController;

/**
 *
 * @author christian
 */
public abstract class AbstractCategorizedEvidenceCountGenerator extends AbstractSnapshotGenerator {

    Map<String, Color> categoryColors = new HashMap<>();
    private List<String> categoriesSelected;
    protected AttributeModel model;
    Random rand = new Random();

    @Override
    public void generate(ContainerLoader container) {
        model = container.getAttributeModel();
        model.getNodeTable().addColumn("likes", AttributeType.INT);
        model.getNodeTable().addColumn("category", AttributeType.STRING);
        addAttributeColumns();

        if (hasSnapshot() && !categoriesSelected.isEmpty()) {
            Map<CategoryItem, Integer> whoPostedMap = getEvidenceMap();
            NodeDraft namedItemDraft;
            NodeDraft categoryDraft;
            
            long startTime = System.currentTimeMillis();
            for (Map.Entry<CategoryItem, Integer> entry : whoPostedMap.entrySet()) {
                if (categoriesSelected.contains(entry.getKey().getCategory())) {
                    namedItemDraft = container.factory().newNodeDraft();
                    namedItemDraft.setId(entry.getKey().getId());
                    namedItemDraft.setLabel(entry.getKey().getName());

                    if (!categoryColors.containsKey(entry.getKey().getCategory())) {
                        categoryColors.put(entry.getKey().getCategory(), new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                        categoryDraft = container.factory().newNodeDraft();
                        categoryDraft.setId(entry.getKey().getCategory());
                        container.addNode(categoryDraft);
                    } else {
                        categoryDraft = container.getNode(entry.getKey().getCategory());
                    }

                    namedItemDraft.setColor(categoryColors.get(entry.getKey().getCategory()));
                    fillAttributeValues(namedItemDraft, entry);

                    EdgeDraft catEdge = container.factory().newEdgeDraft();
                    catEdge.setSource(categoryDraft);
                    catEdge.setTarget(namedItemDraft);

                    container.addNode(namedItemDraft);
                    container.addEdge(catEdge);
                }
            }
            System.out.println(System.currentTimeMillis() - startTime);
        } else {
            cancel();
        }
    }

    @Override
    public GeneratorUI getUI() {
        return Lookup.getDefault().lookup(EvidenceCountGraphUI.class);
    }

    protected JPanel getCategoryChooser() {
        JPanel pane = new JPanel();
        List<String> categories = getDistinctCategoryList();
        for (String cat : categories) {
            pane.add(new JCheckBox(cat));
        }
        return pane;
    }

    protected abstract Map<CategoryItem, Integer> getEvidenceMap();

    protected abstract void addAttributeColumns();

    protected void fillAttributeValues(NodeDraft nodeDraft, Map.Entry<CategoryItem, Integer> entry) {
        nodeDraft.addAttributeValue(model.getNodeTable().getColumn("likes"), entry.getValue());
        nodeDraft.addAttributeValue(model.getNodeTable().getColumn("category"), ((CategoryItem) entry.getKey()).getCategory());
    }
    public abstract List<String> getDistinctCategoryList();

    public void setCategoriesSelected(List<String> categories) {
        this.categoriesSelected = categories;
    }
    
    @Override
    public AbstractSnapshotRankingController getGeneratorRanking() {
        return new CategorizedEvidenceRankingController();
    }
    
    @Override
    public AbstractSnapshotLayoutController getGeneratorLayout() {
        return new CategorizedEvidenceLayoutController();
    }
}
