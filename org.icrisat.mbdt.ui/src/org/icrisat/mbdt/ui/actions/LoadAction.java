package org.icrisat.mbdt.ui.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.icrisat.mbdt.gef.views.GraphicalView;
import org.icrisat.mbdt.gef.views.TargetGenotype;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.WorkbenchState;
import org.icrisat.mbdt.model.WorkbenchStateMultipleLoading;
import org.icrisat.mbdt.model.Chromosome.Chromosome;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.GenotypeModel.GenotypeMarkers;
import org.icrisat.mbdt.model.LinkageMapModel.MapMarkers;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionChromosome;
import org.icrisat.mbdt.model.sessions.SessionTargetGenotype;
import org.icrisat.mbdt.ui.dialog.IsmabLoadDialog;
import org.icrisat.mbdt.ui.views.AccessionListView;
import org.icrisat.mbdt.ui.views.LinkageMapTableView;
import org.icrisat.mbdt.ui.views.SelectedAccessionsView;
import org.icrisat.mbdt.ui.views.UnScreenedMarkersView;

/**
 * SwapnaNair February 2010
 **/

public class LoadAction implements IWorkbenchWindowActionDelegate{

	public static final String ID = "org.icrisat.mbdt.ui.actions.LoadAction";
	private IWorkbenchWindow window;
	HashMap<Object, Object> targetHashMap = new HashMap<Object, Object>();
	List<Object> targetGenoList = new ArrayList<Object>();
	List<EditPart> editParts = new ArrayList<EditPart>();
	List<EditPart> UnscreenEditParts = new ArrayList<EditPart>();
	Object obj = "";
	String mainFolder = "";
	String folder = "";
	String subFolder = "";
	LinkedHashSet<Object> selUnscreenMarkers = new LinkedHashSet<Object>();
	Object testObj = "";
	
	
	
	public void run(IAction action) {
			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		
			//Creating our own Dialog Box for Loading.....
			IsmabLoadDialog loadDialog = new IsmabLoadDialog(shell);
			
			try {
				if(loadDialog.open()== Window.OK){
					
					if(Session.getInstance().getRootmodel() !=null){
						//PlatformUI.getWorkbench().close();
						PlatformUI.getWorkbench().restart();
						
						IWorkspace workspace = ResourcesPlugin.getWorkspace(); 
						IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
						IProject myWebProject = myWorkspaceRoot.getProject();
						
						WorkbenchStateMultipleLoading myObject = new WorkbenchStateMultipleLoading();
						myObject.setProjectName(loadDialog.getTextData());
						myObject.setGenerationName(loadDialog.getText1Data());
						
						FileOutputStream fos = new FileOutputStream("MbdtLoadConfigFile.ser");
				        ObjectOutputStream oos;
						oos = new ObjectOutputStream(fos);
						oos.writeObject(myObject);
			            oos.flush();
			            oos.close();
						
					}
					
					
					String projectName = loadDialog.getTextData();
					String generationName = loadDialog.getText1Data();
					mainFolder = Platform.getLocation().getDevice()+"//MBDT_PROJECTSFiles";
					folder = mainFolder+"//"+projectName;
					subFolder = folder+"//"+generationName;
					
					FileInputStream fis = new FileInputStream(subFolder);
		            ObjectInputStream ois = new ObjectInputStream(fis);
		            WorkbenchState myDeserializedObject;
					try {
						
						myDeserializedObject = (WorkbenchState)ois.readObject();
						RootModel rModel = myDeserializedObject.getRootModelSer();
						LinkageData linkData =  myDeserializedObject.getLinkData();
						Chromosome chromosome = myDeserializedObject.getChromsome();
						rModel.setLinkData(linkData);
						rModel.getRootModel().setLinkData(linkData);
						long theSUID = myDeserializedObject.getSerialversionuid();
						Qtl_MapData qtlMapData = myDeserializedObject.getQtlMapData();
						rModel.setQtlMapData(qtlMapData);
						rModel.getRootModel().setQtlMapData(qtlMapData);
						
						try {
							
						rModel.setLoadFlag(true);
						rModel.getRootModel().setLoadFlag(true);
						
						rModel.setLoadProject(projectName);
						rModel.setLoadGeneration(generationName);
						
						rModel.getRootModel().setLoadProject(projectName);
						rModel.getRootModel().setLoadGeneration(generationName);
						
							AccessionListView view1 = (AccessionListView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(AccessionListView.class.getName());
							GraphicalView gView = (GraphicalView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(GraphicalView.class.getName());
							TargetGenotype tView = (TargetGenotype) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(TargetGenotype.class.getName());
							UnScreenedMarkersView unScreenView = (UnScreenedMarkersView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(UnScreenedMarkersView.class.getName());
							
							Session.getInstance().setRootModel(rModel);
							SessionChromosome.getInstance().setChromosome(chromosome);
							List<Object> selaccSubset = new ArrayList<Object>();
								try{
									if(rModel.getLinkData().getLoadAcc().size() !=0){
//										if(rModel.getGenotype().get(0).getAccessions().get(0).getLoadAcc().size() !=0){
											selaccSubset = rModel.getLinkData().getLoadAcc();
										}
								}catch(Exception e){
								}
					       
					        try{
					        	view1.getSite().getSelectionProvider().setSelection(new StructuredSelection(selaccSubset));
					        	
					        	
					        	/**
					        	 ** For unscreened Markers....
					        	 **/
					        	for(int uns = 0; uns < rModel.getLinkagemap().get(0).getSelectedUnscreenMarkers().size(); uns++){
					        		testObj = rModel.getLinkagemap().get(0).getSelectedUnscreenMarkers().get(uns);					        		
					        		selUnscreenMarkers.add(testObj);
					        	}
					        	
					        	 for (Iterator<Object> iterator = selUnscreenMarkers.iterator(); iterator.hasNext();) {
					        		MapMarkers Unscreenmarker = (MapMarkers) iterator.next();
					        		EditPart UnscreeneditPart = (EditPart) gView.getGViewer().getEditPartRegistry().get(Unscreenmarker);
					        		UnscreenEditParts.add(UnscreeneditPart);										
									gView.getGViewer().setSelection(new StructuredSelection(UnscreenEditParts));
					        	 }
					        	
					        	
					        	
					        	/**
					        	 ** For TargetGenotype..... 
					        	 **/
						        	for(int tg = 0; tg < rModel.getGenotype().get(0).getAccessions().get(0).getSelAccforTargetCreation().size(); tg++){
							        	obj = rModel.getGenotype().get(0).getAccessions().get(0).getSelAccforTargetCreation().get(tg);
										Object obj1 = ((ArrayList)obj).get(0);
										targetGenoList.add(obj1);
									}
						        	
						        	if(rModel.getSaveType().equals("TargetDataAvailable")){
						        		try{
								        for (Iterator<Object> iterator = targetGenoList.iterator(); iterator.hasNext();) {
											GenotypeMarkers type = (GenotypeMarkers) iterator.next();
											type = (GenotypeMarkers)targetGenoList.get(0);
											EditPart editPart = (EditPart) gView.getGViewer().getEditPartRegistry().get((GenotypeMarkers)targetGenoList.get(0));
											editParts.add(editPart);
											gView.getGViewer().setSelection(new StructuredSelection(editParts));
										}
						        		}catch(Exception e){
						        		}
								        TargetGeno targetGeno = myDeserializedObject.getTargetGeno();
							       
										SessionTargetGenotype.getInstance().setTargetGeno(targetGeno);
									}else{
										if(SessionTargetGenotype.getInstance().getTargetGeno() != null){
											if(!(SessionTargetGenotype.getInstance().getTargetGeno().getParents().isEmpty())) {
												SessionTargetGenotype.getInstance().getTargetGeno().getParents().clear();
												if(SessionTargetGenotype.getInstance().getTargetGeno().getParents().size()== 0) {
													SessionTargetGenotype.getInstance().setTargetGeno(SessionTargetGenotype.getInstance().getTargetGeno());
												}
											}
										}
									}
						        
					        		if(!rModel.getLoadNextGen().isEmpty()){
					        		//For hiding views...
					    			IViewPart iView = PlatformUI.getWorkbench().getActiveWorkbenchWindow ().getActivePage().findView(TargetGenotype.class.getName());
					    			IViewPart iView1 = PlatformUI.getWorkbench().getActiveWorkbenchWindow ().getActivePage().findView(SelectedAccessionsView.class.getName());
					    			
					    			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(iView);
					    			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(iView1);
					    			
					    			Session.getInstance().setRootModel(rModel);
					        	}
					        }catch(Exception e){
					        }
					        
					        
					        Session.getInstance().setRootModel(rModel);
					        
						} catch (PartInitException e) {
						}
						
						     
			            ois.close();
					} catch (ClassNotFoundException e) {
					}
				}  

			} catch (IOException e) {
				e.printStackTrace();
			}
            

	}


	public void dispose() {
		// TODO Auto-generated method stub
		
	}


	public void init(IWorkbenchWindow window) {
		this.window = window; // cache the window object in which action
		// delegate is operating
	}


	

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
	}
}
