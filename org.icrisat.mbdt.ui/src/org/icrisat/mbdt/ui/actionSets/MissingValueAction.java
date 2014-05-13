package org.icrisat.mbdt.ui.actionSets;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.icrisat.mbdt.ui.views.MissingValuesView;

public class MissingValueAction implements IWorkbenchWindowActionDelegate {

	private Shell shell;
	
	
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
			
		} else{
			try {
				 MissingValuesView view = (MissingValuesView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(MissingValuesView.class.getName());
				 List<String> missingMarkers = new ArrayList<String>();
				 for(int i = 0; i < rootModel.getGenotype().get(0).getAccessions().size(); i++){
					 missingMarkers = new ArrayList<String>();
					 for(int j = 0; j < rootModel.getGenotype().get(0).getAccessions().get(i).getGmark().size(); j++){
						 if(rootModel.getGeneration().equals("First")){
							 if(rootModel.getGenotype().get(0).getDataCheck().equals("Char")){
								 if (rootModel.getGenotype().get(0).getAccessions().get(i).getAlleValues().get(j).equals("-")){
				 						missingMarkers.add(rootModel.getGenotype().get(0).getAccessions().get(i).getGmark().get(j));
				 						rootModel.getGenotype().get(0).getAccessions().get(i).setMissingMarkers(missingMarkers);
								 } 
							 }
							 else{ if (rootModel.getGenotype().get(0).getAccessions().get(i).getAlleValues().get(j).equals("0")){
		 						missingMarkers.add(rootModel.getGenotype().get(0).getAccessions().get(i).getGmark().get(j));
		 						rootModel.getGenotype().get(0).getAccessions().get(i).setMissingMarkers(missingMarkers);
							 }
							 }
						 }else{
							 try{
								 if (rootModel.getGenotype().get(0).getAccessions().get(i).getAlleValues().get(j).equals("-")){
									 HashMap monomarkers=new HashMap();
									 monomarkers=rootModel.getLinkagemap().get(0).getLimsMarkers().get(0).getMonomorphicMarkers();
									 if(!(monomarkers==null)){
										 if(monomarkers.get(rootModel.getGenotype().get(0).getAccessions().get(i).getGmark().get(j)).equals("Monomorphic")){

										 }else{
											 missingMarkers.add(rootModel.getGenotype().get(0).getAccessions().get(i).getGmark().get(j));
											 rootModel.getGenotype().get(0).getAccessions().get(i).setMissingMarkers(missingMarkers);	
										 }
									 }else{
										 missingMarkers.add(rootModel.getGenotype().get(0).getAccessions().get(i).getGmark().get(j));
										 rootModel.getGenotype().get(0).getAccessions().get(i).setMissingMarkers(missingMarkers);
									 }
								 }
							 }catch(Exception e){
							 }
						 } 
					 }
					 if(missingMarkers.size() > 0){
						 view.setInput(rootModel.getGenotype().get(0).getAccessions().get(i));
					 }
	 			 }
				 rootModel.getLinkagemap().get(0);
					 			
			} catch (PartInitException e) {
				// TODO Auto-generated catch block
			}
		}
		
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
	}

}
