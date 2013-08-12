/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.exportReport.view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.project.api.WorkspaceProvider;
import org.gephi.ui.components.JSqueezeBoxPanel;
import org.openide.util.Lookup;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.ChapterContent;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.Content;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ReportContent;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ExportContentUI;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.TextContent;
import org.ssanalytics.snapshotplugin.ui.exportReport.contentGenerator.GraphVisUI;

/**
 *
 * @author christian
 */
public class ExportPanel extends JPanel {

    private ExportCategory[] categories;
    private List<UIFrontEnd> frontEnds;
    private JSqueezeBoxPanel squeezeBoxPanel;
    private ISnapshotInfo snapshot;

    public ExportPanel(ISnapshotInfo snapshot) {
        this.snapshot = snapshot;
        setLayout(new BorderLayout());
        initComponents();
        initCategories();
        initFrontEnds();
    }
    
    public ExportPanel() {
        setLayout(new BorderLayout());
        initComponents();
        initCategories();
        initFrontEnds();
    }

    private void initFrontEnds() {

        ExportContentUI[] exportUIs = Lookup.getDefault().lookupAll(ExportContentUI.class).toArray(new ExportContentUI[0]);
        frontEnds = new ArrayList<>();

        for (ExportCategory category : categories) {
            //Find uis in this category
            List<ExportContentUI> ecuis = new ArrayList<>();
            for (ExportContentUI ecui : exportUIs) {
                if (ecui.getCategory().equals(category.getName())) {
                    ecuis.add(ecui);
                }
            }

            if (ecuis.size() > 0) {
                //Sort it by position
                Collections.sort(ecuis, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Integer p1 = ((ExportContentUI) o1).getPosition();
                        Integer p2 = ((ExportContentUI) o2).getPosition();
                        return p1.compareTo(p2);
                    }
                });

                MigLayout migLayout = new MigLayout("insets 0");
                migLayout.setColumnConstraints("[grow,fill]");
                migLayout.setRowConstraints("[fill]");
                JPanel innerPanel = new JPanel(migLayout);

                for (ExportContentUI exportUI : ecuis) {
                    ExportFrontEnd frontEnd = new ExportFrontEnd(exportUI, this.snapshot);
                    UIFrontEnd uife = new UIFrontEnd(exportUI, frontEnd, category);
                    frontEnds.add(uife);

                    innerPanel.add(frontEnd);
                }

                squeezeBoxPanel.addPanel(innerPanel, category.getName());
            }
        }
    }

    private void initCategories() {
        Map<String, ExportCategory> tempCategories = new LinkedHashMap<>();
        tempCategories.put(ExportContentUI.SNAPSHOT_INFO, new ExportCategory(ExportContentUI.SNAPSHOT_INFO, 100));
        tempCategories.put(ExportContentUI.GRAPH_VIS, new ExportCategory(ExportContentUI.GRAPH_VIS, 200));
        tempCategories.put(ExportContentUI.COMMUNICATION_PROTOCOLS, new ExportCategory(ExportContentUI.COMMUNICATION_PROTOCOLS, 300));
        tempCategories.put(ExportContentUI.STATISTIC_EVAL, new ExportCategory(ExportContentUI.STATISTIC_EVAL, 400));
        tempCategories.put(ExportContentUI.STATISTIC_CHARTS, new ExportCategory(ExportContentUI.STATISTIC_CHARTS, 500));

        int position = 500;
        for (ExportContentUI uis : Lookup.getDefault().lookupAll(ExportContentUI.class)) {
            String category = uis.getCategory();
            if (!tempCategories.containsKey(category)) {
                tempCategories.put(category, new ExportCategory(category, position));
                position += 100;
            }
        }

        categories = tempCategories.values().toArray(new ExportCategory[0]);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        squeezeBoxPanel = new JSqueezeBoxPanel();
        add(squeezeBoxPanel, BorderLayout.CENTER);
    }

    public Content[][] retrieveContent(ISnapshotInfo snapshot) {
        List<Content[]> contentList = new LinkedList<>();
        ExportCategory catCurrent = null;
        for (UIFrontEnd uife : frontEnds) {
            if (uife.getFrontEnd().isActiveExport()) {
                for (ReportContent ecui : Lookup.getDefault().lookupAll(ReportContent.class)) {
                    if (ecui.getClass().equals(uife.exportContentUI.getExportContentClass())) {
                        uife.getExportContentUI().setup(ecui, snapshot);
                    }
                }
                uife.getExportContentUI().generateContent();
                if (catCurrent == null || catCurrent != uife.getCategory()) {
                    Content[] c = {new ChapterContent(uife.getCategory().getName())};
                    contentList.add(c);
                }
                contentList.add(uife.getExportContentUI().getContent());
                catCurrent = uife.getCategory();
            }
        }
        return contentList.toArray(new Content[0][]);
    }

    public ExportCategory[] getCategories() {
        return categories;
    }

    private static class UIFrontEnd {

        private ExportContentUI exportContentUI;
        private ExportFrontEnd frontEnd;
        private ExportCategory category;
        private boolean visible;

        public UIFrontEnd(ExportContentUI statisticsUI, ExportFrontEnd frontEnd, ExportCategory category) {
            this.exportContentUI = statisticsUI;
            this.frontEnd = frontEnd;
            this.category = category;
            this.visible = true;
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        public ExportFrontEnd getFrontEnd() {
            return frontEnd;
        }

        public ExportContentUI getExportContentUI() {
            return exportContentUI;
        }

        public ExportCategory getCategory() {
            return category;
        }
    }
}
