package org.icrisat.mbdt.ui.actionSets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.ui.views.LinkageMapTableView;


public class LinkageMapTableAction implements IWorkbenchWindowActionDelegate {
	private Shell shell;
	
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	
	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		shell = window.getShell();
	}

	
	public void run(IAction action) {
		// TODO Auto-generated method stub
		RootModel rootModel = Session.getInstance().getRootmodel();

		if(rootModel == null){
			MessageDialog.openError(shell, "Error", "Please import the required files!");
			
		} else{
			try {
				LinkageMapTableView view = (LinkageMapTableView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(LinkageMapTableView.class.getName());
				 	view.setInput(rootModel.getLinkagemap().get(0));
	 				 				 			
			} catch (PartInitException e) {
			}
		}
		
	}

	
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
		
	}

}
