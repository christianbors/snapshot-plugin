package org.ssanalytics.snapshotplugin.ui.standalone.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.Configuration;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.config.dynamicConfig.DbConfiguration;

public class SnapshotSettingsDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTabbedPane tabbedPane;
    private JTextField tfDBName;
    private JComboBox<DbConfiguration> cbDbConfig;
    private JTextField tfHostName;
    private JTextField tfPort;
    private JTextField tfUsername;
    private JTextField tfPassword;
    
    private Configuration config = ConfigurationFileManager.getCurrentConfig();

    /**
     * Create the dialog.
     */
    public SnapshotSettingsDialog() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));
        {
            tabbedPane = new JTabbedPane(JTabbedPane.TOP);
            contentPanel.add(tabbedPane, BorderLayout.CENTER);

            JPanel panelDb = new JPanel();
            JPanel panelPlaceholder = new JPanel();
            tabbedPane.addTab("Database", panelDb);
            {
                JPanel panelDbTest = new JPanel();
                tabbedPane.addTab("Database Tester", null, panelDbTest, null);
                GridBagLayout gbl_panelDbTest = new GridBagLayout();
                gbl_panelDbTest.columnWidths = new int[]{419, 0};
                gbl_panelDbTest.rowHeights = new int[]{191, 0};
                gbl_panelDbTest.columnWeights = new double[]{1.0, Double.MIN_VALUE};
                gbl_panelDbTest.rowWeights = new double[]{0.0, Double.MIN_VALUE};
                panelDbTest.setLayout(gbl_panelDbTest);
            }
            tabbedPane.addTab("TODO", panelPlaceholder);

            GridBagLayout gbl_panelDb = new GridBagLayout();
            gbl_panelDb.columnWidths = new int[]{0, 0, 0};
            gbl_panelDb.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
            gbl_panelDb.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
            gbl_panelDb.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
            panelDb.setLayout(gbl_panelDb);
            {
                JLabel lblDatabase = new JLabel("Database:");
                GridBagConstraints gbc_lblDatabase = new GridBagConstraints();
                gbc_lblDatabase.insets = new Insets(0, 0, 5, 5);
                gbc_lblDatabase.anchor = GridBagConstraints.EAST;
                gbc_lblDatabase.gridx = 0;
                gbc_lblDatabase.gridy = 0;
                panelDb.add(lblDatabase, gbc_lblDatabase);
            }
            {
                cbDbConfig = new JComboBox<>(config.getDbConfigurations().toArray(new DbConfiguration[0]));
                cbDbConfig.setSelectedItem(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration());
                cbDbConfig.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        config.setActiveDbConfiguration(((DbConfiguration) cbDbConfig.getSelectedItem()).getConfigurationName());

                        System.out.println("DB Mode set to " + ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbMode());

                        tfDBName.setText(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbName());
                        refillDbValues();
                    }
                });
                GridBagConstraints gbc_comboBox = new GridBagConstraints();
                gbc_comboBox.insets = new Insets(0, 0, 5, 0);
//				gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
                gbc_comboBox.gridx = 1;
                gbc_comboBox.gridy = 0;
                gbc_comboBox.gridwidth = 2;
                panelDb.add(cbDbConfig, gbc_comboBox);
            }
            {
                JLabel lblDatabaseName = new JLabel("Database Name:");
                GridBagConstraints gbc_lblDatabaseName = new GridBagConstraints();
                gbc_lblDatabaseName.anchor = GridBagConstraints.EAST;
                gbc_lblDatabaseName.insets = new Insets(0, 0, 5, 5);
                gbc_lblDatabaseName.gridx = 0;
                gbc_lblDatabaseName.gridy = 1;
                panelDb.add(lblDatabaseName, gbc_lblDatabaseName);
            }
            {
                tfDBName = new JTextField();
                tfDBName.setText(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbName());
                GridBagConstraints gbc_textField = new GridBagConstraints();
                gbc_textField.insets = new Insets(0, 0, 5, 5);
                gbc_textField.fill = GridBagConstraints.HORIZONTAL;
                gbc_textField.gridx = 1;
                gbc_textField.gridy = 1;
                panelDb.add(tfDBName, gbc_textField);
                tfDBName.setColumns(10);
            }
            {
                JLabel lblHostName = new JLabel("Host Name:");
                GridBagConstraints gbc_lblHostName = new GridBagConstraints();
                gbc_lblHostName.anchor = GridBagConstraints.EAST;
                gbc_lblHostName.insets = new Insets(0, 0, 5, 5);
                gbc_lblHostName.gridx = 0;
                gbc_lblHostName.gridy = 2;
                panelDb.add(lblHostName, gbc_lblHostName);
            }
            {
                tfHostName = new JTextField();
                tfHostName.setText(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getHost());
                GridBagConstraints gbc_textField_1 = new GridBagConstraints();
                gbc_textField_1.insets = new Insets(0, 0, 5, 5);
                gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
                gbc_textField_1.gridx = 1;
                gbc_textField_1.gridy = 2;
                panelDb.add(tfHostName, gbc_textField_1);
                tfHostName.setColumns(10);
            }
            {
                JLabel lblDatabasePort = new JLabel("Database Port:");
                GridBagConstraints gbc_lblDatabasePort = new GridBagConstraints();
                gbc_lblDatabasePort.anchor = GridBagConstraints.EAST;
                gbc_lblDatabasePort.insets = new Insets(0, 0, 5, 5);
                gbc_lblDatabasePort.gridx = 0;
                gbc_lblDatabasePort.gridy = 3;
                panelDb.add(lblDatabasePort, gbc_lblDatabasePort);
            }
            {
                tfPort = new JTextField();
                tfPort.setText(String.valueOf(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getPort()));
                GridBagConstraints gbc_textField_2 = new GridBagConstraints();
                gbc_textField_2.insets = new Insets(0, 0, 5, 5);
                gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
                gbc_textField_2.gridx = 1;
                gbc_textField_2.gridy = 3;
                panelDb.add(tfPort, gbc_textField_2);
                tfPort.setColumns(10);
            }
            {
                JLabel lblUsername = new JLabel("Username:");
                GridBagConstraints gbc_lblUsername = new GridBagConstraints();
                gbc_lblUsername.anchor = GridBagConstraints.EAST;
                gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
                gbc_lblUsername.gridx = 0;
                gbc_lblUsername.gridy = 4;
                panelDb.add(lblUsername, gbc_lblUsername);
            }
            {
                tfUsername = new JTextField();
                tfUsername.setText(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getUser());
                GridBagConstraints gbc_textField_3 = new GridBagConstraints();
                gbc_textField_3.insets = new Insets(0, 0, 5, 5);
                gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
                gbc_textField_3.gridx = 1;
                gbc_textField_3.gridy = 4;
                panelDb.add(tfUsername, gbc_textField_3);
                tfUsername.setColumns(10);
            }
            {
                JLabel lblPassword = new JLabel("Password:");
                GridBagConstraints gbc_lblPassword = new GridBagConstraints();
                gbc_lblPassword.anchor = GridBagConstraints.EAST;
                gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
                gbc_lblPassword.gridx = 0;
                gbc_lblPassword.gridy = 5;
                panelDb.add(lblPassword, gbc_lblPassword);
            }
            {
                tfPassword = new JTextField();
                tfPassword.setText(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getPwd());
                GridBagConstraints gbc_textField_4 = new GridBagConstraints();
                gbc_textField_4.insets = new Insets(0, 0, 5, 5);
                gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
                gbc_textField_4.gridx = 1;
                gbc_textField_4.gridy = 5;
                panelDb.add(tfPassword, gbc_textField_4);
                tfPassword.setColumns(10);
            }
            {
                JButton btnSave = new JButton("Save");
                btnSave.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        JOptionPane.showMessageDialog(null, "Save Procedure did not end succesfully", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
                GridBagConstraints gbc_btnSave = new GridBagConstraints();
                gbc_btnSave.anchor = GridBagConstraints.NORTHEAST;
                gbc_btnSave.insets = new Insets(0, 0, 0, 5);
                gbc_btnSave.gridx = 1;
                gbc_btnSave.gridy = 6;
                panelDb.add(btnSave, gbc_btnSave);
            }
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");

                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        saveVariables();
                        dispose();
                    }
                });

                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");

                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();

                    }
                });

                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void refillDbValues() {
        
        tfDBName.setText(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getDbName());
        tfHostName.setText(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getHost());
        tfPort.setText(String.valueOf(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getPort()));
        tfUsername.setText(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getUser());
        tfPassword.setText(ConfigurationFileManager.getCurrentConfig().getActiveDbConfiguration().getPwd());
    }

    private void saveVariables() {
    }
}
