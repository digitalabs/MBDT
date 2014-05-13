package org.icrisat.mbdt.lims.views;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.icrisat.mbdt.model.LinkageMapModel.LimsMarkers;

public class LimsMarkersInputLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		String result= "";
		switch(columnIndex) {
		case 0:
			result = ((LimsMarkers)element).getstrUnscreenedMrks();
			((LimsMarkers)element).setStrMarkersFrmTviewer(result);
			break;
		case 1:
			result= ((LimsMarkers)element).getMarkerStatus();
			break;
		default :
			break;
		}
		return result;
	}

}
