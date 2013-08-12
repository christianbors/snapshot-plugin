/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.databaseConnect;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.json.simple.parser.ParseException;
import org.netbeans.validation.api.ui.ValidationPanel;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.Configuration;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;

@ActionID(
        category = "File",
        id = "org.ssanalytics.snapshotplugin.ui.databaseConnect.DatabaseConnect")
@ActionRegistration(
        displayName = "#CTL_DatabaseConnect")
@ActionReference(path = "Menu/File", position = 1100)
public final class DatabaseConnect implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        Configuration config = ConfigurationFileManager.getCurrentConfig();
        if (config == null) {
            JFileChooser chooser = new JFileChooser(ConfigurationFileManager.CONFIG_FILE_NAME);
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    ConfigurationFileManager.CONFIG_FILE_NAME = chooser.getSelectedFile().getCanonicalPath();
                    config = ConfigurationFileManager.readConfigFromFile();
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (ParseException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
        if (config != null) {
            DatabaseConnectPanel dsp = new DatabaseConnectPanel(config);
            ValidationPanel vp = DatabaseConnectPanel.createValidationPanel(dsp);
            if (vp.showOkCancelDialog("Connect to Snapshot Database")) {
                try {
                    ConfigurationFileManager.saveConfigToFile(config);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        } else {
            JOptionPane queryPane = new JOptionPane("Configuration File not found in " + ConfigurationFileManager.CONFIG_FILE_NAME + "!", JOptionPane.ERROR_MESSAGE, JOptionPane.CLOSED_OPTION);
            queryPane.createDialog(null, "Configuration File Error").setVisible(true);
        }
    }
}
