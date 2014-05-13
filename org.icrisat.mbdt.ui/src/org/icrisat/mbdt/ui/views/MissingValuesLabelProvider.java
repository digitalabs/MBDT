package org.icrisat.mbdt.ui.views;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;

public class MissingValuesLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getColumnText(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		String result= "";
		switch(columnIndex) {
		case 0:			
			result = ((Accessions)element).getName();
			break;
		case 1:	
			String markers="";
			for( int i = 0; i < ((Accessions)element).getMissingMarkers().size(); i++) {
				if (markers.equals("")){
					markers=((Accessions)element).getMissingMarkers().get(i);
						
				} else{
					if(markers.indexOf(((Accessions)element).getMissingMarkers().get(i)) == -1){
						markers=markers+", "+((Accessions)element).getMissingMarkers().get(i);
					}
				}
			}
			result = markers;
			break;
		 default :
			break;
		}
		return result;
	}
}
