package org.icrisat.mbdt.gef.dialog;

import org.eclipse.jface.viewers.LabelProvider;
import org.icrisat.mbdt.model.LinkageMapModel.MapMarkers;

public class MapLabelProvider extends LabelProvider {
	
	@Override
	public String getText(Object element) {
		// TODO Auto-generated method stub
		return ((MapMarkers)element).getName();
	}

}
