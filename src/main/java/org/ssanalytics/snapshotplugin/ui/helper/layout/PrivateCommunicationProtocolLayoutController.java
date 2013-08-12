/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.helper.layout;

import org.gephi.layout.plugin.AutoLayout;
import org.gephi.layout.plugin.labelAdjust.LabelAdjust;
import org.gephi.layout.plugin.multilevel.YifanHuMultiLevel;

/**
 *
 * @author christian
 */
public class PrivateCommunicationProtocolLayoutController extends AbstractSnapshotLayoutController {

    @Override
    protected void setLayouts() {
        YifanHuMultiLevel yiHufan = new YifanHuMultiLevel();
        AutoLayout.DynamicProperty dist = AutoLayout.createDynamicProperty("YifanHuMultiLevel.optimalDistance.name", new Double(150), 150f);
        AutoLayout.DynamicProperty[] propertiesYiHufan = {dist};


        LabelAdjust labelAdjust = new LabelAdjust(null);
        AutoLayout.DynamicProperty speed = AutoLayout.createDynamicProperty("LabelAdjust.speed.name", new Double(10), 10f);
        AutoLayout.DynamicProperty[] propertiesLabelAdjust = {speed};

        layout.addLayout(yiHufan.buildLayout(), 0.9f, propertiesYiHufan);
        layout.addLayout(labelAdjust, 0.1f, propertiesLabelAdjust);
    }
}
