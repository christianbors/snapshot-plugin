package org.ssanalytics.snapshotplugin.ui.standalone.helper;

import java.util.List;
import javax.swing.MutableComboBoxModel;
import javax.swing.event.ListDataListener;
import org.ssanalytics.snapshotplugin.ui.standalone.controller.SnapshotController.Visualization;

public class VisualizationMutableModel implements MutableComboBoxModel<Visualization> {

	private List<Visualization> modelList;
	private int index;
	
	public VisualizationMutableModel(List<Visualization> visualizationList) {
		modelList = visualizationList;
		this.index = -1;
	}
	
	public void setModelList(List<Visualization> visualizationList) {
		this.modelList = visualizationList;
	}
	
	public void clear() {
		modelList.clear();
	}
	
	@Override
	public Object getSelectedItem() {
		if(this.index >= 0) {
			return (modelList.get(this.index));
		}
		else {
			return "";
		}
	}

	@Override
	public void setSelectedItem(Object anItem) {

		for(int i = 0; i < modelList.size(); ++i) {
			if(modelList.get(i) == anItem) {
				this.index = i;
				break;
			}
		}
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO not implemented
		
	}

	@Override
	public Visualization getElementAt(int index) {
		return modelList.get(index);
	}

	@Override
	public int getSize() {
		return modelList.size();
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO not implemented
		
	}

	@Override
	public void addElement(Visualization item) {
		if(!modelList.contains(item)) {
			modelList.add(item);
			if(this.index == -1) {
				this.index = 0;
			}
		}
	}

	@Override
	public void insertElementAt(Visualization item, int index) {
		modelList.add(index, item);
	}

	@Override
	public void removeElement(Object obj) {

		if(modelList.contains(obj)) {
			modelList.remove(obj);
		}
	}

	@Override
	public void removeElementAt(int index) {

		if(modelList.size() > index) {
			modelList.remove(index);
		}
		
	}
	
}
