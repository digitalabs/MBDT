package org.icrisat.mbdt.lims.actionSets;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.icrisat.mbdt.lims.views.CreatingLIMSSubSetView;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.sessions.Session;

public class LimsInputAction implements IWorkbenchWindowActionDelegate {
	
	private Shell shell;
	static String prevAccViewStatus = "";
	
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		shell = window.getShell();
	}

	public void run(IAction action) {
		
		RootModel rootModel = Session.getInstance().getRootmodel();
		
		//Checking whether root model is empty.....
		if(rootModel == null){		
			MessageDialog.openError(shell, "Error", "Please import the required files!");
			
		}else{		
		
			try {				
//				MessageDialog.openInformation(shell, "Information", "For Lims input you can have Accessions from Selected Subset\n and You can also select from main List!");
				MessageDialog.openInformation(shell, "Information", "LIMS input file will be generated from selected germplasm identifiers.\nYou may add identifiers to this list!");
				CreatingLIMSSubSetView view = (CreatingLIMSSubSetView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(CreatingLIMSSubSetView.class.getName());
				String currAccViewStatus = view.getViewSite().getPage().getActivePart()+"";
				
				
				if(!prevAccViewStatus.equals(currAccViewStatus)){
					for(int i = 0; i < rootModel.getGenotype().get(0).getAccessions().size(); i++){
						if(rootModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions1().size() != 0){
							
							view.setInput(rootModel.getGenotype().get(0).getAccessions().get(i));
							
						}
					}
				}else{
					 MessageDialog.openError(shell, "Error", "The selected view is already active!");
				}
				prevAccViewStatus =  view.getViewSite().getPage().getActivePart()+"";
				
			} catch (PartInitException e) {
			}
		
		}		

	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
