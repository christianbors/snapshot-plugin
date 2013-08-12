package org.ssanalytics.snapshotplugin.ui.standalone.helper;

import java.awt.Dimension;
import javax.swing.JLabel;

public class StatusBar extends JLabel {
	
	public StatusBar() {
		super();
		super.setPreferredSize(new Dimension(100, 16));
		this.setMessage("Ready");
	}
	
	public void setMessage(String message) {
		this.setText(" " + message);
	}
}
