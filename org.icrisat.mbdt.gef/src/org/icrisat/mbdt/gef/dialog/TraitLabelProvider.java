package org.icrisat.mbdt.gef.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.sessions.Session;

public class TraitLabelProvider extends LabelProvider implements ITableLabelProvider {
	List<String> lMarkers= new ArrayList<String>();
	List<String> markerPositions= new ArrayList<String>();
	List<String> markersList = new ArrayList<String>();
	static int count= 0;

	
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getColumnText(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		String result= "Trait";
		
		
		return result;
	}

}
