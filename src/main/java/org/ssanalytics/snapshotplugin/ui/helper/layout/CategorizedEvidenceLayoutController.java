/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.helper.layout;

import org.gephi.layout.plugin.AutoLayout;
import org.gephi.layout.plugin.forceAtlas.ForceAtlasLayout;

/**
 *
 * @author christian
 */
public class CategorizedEvidenceLayoutController extends AbstractSnapshotLayoutController {

    public CategorizedEvidenceLayoutController() {
        super();

    }

    @Override
    protected void setLayouts() {
        ForceAtlasLayout forceAtlasLayout = new ForceAtlasLayout(null);
        AutoLayout.DynamicProperty repulsion = AutoLayout.createDynamicProperty("forceAtlas.repulsionStrength.name", new Double(50), 1f);
        AutoLayout.DynamicProperty attraction = AutoLayout.createDynamicProperty("forceAtlas.attractionStrength.name", new Double(0.1), 1f);
        AutoLayout.DynamicProperty maxDisplacement = AutoLayout.createDynamicProperty("forceAtlas.maxDisplacement.name", new Double(50.0), 1f);
        AutoLayout.DynamicProperty gravity = AutoLayout.createDynamicProperty("forceAtlas.gravity.name", new Double(10.0), 1f);
        AutoLayout.DynamicProperty adjustSizes = AutoLayout.createDynamicProperty("forceAtlas.adjustSizes.name", Boolean.FALSE, 0f);
        
        AutoLayout.DynamicProperty[] properties = {repulsion, attraction, maxDisplacement, gravity, adjustSizes};
        layout.addLayout(forceAtlasLayout, 1f, properties);
    }
}
