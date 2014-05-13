package org.icrisat.mbdt.ui.actionSets;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.icrisat.mbdt.gef.views.GraphicalView;

public class GraphicalEditorAction implements IWorkbenchWindowActionDelegate {

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		
	}

	public void run(IAction action) {
		IViewPart iView= PlatformUI.getWorkbench().getActiveWorkbenchWindow ().getActivePage().findView(GraphicalView.class.getName());   	
		PlatformUI.getWorkbench().getActiveWorkbenchWindow ().getActivePage().hideView(iView);   	
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow ().getActivePage().showView(GraphicalView.class.getName());
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
		}   	
//		System.out.println("Refresh GraphicalView= "+GraphicalView.class.getName());
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
