package org.icrisat.mbdt.gef.dialog;

import org.eclipse.jface.viewers.LabelProvider;
import org.icrisat.mbdt.model.GenotypeModel.SelectedAccessions;

public class AccessionLabelProvider extends LabelProvider {
	
	@Override
	public String getText(Object element) {
		// TODO Auto-generated method stub
		return ((SelectedAccessions)element).getSelAccession();
	}

}
