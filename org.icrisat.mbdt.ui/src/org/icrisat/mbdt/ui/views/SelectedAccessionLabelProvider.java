package org.icrisat.mbdt.ui.views;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;

public class SelectedAccessionLabelProvider extends LabelProvider implements ITableLabelProvider{

	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		
		String result= "";
		switch(columnIndex) {
		case 0:
			result= ((Accessions)element).getName();
			break;
		case 1:
			result= ((Accessions)element).getType();
			break;
		default:
			break;
		}
		return result;
	}
}
