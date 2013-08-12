/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.generator.profile;

import javax.swing.JPanel;
import org.gephi.io.generator.spi.Generator;
import org.openide.util.lookup.ServiceProvider;
import org.ssanalytics.snapshotplugin.io.generator.profile.AbstractPhotoInfoGenerator;

/**
 *
 * @author christian
 */
@ServiceProvider(service = PhotoGraphUI.class)
public class PhotoGraphUIImpl implements PhotoGraphUI {

    private AbstractPhotoInfoGenerator generator;
    private PhotoGraphPanel panel;
    
    @Override
    public JPanel getPanel() {
        if(this.panel == null) {
            panel = new PhotoGraphPanel();
        }
        return panel;
    }

    @Override
    public void setup(Generator generator) {
        this.generator = (AbstractPhotoInfoGenerator) generator;
        
        if(this.panel == null) {
            this.panel = new PhotoGraphPanel();
        }
        panel.setShowFriends(true);
    }

    @Override
    public void unsetup() {
        generator.showFriendsOnly(panel.getShowFriends());
    }
    
}
