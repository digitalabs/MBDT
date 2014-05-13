package org.icrisat.mbdt.lims.actionSets;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.icrisat.mbdt.lims.views.LimsMarkersInputView;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.LinkageMapModel.LinkageMap;
import org.icrisat.mbdt.model.sessions.Session;

public class LimsMarkersInputAction implements IWorkbenchWindowActionDelegate {
	
	private Shell shell;
	static String prevViewStatus = "";
	
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		shell = window.getShell();
	}

	public void run(IAction action) {
		RootModel rootModel = Session.getInstance().getRootmodel();
		
		if(rootModel == null){
			MessageDialog.openError(shell, "Error", "Please import the required files!");
			
		}else{
			LinkageMap map = (LinkageMap)rootModel.getLinkagemap().get(0);
			Genotype geno = (Genotype) rootModel.getGenotype().get(0);
			
			if(map.getUnScreenedcount() == 0){
				
				MessageDialog.openError(shell, "Error", "Please select the markers from the Linkage Map!");
				
			}else{
				try {
					MessageDialog.openInformation(shell, "Information", "Unscreened markers will be added to the list to be submitted to LIMS.\nAdd to the list by selecting markers from the Linkage Map.\n\nPress [ Ctrl ] key  for selecting more than one marker from Linkage Map.");
					LimsMarkersInputView view = (LimsMarkersInputView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(LimsMarkersInputView.class.getName());
					String currViewStatus = view.getViewSite().getPage().getActivePart()+"";
					
					 if(!prevViewStatus.equals(currViewStatus)){
			 			 for(int i=0;i<map.getLimsMarkers().size();i++){
//			 				System.out.println(map.getLimsMarkers().get(i)+"    "+map.getLimsMarkers().size());
			 				 if(map.getLimsMarkers().get(i).getstrUnscreenedMrks() != null){
			 					
			 					if(geno.getHeaderList().contains(map.getChromosomes().get(i).getMap_marker())){
									map.getLimsMarkers().get(i).setMarkerStatus("Screened");
									
								}else {
									map.getLimsMarkers().get(i).setMarkerStatus("UnScreened");
								}
			 					 view.setInput(map.getLimsMarkers().get(i));
			 					 
			 				 }
			 			 }
					 }else{
						 MessageDialog.openError(shell, "Error", "The selected view is already active!");
					 }
		 			prevViewStatus =  view.getViewSite().getPage().getActivePart()+"";
		 			
				} catch (PartInitException e) {
					// TODO Auto-generated catch block
				}
			}
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}
}
