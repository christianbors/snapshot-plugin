/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.standalone;

import java.awt.EventQueue;
import org.ssanalytics.snapshotplugin.ui.standalone.view.SnapshotMainFrame;

/**
 *
 * @author christian
 */
public class StandaloneApp {
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    SnapshotMainFrame window = new SnapshotMainFrame();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
