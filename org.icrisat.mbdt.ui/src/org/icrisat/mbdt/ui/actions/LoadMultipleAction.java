package org.icrisat.mbdt.ui.actions;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.icrisat.mbdt.gef.views.GraphicalView;
import org.icrisat.mbdt.gef.views.TargetGenotype;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.WorkbenchState;
import org.icrisat.mbdt.model.WorkbenchStateMultipleLoading;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.GenotypeModel.GenotypeMarkers;
import org.icrisat.mbdt.model.LinkageMapModel.MapMarkers;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionTargetGenotype;
import org.icrisat.mbdt.ui.views.AccessionListView;
import org.icrisat.mbdt.ui.views.SelectedAccessionsView;

public class LoadMultipleAction extends Action {
	
	public static final String ID = "org.icrisat.mbdt.ui.LoadMultipleAction";
	private IWorkbenchWindow window;
	HashMap<Object, Object> targetHashMap = new HashMap<Object, Object>();
	List<Object> targetGenoList = new ArrayList<Object>();
	List<EditPart> editParts = new ArrayList<EditPart>();
	List<EditPart> UnscreenEditParts = new ArrayList<EditPart>();
	Object obj = "";
	String mainFolder = "";
	String folder = "";
	String subFolder = "";
//	List<Object> selUnscreenMarkers = new ArrayList<Object>();
	LinkedHashSet<Object> selUnscreenMarkers = new LinkedHashSet<Object>();
	Object testObj = "";
	
	public LoadMultipleAction(IWorkbenchWindow window) {
		super();
		this.window = window;
		setId(ID);
		
	}
	
	
	public void run() {
		FileInputStream fisload;
		try {
			fisload = new FileInputStream("MbdtLoadConfigFile.ser");
			
			
			ObjectInputStream oisload = new ObjectInputStream(fisload);
			
			WorkbenchStateMultipleLoading myDeserializedObjectload;
			
			myDeserializedObjectload = (WorkbenchStateMultipleLoading)oisload.readObject();
			String projname = myDeserializedObjectload.getProjectName();
			String genname = myDeserializedObjectload.getGenerationName();
			
//			System.out.println(projname+":::::::"+genname);
			
			String projectName = projname;
			String generationName = genname;
			
			mainFolder = Platform.getLocation().getDevice()+"//MBDT_PROJECTSFiles";
			folder = mainFolder+"//"+projectName;
			subFolder = folder+"//"+generationName;
//			System.out.println("subFolder :"+subFolder);
			
			if(new File(subFolder).exists()){
				
			
			
//			FileInputStream fis = new FileInputStream(subFolder+"//myObject.ser");
			FileInputStream fis = new FileInputStream(subFolder);
            ObjectInputStream ois = new ObjectInputStream(fis);
            WorkbenchState myDeserializedObject;
            
			try {
				myDeserializedObject = (WorkbenchState)ois.readObject();				
				RootModel rModel = myDeserializedObject.getRootModelSer();
				LinkageData linkData =  myDeserializedObject.getLinkData();
				rModel.setLinkData(linkData);
				rModel.getRootModel().setLinkData(linkData);
				
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
					
					
					Session.getInstance().setRootModel(rModel);
					List<Object> selaccSubset = new ArrayList<Object>(); 
					try{					
					//for(int acc = 0;acc<rModel.getGenotype().get(0).getAccessions().size();acc++){
						if(rModel.getLinkData().getLoadAcc().size() !=0){
//						if(rModel.getGenotype().get(0).getAccessions().get(0).getLoadAcc().size() !=0){
							selaccSubset = rModel.getLinkData().getLoadAcc();
						}
					//}
					}catch(Exception e){
					}
			        try{
			        	view1.getSite().getSelectionProvider().setSelection(new StructuredSelection(selaccSubset));
			        	
			        	//For unscreened Markers....
			        	for(int uns = 0; uns < rModel.getLinkagemap().get(0).getSelectedUnscreenMarkers().size(); uns++){
			        		testObj = rModel.getLinkagemap().get(0).getSelectedUnscreenMarkers().get(uns);					        		
			        		selUnscreenMarkers.add(testObj);
			        	}
//			        	System.out.println("selUnscreenMarkers :"+selUnscreenMarkers);
			        	
			        	 for (Iterator<Object> iterator = selUnscreenMarkers.iterator(); iterator.hasNext();) {
								//System.out.println("iterator.next() ::::"+(GenotypeMarkers)get);
								//viewer.getEditPartRegistry();
			        		MapMarkers Unscreenmarker = (MapMarkers) iterator.next();
			        		EditPart UnscreeneditPart = (EditPart) gView.getGViewer().getEditPartRegistry().get(Unscreenmarker);
			        		UnscreenEditParts.add(UnscreeneditPart);										
							gView.getGViewer().setSelection(new StructuredSelection(UnscreenEditParts));
			        	 }			        	
			        	
			        	/**
			        	 * For TargetGenotype....
			        	 **/ 
			        	 if(rModel.getLoadNextGen().isEmpty()){ 
				        	for(int tg = 0; tg < rModel.getGenotype().get(0).getAccessions().get(0).getSelAccforTargetCreation().size(); tg++){
					        	obj = rModel.getGenotype().get(0).getAccessions().get(0).getSelAccforTargetCreation().get(tg);
	//								System.out.println("OBJECT :"+obj);
								Object obj1 = ((ArrayList)obj).get(0);
								targetGenoList.add(obj1);
							}
				        	if(rModel.getSaveType().equals("TargetDataAvailable")){
				        		try{
					        		for (Iterator<Object> iterator = targetGenoList.iterator(); iterator.hasNext();) {
									
									GenotypeMarkers type = (GenotypeMarkers) iterator.next();
									
									EditPart editPart = (EditPart) gView.getGViewer().getEditPartRegistry().get(type);
									editParts.add(editPart);
									
									gView.getGViewer().setSelection(new StructuredSelection(editParts));
																
				        		}
				        		}catch(Exception e){
				        		}
					        	TargetGeno targetGeno = myDeserializedObject.getTargetGeno();
					       
							
								SessionTargetGenotype.getInstance().setTargetGeno(targetGeno);
							}else{
	//							System.out.println("target ;;;;;;;;"+SessionTargetGenotype.getInstance().getTargetGeno().getParents().isEmpty());
								if(SessionTargetGenotype.getInstance().getTargetGeno() != null){
									if(!(SessionTargetGenotype.getInstance().getTargetGeno().getParents().isEmpty())) {
										SessionTargetGenotype.getInstance().getTargetGeno().getParents().clear();
										if(SessionTargetGenotype.getInstance().getTargetGeno().getParents().size()== 0) {
											SessionTargetGenotype.getInstance().setTargetGeno(SessionTargetGenotype.getInstance().getTargetGeno());
											//Session.getInstance().setRootModel(rModel);
										}
									}
								}
	//							SessionTargetGenotype.getInstance().setTargetGeno(targetGeno);
							}
			        	 }else{
				        		//For hiding views...
				    			//IViewPart iView= PlatformUI.getWorkbench().getActiveWorkbenchWindow ().getActivePage().findView(TargetGenotype.class.getName());
				    			//IViewPart iView1 = PlatformUI.getWorkbench().getActiveWorkbenchWindow ().getActivePage().findView(SelectedAccessionsView.class.getName());
				    			//System.out.println(iView+"::::");
				    			//System.out.println(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getWorkbench().getActiveWorkbenchWindow().getActivePage());
				    			
				    			try{
				    				//TargetGenotype ttview = (TargetGenotype)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(TargetGenotype.class.getName());
					    			//ttview.getSite().getPage().hideView(ttview);
					    			//SelectedAccessionsView sview = (SelectedAccessionsView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(SelectedAccessionsView.class.getName());
				    			//sview.getSite().getPage().hideView(sview);
				    			
//				    			PlatformUI.getWorkbench().getActiveWorkbenchWi;ndow().getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(iView);
				    				//PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(iView);
				    				//PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(iView1);
				    				Session.getInstance().setRootModel(rModel);
				    			}catch(Exception e){
				    				
				    			}
				    			
				        }
			        }catch(Exception e){
			        }
			        
			        
			        //Session.getInstance().setRootModel(rModel);
			        
				} catch (PartInitException e) {
					// TODO Auto-generated catch block
				}
	            //}
	            ois.close();
	            
	            //Here clearing the contents of the file...
	            FileOutputStream ff = new FileOutputStream("MbdtLoadConfigFile.ser");
	            ff.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
			}
			
			}
			
			
			
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
		}catch (IOException e) {
			// TODO Auto-generated catch block
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
		}
	}
	
	

}
