package org.icrisat.mbdt.ui.views;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class SelectedAccessionContentProvider implements
		IStructuredContentProvider {

	public Object[] getElements(Object inputElement) {
		return ((List)inputElement).toArray();
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}
}
