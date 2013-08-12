/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.helper.model;

import java.util.LinkedList;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;
import org.gephi.io.generator.api.GeneratorController;
import org.gephi.io.generator.spi.Generator;
import org.openide.util.Lookup;
import org.ssanalytics.snapshotplugin.io.generator.general.GeneralGenerator;
import org.ssanalytics.snapshotplugin.io.generator.snapshot.SnapshotGenerator;
import org.ssanalytics.snapshotplugin.ui.helper.ComboItemGenerator;

/**
 *
 * @author christian
 */
public class SnapshotGeneratorComboBoxModel implements ComboBoxModel<ComboItemGenerator> {

    GeneratorController genController = Lookup.getDefault().lookup(GeneratorController.class);
    List<ComboItemGenerator> generators = new LinkedList<>();
    private ListDataListener listener = null;
    private ComboItemGenerator selected = null;

    public SnapshotGeneratorComboBoxModel() {
        if (genController != null) {
            for (Generator gen : Lookup.getDefault().lookupAll(GeneralGenerator.class)) {
                ComboItemGenerator genItem = new ComboItemGenerator(gen);
                generators.add(genItem);
            }
            for (Generator gen : Lookup.getDefault().lookupAll(SnapshotGenerator.class)) {
                ComboItemGenerator genItem = new ComboItemGenerator(gen);
                generators.add(genItem);
            }
        }
    }

    @Override
    public void setSelectedItem(Object anItem) {
        this.selected = (ComboItemGenerator) anItem;
    }

    @Override
    public Object getSelectedItem() {
        return this.selected;
    }

    @Override
    public int getSize() {
        return generators.size();
    }

    @Override
    public ComboItemGenerator getElementAt(int index) {
        return generators.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        this.listener = l;
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        this.listener = null;
    }
}