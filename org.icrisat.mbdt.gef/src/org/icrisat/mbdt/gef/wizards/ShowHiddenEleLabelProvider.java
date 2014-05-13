package org.icrisat.mbdt.gef.wizards;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;

public class ShowHiddenEleLabelProvider extends LabelProvider implements ITableLabelProvider {
	/*@Override
	public String getText(Object element) {
		// TODO Auto-generated method stub
		return ((Accessions)element).getName();
	}*/

	
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getColumnText(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		String result= "";
		switch(columnIndex) {
		case 0:
			result= ((Accessions)element).getName();
			break;
		default:
			break;
		}
		return result;
	}

}
