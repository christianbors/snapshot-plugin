package org.ssanalytics.snapshotplugin.ui.standalone.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;
import org.ssanalytics.snapshotplugin.ui.standalone.controller.SnapshotSummaryController;

public class SnapshotSummaryView extends JFrame {

	private JPanel contentPane;
	private JPanel panelPosts;
	private JPanel panelPhotos;
	private JPanel panelFriends;
	private JPanel panelPrivate;
	private JLabel lblRelationshipStatus;
	private JLabel label;
	private JLabel label_1;
	private JComboBox cbRelationshipStatus;
	private JLabel lblInterests;
	private JPanel panelFilterFriends;
	private JLabel lblEvents;
	private JComboBox cbInterests;
	private JTable tableFilteredFriends;
	private JScrollPane spTable;
	
	private FriendTableModel friendTableModel;
	
	private SnapshotSummaryController summaryController;
	private JPanel panelMessages;

	/**
	 * Create the frame.
	 */
	public SnapshotSummaryView(ISnapshotInfo snapshot) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		summaryController = new SnapshotSummaryController();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		panelPosts = new JPanel();
		panelPhotos = new JPanel();
		panelFriends = new JPanel();
		panelPrivate = new JPanel();
		
		tabbedPane.addTab("Friends", panelFriends);
		
		label_1 = new JLabel("Number of Friends:");
		
		label = new JLabel("Friend with most mutual friends:");
		
		panelFilterFriends = new JPanel();
		panelFilterFriends.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gbl_panelFilterFriends = new GridBagLayout();
		gbl_panelFilterFriends.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panelFilterFriends.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panelFilterFriends.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelFilterFriends.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		panelFilterFriends.setLayout(gbl_panelFilterFriends);
		
		JLabel lblGender = new JLabel("Gender:");
		GridBagConstraints gbc_lblGender = new GridBagConstraints();
		gbc_lblGender.anchor = GridBagConstraints.WEST;
		gbc_lblGender.insets = new Insets(0, 0, 5, 5);
		gbc_lblGender.gridx = 0;
		gbc_lblGender.gridy = 0;
		panelFilterFriends.add(lblGender, gbc_lblGender);
		
		JRadioButton rdbtnMale = new JRadioButton("male");
		GridBagConstraints gbc_rdbtnMale = new GridBagConstraints();
		gbc_rdbtnMale.anchor = GridBagConstraints.NORTHWEST;
		gbc_rdbtnMale.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnMale.gridx = 1;
		gbc_rdbtnMale.gridy = 0;
		panelFilterFriends.add(rdbtnMale, gbc_rdbtnMale);
		GroupLayout gl_panelFriends = new GroupLayout(panelFriends);
		gl_panelFriends.setHorizontalGroup(
			gl_panelFriends.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFriends.createSequentialGroup()
					.addGroup(gl_panelFriends.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelFriends.createParallelGroup(Alignment.LEADING)
							.addComponent(label_1)
							.addComponent(label))
						.addComponent(panelFilterFriends, GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelFriends.setVerticalGroup(
			gl_panelFriends.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFriends.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_1)
					.addGap(5)
					.addComponent(label)
					.addGap(5)
					.addComponent(panelFilterFriends, GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JRadioButton rdbtnFemail = new JRadioButton("female");
		GridBagConstraints gbc_rdbtnFemail = new GridBagConstraints();
		gbc_rdbtnFemail.anchor = GridBagConstraints.NORTHWEST;
		gbc_rdbtnFemail.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnFemail.gridx = 2;
		gbc_rdbtnFemail.gridy = 0;
		panelFilterFriends.add(rdbtnFemail, gbc_rdbtnFemail);
		
		lblRelationshipStatus = new JLabel("Relationship status:");
		GridBagConstraints gbc_lblRelationshipStatus = new GridBagConstraints();
		gbc_lblRelationshipStatus.anchor = GridBagConstraints.WEST;
		gbc_lblRelationshipStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblRelationshipStatus.gridx = 0;
		gbc_lblRelationshipStatus.gridy = 1;
		panelFilterFriends.add(lblRelationshipStatus, gbc_lblRelationshipStatus);
		
		cbRelationshipStatus = new JComboBox();
		GridBagConstraints gbc_cbRelationshipStatus = new GridBagConstraints();
		gbc_cbRelationshipStatus.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbRelationshipStatus.insets = new Insets(0, 0, 5, 5);
		gbc_cbRelationshipStatus.gridx = 1;
		gbc_cbRelationshipStatus.gridy = 1;
		panelFilterFriends.add(cbRelationshipStatus, gbc_cbRelationshipStatus);
		
		lblInterests = new JLabel("Interests:");
		GridBagConstraints gbc_lblInterests = new GridBagConstraints();
		gbc_lblInterests.anchor = GridBagConstraints.WEST;
		gbc_lblInterests.insets = new Insets(0, 0, 5, 5);
		gbc_lblInterests.gridx = 0;
		gbc_lblInterests.gridy = 2;
		panelFilterFriends.add(lblInterests, gbc_lblInterests);
		
		cbInterests = new JComboBox();
		GridBagConstraints gbc_cbInterests = new GridBagConstraints();
		gbc_cbInterests.insets = new Insets(0, 0, 5, 5);
		gbc_cbInterests.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbInterests.gridx = 1;
		gbc_cbInterests.gridy = 2;
		panelFilterFriends.add(cbInterests, gbc_cbInterests);
		
		lblEvents = new JLabel("Events:");
		GridBagConstraints gbc_lblEvents = new GridBagConstraints();
		gbc_lblEvents.anchor = GridBagConstraints.WEST;
		gbc_lblEvents.insets = new Insets(0, 0, 5, 5);
		gbc_lblEvents.gridx = 0;
		gbc_lblEvents.gridy = 3;
		panelFilterFriends.add(lblEvents, gbc_lblEvents);
		
		spTable = new JScrollPane();
		spTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_spTable = new GridBagConstraints();
		gbc_spTable.gridwidth = 3;
		gbc_spTable.gridx = 0;
		gbc_spTable.gridy = 4;
		gbc_spTable.fill = GridBagConstraints.BOTH;
		gbc_spTable.weightx = 1;
		gbc_spTable.weighty = 1;
		panelFilterFriends.add(spTable, gbc_spTable);
		
		try {
			friendTableModel = new FriendTableModel(summaryController.getFriendsList(snapshot));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tableFilteredFriends = new JTable(friendTableModel);
		spTable.setViewportView(tableFilteredFriends);
		panelFriends.setLayout(gl_panelFriends);
		tabbedPane.addTab("Posts", panelPosts);
		tabbedPane.addTab("Photos", panelPhotos);
		tabbedPane.addTab("Privacy", panelPrivate);
		
		panelMessages = new JPanel();
		tabbedPane.addTab("Messages", null, panelMessages, null);
	}
	
	class FriendTableModel extends AbstractTableModel {
		private String[] columnNames = { "Name", "Account Id" };
		
		private List<IFriend> rowDataList;

		public FriendTableModel(List<IFriend> friendsList) {
			this.setData(friendsList);
		}
		
		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public String getColumnName(int index) {
			return columnNames[index];
		}
		
		@Override
		public int getRowCount() {
			return rowDataList.size();
		}

		@Override
		public Object getValueAt(int arg0, int arg1) {
			switch(arg1) {
			case 0:
				return rowDataList.get(arg0).getName();
			case 1:
				return rowDataList.get(arg0).getId();
			default:
				return null;
			}
		}
		
		public void setData(List<IFriend> friendsList) {
			this.rowDataList = friendsList;
		}
		
	}
}
