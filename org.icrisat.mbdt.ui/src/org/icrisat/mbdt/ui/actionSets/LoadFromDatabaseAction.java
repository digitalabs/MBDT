package org.icrisat.mbdt.ui.actionSets;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.ui.wizards.LoadFromDatabaseWizard;

public class LoadFromDatabaseAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		this.window = window;

	}

	public void run(IAction action) {
		// TODO Auto-generated method stub
		RootModel rootModel = Session.getInstance().getRootmodel();
		
		try {			 
			LoadFromDatabaseWizard wizard=new LoadFromDatabaseWizard();
			WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
			dialog.create();
			dialog.open();		
				 			
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
