package org.icrisat.mbdt.gef.wizards;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;

public class ShowHiddenEleContentProvider implements IStructuredContentProvider {

	
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		return ((Genotype)inputElement).getSelAccForUnHide().toArray();
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}
}
