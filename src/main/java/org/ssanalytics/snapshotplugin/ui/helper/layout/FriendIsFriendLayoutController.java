/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.helper.layout;

import org.gephi.layout.plugin.AutoLayout;
import org.gephi.layout.plugin.fruchterman.FruchtermanReingold;

/**
 *
 * @author christian
 */
public class FriendIsFriendLayoutController extends AbstractSnapshotLayoutController{

    @Override
    protected void setLayouts() {
        FruchtermanReingold fmLayout = new FruchtermanReingold(null);
        AutoLayout.DynamicProperty speed = AutoLayout.createDynamicProperty("fruchtermanReingold.speed.name", new Double(50), 50f);
        AutoLayout.DynamicProperty area = AutoLayout.createDynamicProperty("fruchtermanReingold.area.name", new Double(20000), 20000f);
        AutoLayout.DynamicProperty[] properties = {speed, area};
        
        layout.addLayout(fmLayout, 1f, properties);
    }
    
}
