/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.helper.layout;

import java.util.concurrent.TimeUnit;
import org.gephi.graph.api.GraphController;
import org.gephi.layout.plugin.AutoLayout;
import org.openide.util.Lookup;

/**
 *
 * @author christian
 */
public abstract class AbstractSnapshotLayoutController {
    
    protected AutoLayout layout;
    
    public AbstractSnapshotLayoutController() {
        layout = new AutoLayout(3, TimeUnit.SECONDS);
        layout.setGraphModel(Lookup.getDefault().lookup(GraphController.class).getModel());
    }
    
    protected abstract void setLayouts();
    
    public void execute() {
        setLayouts();
        layout.execute();
    }
    
}
