package org.icrisat.mbdt.ui.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.SessionManager;

public class MbdtProjectExplorerContentProvider implements
		ITreeContentProvider {
	
	RootModel project;

	public Object[] getChildren(Object parentElement) {
		List result = new ArrayList<Object>();
		
		if (parentElement instanceof RootModel) {
			result.add("Data");	
			result.add("Graphical View");
			project = ((RootModel)parentElement);
		} else if (parentElement instanceof String) {
			if (((String)parentElement).equals("Data")) {
				result.add(project.getGenotype());
				result.add(project.getLinkagemap());
			}
		}
		return result.toArray();
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {
		if (element instanceof RootModel) {
			return true;			
		} else if (element instanceof String) {
			if (((String)element).equals("Data")) {
				return true;
			}
		}
		return false;
	}

	public Object[] getElements(Object inputElement) {
		return ((SessionManager)inputElement).getIsmabProjects().toArray();
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

}
