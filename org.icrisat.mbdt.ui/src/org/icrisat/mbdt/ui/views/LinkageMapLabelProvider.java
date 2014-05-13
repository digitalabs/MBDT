package org.icrisat.mbdt.ui.views;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.icrisat.mbdt.model.LinkageMapModel.Intervals;
import org.icrisat.mbdt.model.LinkageMapModel.LimsMarkers;

public class LinkageMapLabelProvider extends LabelProvider implements
ITableLabelProvider {
	static String marker;
	public Image getColumnImage(Object element, int columnIndex) {

		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
	
		String result="";
		
		((Intervals)element).getCount();
		switch (columnIndex){
		case 0:
			result = ((Intervals)element).getChromosome();
			break;
		case 1:
			result = ((Intervals)element).getMap_marker();
			marker=result;
			break;
		case 2:
			result = ((Intervals)element).getNumbering();
			break;
		case 3:
			result = ((Intervals)element).getDistance();
			break;
		case 4:
			result = ((Intervals)element).getMarkerposition();
			break;
		case 5:
			result = ((Intervals)element).getForestatus();
		default:
			break;		
		}
		
		return result;
		
	}

	

}
