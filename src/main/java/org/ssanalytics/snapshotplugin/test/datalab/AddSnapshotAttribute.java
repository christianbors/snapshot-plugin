/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.datalab;

import javax.swing.Icon;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeTable;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.data.attributes.api.AttributeValue;
import org.gephi.datalab.api.AttributeColumnsController;
import org.gephi.datalab.api.GraphElementsController;
import org.gephi.datalab.spi.ManipulatorUI;
import org.gephi.datalab.spi.general.GeneralActionsManipulator;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.Node;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;

/**
 *
 * @author christian
 */
@ServiceProvider(service = GeneralActionsManipulator.class)
public class AddSnapshotAttribute implements GeneralActionsManipulator {

    @Override
    public void execute() {
        AttributeController ac = Lookup.getDefault().lookup(AttributeController.class);
        Lookup.getDefault().lookup(AttributeColumnsController.class).addAttributeColumn(ac.getModel().getNodeTable(), "SnapshotVersion", AttributeType.STRING);
        AttributeTable table = ac.getModel().getNodeTable();
        AttributeColumn attributeColumn = table.getColumn("SnapshotVersion");
        for (Node n : Lookup.getDefault().lookup(GraphController.class).getModel().getGraph().getNodes()) {
            String value;
            try {
                value = DaoFactory.getSnapshotInfoDAO().getSnapshotInfoList().get(0).getRoot();
                Lookup.getDefault().lookup(AttributeColumnsController.class).setAttributeValue(value, n.getNodeData().getAttributes(), attributeColumn);
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }

        }
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(AddSnapshotAttribute.class, "AddSnapshotAttribute.name");
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    @Override
    public ManipulatorUI getUI() {
        return null;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public int getPosition() {
        return 5;
    }

    @Override
    public Icon getIcon() {
        return ImageUtilities.loadImageIcon("org/ssanalytics/snapshotplugin/test/datalab/Facebook-32.png", true);
    }
}
