package org.ssanalytics.snapshotplugin.ui.standalone.view;

import com.mongodb.MongoException;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.UnknownHostException;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.gephi.preview.spi.Renderer;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;
import org.ssanalytics.snapshotplugin.ui.standalone.controller.GraphVisualizationController;
import org.ssanalytics.snapshotplugin.ui.standalone.controller.SnapshotController;
import org.ssanalytics.snapshotplugin.ui.standalone.controller.SnapshotController.SourceOption;
import org.ssanalytics.snapshotplugin.ui.standalone.controller.SnapshotController.Visualization;
import org.ssanalytics.snapshotplugin.ui.standalone.helper.GexfFileFilter;
import org.ssanalytics.snapshotplugin.ui.standalone.helper.PdfFileFilter;
import org.ssanalytics.snapshotplugin.ui.standalone.helper.StatusBar;
import org.ssanalytics.snapshotplugin.ui.standalone.helper.VisualizationMutableModel;

public class SnapshotMainFrame {

    private JFrame frame;
    private StatusBar statusBar;
    private JCheckBox chckbxProfileVis;
    private JComboBox<Visualization> cbVisualization;
    private VisualizationMutableModel cbmVisualization;
    private DefaultListModel<ISnapshotInfo> snapshotInfoModel;
    private JList<ISnapshotInfo> listSnapshot;
    private DefaultListModel<IProfile> profileModel;
    private JList<IProfile> listProfile;
    private JFileChooser chooser;
    private JButton btnGenerate;
    private JButton btnExport;
    private JButton btnSnapshotSummary;
    private JCheckBox chckbxShowLabels;
    private GraphVisualizationView panelCenter;
    private SnapshotController snapshotController;
    private GraphVisualizationController graphVisController;

    /**
     * Create the application.
     */
    public SnapshotMainFrame() {
        try {
            initialize();
        } catch (UnknownHostException | MongoException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setVisible(boolean isVisible) {
        this.frame.setVisible(isVisible);
    }

    /**
     * Initialize the contents of the frame.
     *
     * @throws SQLException
     * @throws MongoException
     * @throws UnknownHostException
     */
    private void initialize() throws SQLException, UnknownHostException, MongoException {

        frame = new JFrame();
        frame.setBounds(100, 100, 800, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        snapshotController = new SnapshotController();
        graphVisController = new GraphVisualizationController();
        profileModel = new DefaultListModel<>();

        JPanel panelTop = new JPanel();
        frame.getContentPane().add(panelTop, BorderLayout.NORTH);
        GridBagLayout gbl_panelTop = new GridBagLayout();
        gbl_panelTop.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
        gbl_panelTop.rowHeights = new int[]{20, 0};
        gbl_panelTop.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panelTop.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panelTop.setLayout(gbl_panelTop);

        JPanel panelVisualization = new JPanel();
        panelVisualization.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Visualization", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagConstraints gbc_panelVisualization = new GridBagConstraints();
        gbc_panelVisualization.insets = new Insets(0, 0, 0, 5);
        gbc_panelVisualization.fill = GridBagConstraints.BOTH;
        gbc_panelVisualization.gridx = 0;
        gbc_panelVisualization.gridy = 0;
        panelTop.add(panelVisualization, gbc_panelVisualization);
        GridBagLayout gbl_panelVisualization = new GridBagLayout();
        gbl_panelVisualization.columnWidths = new int[]{0, 0, 0};
        gbl_panelVisualization.rowHeights = new int[]{20, 0};
        gbl_panelVisualization.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gbl_panelVisualization.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panelVisualization.setLayout(gbl_panelVisualization);

        JLabel lblVisualization = new JLabel("Graph Type");
        GridBagConstraints gbcLblVisualization = new GridBagConstraints();
        gbcLblVisualization.insets = new Insets(0, 0, 0, 5);
        gbcLblVisualization.gridx = 0;
        gbcLblVisualization.gridy = 0;
        panelVisualization.add(lblVisualization, gbcLblVisualization);

        cbmVisualization = new VisualizationMutableModel(snapshotController.getVisOptions(SourceOption.SNAPSHOT));

        cbVisualization = new JComboBox<>(cbmVisualization);
        cbVisualization.setSelectedIndex(0);
        GridBagConstraints gbcCbVisualization = new GridBagConstraints();
        gbcCbVisualization.gridx = 1;
        gbcCbVisualization.gridy = 0;
        panelVisualization.add(cbVisualization, gbcCbVisualization);

        btnGenerate = new JButton("Generate");
        btnGenerate.setEnabled(false);

        GridBagConstraints gbc_btnGenerate = new GridBagConstraints();
        gbc_btnGenerate.insets = new Insets(0, 0, 0, 5);
        gbc_btnGenerate.anchor = GridBagConstraints.EAST;
        gbc_btnGenerate.gridx = 1;
        gbc_btnGenerate.gridy = 0;
        panelTop.add(btnGenerate, gbc_btnGenerate);

        btnExport = new JButton("Export");
        btnExport.setEnabled(false);

        GridBagConstraints gbc_btnExport = new GridBagConstraints();
        gbc_btnExport.insets = new Insets(0, 0, 0, 5);
        gbc_btnExport.gridx = 2;
        gbc_btnExport.gridy = 0;
        panelTop.add(btnExport, gbc_btnExport);

        btnSnapshotSummary = new JButton("Snapshot Summary");
        btnSnapshotSummary.setEnabled(false);

        GridBagConstraints gbc_btnSnapshotSummary = new GridBagConstraints();
        gbc_btnSnapshotSummary.gridx = 4;
        gbc_btnSnapshotSummary.gridy = 0;
        panelTop.add(btnSnapshotSummary, gbc_btnSnapshotSummary);

        JPanel panelSideLeft = new JPanel();
        frame.getContentPane().add(panelSideLeft, BorderLayout.WEST);
        GridBagLayout gbl_panelSideLeft = new GridBagLayout();
        gbl_panelSideLeft.columnWidths = new int[]{49, 0};
        gbl_panelSideLeft.rowHeights = new int[]{0, 0, 0, 0};
        gbl_panelSideLeft.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_panelSideLeft.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
        panelSideLeft.setLayout(gbl_panelSideLeft);

        JPanel panelSnapshot = new JPanel();
        panelSnapshot.setToolTipText("Snapshots");
        panelSnapshot.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Snapshot", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagConstraints gbc_panelSnapshot = new GridBagConstraints();
        gbc_panelSnapshot.fill = GridBagConstraints.BOTH;
        gbc_panelSnapshot.insets = new Insets(0, 0, 5, 0);
        gbc_panelSnapshot.gridx = 0;
        gbc_panelSnapshot.gridy = 0;
        panelSideLeft.add(panelSnapshot, gbc_panelSnapshot);
        GridBagLayout gbl_panelSnapshot = new GridBagLayout();
        gbl_panelSnapshot.columnWidths = new int[]{49, 0};
        gbl_panelSnapshot.rowHeights = new int[]{0, 0};
        gbl_panelSnapshot.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_panelSnapshot.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        panelSnapshot.setLayout(gbl_panelSnapshot);

        try {
            snapshotInfoModel = new DefaultListModel<>();
            for (ISnapshotInfo si : snapshotController.getSnapshotList()) {
                snapshotInfoModel.addElement(si);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JScrollPane scrollPaneSnapshot = new JScrollPane();
        scrollPaneSnapshot.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneSnapshot.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        GridBagConstraints gbc_scrollPaneSnapshot = new GridBagConstraints();
        gbc_scrollPaneSnapshot.fill = GridBagConstraints.BOTH;
        gbc_scrollPaneSnapshot.gridx = 0;
        gbc_scrollPaneSnapshot.gridy = 0;
        panelSnapshot.add(scrollPaneSnapshot, gbc_scrollPaneSnapshot);
        listSnapshot = new JList<>(snapshotInfoModel);
        listSnapshot.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPaneSnapshot.setViewportView(listSnapshot);

        chckbxProfileVis = new JCheckBox("Profile based Visualization");
        chckbxProfileVis.setSelected(false);

        GridBagConstraints gbc_chckbxProfileVis = new GridBagConstraints();
        gbc_chckbxProfileVis.anchor = GridBagConstraints.WEST;
        gbc_chckbxProfileVis.insets = new Insets(0, 0, 5, 0);
        gbc_chckbxProfileVis.gridx = 0;
        gbc_chckbxProfileVis.gridy = 1;
        panelSideLeft.add(chckbxProfileVis, gbc_chckbxProfileVis);

        JPanel panelProfile = new JPanel();
        panelProfile.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Profile", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagConstraints gbc_panelProfile = new GridBagConstraints();
        gbc_panelProfile.fill = GridBagConstraints.BOTH;
        gbc_panelProfile.gridx = 0;
        gbc_panelProfile.gridy = 2;
        panelSideLeft.add(panelProfile, gbc_panelProfile);
        GridBagLayout gbl_panelProfile = new GridBagLayout();
        gbl_panelProfile.columnWidths = new int[]{49, 0};
        gbl_panelProfile.rowHeights = new int[]{0, 0};
        gbl_panelProfile.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_panelProfile.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        panelProfile.setLayout(gbl_panelProfile);

        JScrollPane scrollPaneProfile = new JScrollPane();
        scrollPaneProfile.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneProfile.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        GridBagConstraints gbc_scrollPaneProfile = new GridBagConstraints();
        gbc_scrollPaneProfile.fill = GridBagConstraints.BOTH;
        gbc_scrollPaneProfile.gridx = 0;
        gbc_scrollPaneProfile.gridy = 0;
        panelProfile.add(scrollPaneProfile, gbc_scrollPaneProfile);
        listProfile = new JList<>(profileModel);
        scrollPaneProfile.setViewportView(listProfile);
        listProfile.setVisible(false);

        panelCenter = new GraphVisualizationView();
        frame.getContentPane().add(panelCenter, BorderLayout.CENTER);

        // Options Panel
        JPanel panelSideRight = new JPanel();
        frame.getContentPane().add(panelSideRight, BorderLayout.EAST);
        GridBagLayout gbl_panelSideRight = new GridBagLayout();
        gbl_panelSideRight.columnWidths = new int[]{0, 0};
        gbl_panelSideRight.rowHeights = new int[]{0, 0};
        gbl_panelSideRight.columnWeights = new double[]{0.0, Double.MIN_VALUE};
        gbl_panelSideRight.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panelSideRight.setLayout(gbl_panelSideRight);

        JPanel panelOptions = new JPanel();
        panelOptions.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Graph Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagConstraints gbc_panelOptions = new GridBagConstraints();
        gbc_panelOptions.fill = GridBagConstraints.BOTH;
        gbc_panelOptions.gridx = 0;
        gbc_panelOptions.gridy = 0;
        panelSideRight.add(panelOptions, gbc_panelOptions);
        GridBagLayout gbl_panelOptions = new GridBagLayout();
        gbl_panelOptions.columnWidths = new int[]{0, 0};
        gbl_panelOptions.rowHeights = new int[]{-19, 0, 0, 0, 0, 0, 0};
        gbl_panelOptions.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_panelOptions.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, Double.MIN_VALUE};
        panelOptions.setLayout(gbl_panelOptions);

        chckbxShowLabels = new JCheckBox("Show Labels");
        GridBagConstraints gbc_chckbxShowLabels = new GridBagConstraints();
        gbc_chckbxShowLabels.insets = new Insets(0, 0, 5, 0);
        gbc_chckbxShowLabels.gridx = 0;
        gbc_chckbxShowLabels.gridy = 0;
        panelOptions.add(chckbxShowLabels, gbc_chckbxShowLabels);

        JLabel lblOtherOptionsTo = new JLabel("Other Options to be implemented");
        GridBagConstraints gbc_lblOtherOptionsTo = new GridBagConstraints();
        gbc_lblOtherOptionsTo.insets = new Insets(0, 0, 5, 0);
        gbc_lblOtherOptionsTo.gridx = 0;
        gbc_lblOtherOptionsTo.gridy = 1;
        panelOptions.add(lblOtherOptionsTo, gbc_lblOtherOptionsTo);

        JLabel lblNodeStyle = new JLabel("Node Style");
        GridBagConstraints gbc_lblNodeStyle = new GridBagConstraints();
        gbc_lblNodeStyle.anchor = GridBagConstraints.WEST;
        gbc_lblNodeStyle.insets = new Insets(0, 0, 5, 0);
        gbc_lblNodeStyle.gridx = 0;
        gbc_lblNodeStyle.gridy = 2;
        panelOptions.add(lblNodeStyle, gbc_lblNodeStyle);

        JComboBox<Renderer> cbNodes = new JComboBox<Renderer>(graphVisController.getNodeStyles());
        GridBagConstraints gbc_cbNodes = new GridBagConstraints();
        gbc_cbNodes.insets = new Insets(0, 0, 5, 0);
        gbc_cbNodes.fill = GridBagConstraints.HORIZONTAL;
        gbc_cbNodes.gridx = 0;
        gbc_cbNodes.gridy = 3;
        panelOptions.add(cbNodes, gbc_cbNodes);

        JLabel lblEdgeStyle = new JLabel("Edge Style");
        GridBagConstraints gbc_lblEdgeStyle = new GridBagConstraints();
        gbc_lblEdgeStyle.insets = new Insets(0, 0, 5, 0);
        gbc_lblEdgeStyle.anchor = GridBagConstraints.WEST;
        gbc_lblEdgeStyle.gridx = 0;
        gbc_lblEdgeStyle.gridy = 4;
        panelOptions.add(lblEdgeStyle, gbc_lblEdgeStyle);

        JComboBox<Renderer> cbEdges = new JComboBox<>(graphVisController.getRenderers());
        GridBagConstraints gbc_cbEdges = new GridBagConstraints();
        gbc_cbEdges.fill = GridBagConstraints.HORIZONTAL;
        gbc_cbEdges.gridx = 0;
        gbc_cbEdges.gridy = 5;
        panelOptions.add(cbEdges, gbc_cbEdges);

        // MenuBar
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        JMenuItem mntmNew = new JMenuItem("New");
        mnFile.add(mntmNew);

        JMenuItem mntmImport = new JMenuItem("Import...");
        mnFile.add(mntmImport);

        JMenuItem mntmExport = new JMenuItem("Export...");
        mnFile.add(mntmExport);

        JSeparator separator = new JSeparator();
        mnFile.add(separator);

        JMenuItem mntmExit = new JMenuItem("Exit");
        mnFile.add(mntmExit);

        JMenu mnEdit = new JMenu("Edit");
        menuBar.add(mnEdit);

        JMenu mnTools = new JMenu("Tools");
        menuBar.add(mnTools);

        JMenuItem mntmSettings = new JMenuItem("Settings");
        mnTools.add(mntmSettings);

        JMenu mnHelp = new JMenu("Help");
        menuBar.add(mnHelp);

        // StatusBar
        statusBar = new StatusBar();
        frame.getContentPane().add(statusBar, BorderLayout.SOUTH);

        btnExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                chooser = new JFileChooser();
                File graphExport = new File(listSnapshot.getSelectedValue().toString() + ".gexf");
                chooser.addChoosableFileFilter(new GexfFileFilter());
                chooser.addChoosableFileFilter(new PdfFileFilter());
                chooser.setSelectedFile(graphExport);
                int returnVal = chooser.showSaveDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    // add other options to export (pdf etc.)
                    String filepath = chooser.getSelectedFile().getAbsolutePath();
                    if (!filepath.endsWith(".gexf")) {
                        filepath = filepath + ".gexf";
                    }
                    graphVisController.exportCurrentGraph(filepath);
                }
            }
        });

        btnSnapshotSummary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!listSnapshot.isSelectionEmpty()) {
                    SnapshotSummaryView snapshotSummary = new SnapshotSummaryView(listSnapshot.getSelectedValue());
                    snapshotSummary.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Please make a valid Snapshot selection", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (chckbxProfileVis.isSelected()) {
                    if (!listProfile.isSelectionEmpty()) {
//						switch((Visualization) cbVisualization.getSelectedItem()) {
//						case EVENT:
////							panelCenter
//							break;
//						case TESTPROFILE:
//							break;
//						default:
//							//TODO implement Dialog: Something went wrong
//							break;
//						}	
                    } else {
                        JOptionPane.showMessageDialog(null, "Please make a valid Profile selection", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    try {
                        if (!listSnapshot.isSelectionEmpty()) {
                            System.out.println(cbVisualization.getSelectedItem());
                            if (cbVisualization.getSelectedItem() == Visualization.EVENT) {
                                graphVisController.createEventsGraph(listSnapshot.getSelectedValue(), panelCenter);
                            }
                            if (cbVisualization.getSelectedItem() == Visualization.FRIENDISFRIEND) {
                                graphVisController.createFriendIsFriendGraph(listSnapshot.getSelectedValue(), panelCenter);
                            }

//							switch((Visualization) cbVisualization.getSelectedItem()) {
//								case EVENT: {
//									graphVisController.createEventsGraph(listSnapshot.getSelectedValue(), panelCenter);
//								}
//								case FRIENDISFRIEND: {
//									graphVisController.createFriendIsFriendGraph(listSnapshot.getSelectedValue(), panelCenter);
//								}
//								default:	
//									break;
//							}
                        } else {
                            JOptionPane.showMessageDialog(null, "Please make a valid Snapshot selection", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                btnExport.setEnabled(true);
            }
        });

        chckbxProfileVis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                listProfile.setVisible(chckbxProfileVis.isSelected());

                if (chckbxProfileVis.isSelected()) {

                    cbmVisualization.setModelList(snapshotController.getVisOptions(SourceOption.PROFILE));
                    cbVisualization.updateUI();
                    cbVisualization.setSelectedIndex(0);

                    ISnapshotInfo selected;
                    if (listSnapshot.isSelectionEmpty()) {
                        listSnapshot.setSelectedIndex(0);
                    }

                    selected = listSnapshot.getSelectedValue();

                    try {
                        for (IProfile profile : snapshotController.getProfileList(selected)) {
                            profileModel.addElement(profile);
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                } else {
                    cbmVisualization.setModelList(snapshotController.getVisOptions(SourceOption.SNAPSHOT));
                    cbVisualization.updateUI();
                    cbVisualization.setSelectedIndex(0);
                    profileModel.clear();

                }
            }
        });

        mntmSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnapshotSettingsDialog settings = new SnapshotSettingsDialog();
                settings.setVisible(true);
            }
        });

        listSnapshot.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                btnGenerate.setEnabled(true);
                btnSnapshotSummary.setEnabled(true);
            }
        });

        chckbxShowLabels.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                graphVisController.showLabels(chckbxShowLabels.isSelected());
            }
        });
    }
}
