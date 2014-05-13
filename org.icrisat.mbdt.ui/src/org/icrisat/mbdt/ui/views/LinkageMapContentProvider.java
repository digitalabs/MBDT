package org.icrisat.mbdt.ui.views;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.LinkageMapModel.LinkageMap;

public class LinkageMapContentProvider implements IStructuredContentProvider {

	public Object[] getElements(Object inputElement) {
		
		return ((LinkageMap)inputElement).getChromosomes().toArray();
		//return ((List)inputElement).toArray();
		
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

}
