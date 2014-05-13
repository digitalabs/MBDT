package org.icrisat.mbdt.ui.actionSets;


import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.ui.wizards.FilesImportWizard;

public class ExitAction implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;
	private IWorkbenchAction exitAction;
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	
	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		this.window = window; // cache the window object in which action
		// delegate is operating
	}

	
	public void run(IAction action) {
		try {
			exitAction = ActionFactory.QUIT.create(window);
			exitAction.run();
						 			
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}
	

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
	}

}

