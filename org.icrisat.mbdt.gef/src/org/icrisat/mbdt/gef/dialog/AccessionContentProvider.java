package org.icrisat.mbdt.gef.dialog;

import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.GenotypeModel.SelectedAccessions;

public class AccessionContentProvider implements IStructuredContentProvider {

	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		return ((List<SelectedAccessions>)inputElement).toArray();
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

}
