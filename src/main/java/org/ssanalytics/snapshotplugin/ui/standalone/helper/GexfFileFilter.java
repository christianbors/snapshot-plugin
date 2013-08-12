package org.ssanalytics.snapshotplugin.ui.standalone.helper;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public 	class GexfFileFilter extends FileFilter {
	
	@Override
	public boolean accept(File f) {
		String filename = f.getName();
		
		if (f.isDirectory()) {
			return true;
		}

		
		return filename.endsWith(".gexf");
	}

	@Override
	public String getDescription() {
		return "*.gexf";
	}
}