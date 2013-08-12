/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.generator.snapshot;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import org.gephi.io.generator.spi.Generator;
import org.openide.util.lookup.ServiceProvider;
import org.ssanalytics.snapshotplugin.io.generator.snapshot.AbstractCategorizedEvidenceCountGenerator;

/**
 *
 * @author christian
 */
@ServiceProvider(service = EvidenceCountGraphUI.class)
public class EvidenceCountGraphUIImpl implements EvidenceCountGraphUI {

    private AbstractCategorizedEvidenceCountGenerator generator;
    private EvidenceCountGraphPanel panel;
    
    public EvidenceCountGraphUIImpl() {
    }
    
    @Override
    public JPanel getPanel() {
        if(this.panel == null) {
            panel = new EvidenceCountGraphPanel(generator.getDistinctCategoryList());
            System.out.println("null");
        }
        return EvidenceCountGraphPanel.createValidationPanel(panel, generator.getDistinctCategoryList());
    }

    @Override
    public void setup(Generator generator) {
        this.generator = (AbstractCategorizedEvidenceCountGenerator) generator;
        
        if(panel == null) {
            panel = new EvidenceCountGraphPanel(this.generator.getDistinctCategoryList());
        }
        DefaultListModel<String> model = new DefaultListModel<>();
        for(String s : this.generator.getDistinctCategoryList()) {
            model.addElement(s);
        }
        panel.listCategories.setModel(model);
    }

    @Override
    public void unsetup() {
        generator.setCategoriesSelected(panel.listCategories.getSelectedValuesList());
    }
    
}
