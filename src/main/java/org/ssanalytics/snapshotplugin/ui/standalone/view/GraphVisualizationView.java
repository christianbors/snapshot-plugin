package org.ssanalytics.snapshotplugin.ui.standalone.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class GraphVisualizationView extends JPanel{
	
	public GraphVisualizationView() {
		this.setLayout(new BorderLayout());
	}	
	
//	public void updateView(TransportVisualizationMode transportMode) throws Exception {
//		if(transportMode.transportsSnapshot()) {
//			switch(transportMode.getVisualization()) {
//			case FRIENDISFRIEND:
//				this.add(graphController.createFriendIsFriendGraph(transportMode.getSnapshotInfo()));
//				break;
//			case EVENT:
//				this.add(graphController.createEventsGraph(transportMode.getSnapshotInfo()));
//			default:
//				break;
//			}
//		}
//	}
}
