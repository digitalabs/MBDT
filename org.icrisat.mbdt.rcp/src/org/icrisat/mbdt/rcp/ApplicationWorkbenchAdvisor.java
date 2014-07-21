package org.icrisat.mbdt.rcp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.icrisat.mbdt.gef.views.GraphicalView;
import org.icrisat.mbdt.gef.views.TargetGenotype;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.WorkbenchState;
import org.icrisat.mbdt.model.Chromosome.Chromosome;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.GenotypeModel.GenotypeMarkers;
import org.icrisat.mbdt.model.LinkageMapModel.MapMarkers;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionChromosome;
import org.icrisat.mbdt.model.sessions.SessionTargetGenotype;
import org.icrisat.mbdt.ui.views.AccessionListView;
import org.icrisat.mbdt.ui.views.SelectedAccessionsView;
import org.icrisat.mbdt.ui.views.UnScreenedMarkersView;
import org.eclipse.core.runtime.Platform;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	private static final String PERSPECTIVE_ID = "org.icrisat.mbdt.ui.perspectives.MBDTPerspective";
	LinkedHashSet<Object> selUnscreenMarkers = new LinkedHashSet<Object>();
	Object testObj = "";
	private IWorkbenchWindow window;
	HashMap<Object, Object> targetHashMap = new HashMap<Object, Object>();
	List<Object> targetGenoList = new ArrayList<Object>();
	List<EditPart> editParts = new ArrayList<EditPart>();
	List<EditPart> UnscreenEditParts = new ArrayList<EditPart>();
	Object obj = "";
    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
//System.out.println("exe.."+PlatformUI.getWorkbench().getActiveWorkbenchWindow());
        return new ApplicationWorkbenchWindowAdvisor(configurer);
    }

	public String getInitialWindowPerspectiveId() {
		return PERSPECTIVE_ID;
	}
	
	/*@Override
	public boolean preShutdown() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		String dialogbox = "Question";
		String question = "Are you sure you want to close this application?";
		
		return MessageDialog.openQuestion(shell, dialogbox , question);
	}*/
	@Override
	public void initialize(IWorkbenchConfigurer configurer) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
		    public void run() {
			    Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			    
				
	try{
		String[]  agrs= Platform.getCommandLineArgs();
		File file =  new File(agrs[0]);
		System.out.println("args..."+file.getCanonicalPath());
//			File file =  new File("C:\\0.mbdt");
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TargetGenotype.class.getName()).getViewSite().getActionBars().getStatusLineManager().setMessage(file.getAbsolutePath());
			
		FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        WorkbenchState myDeserializedObject;
       	
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
				
//			rModel.setLoadProject(projectName);
//			rModel.setLoadGeneration(generationName);
//			
//			rModel.getRootModel().setLoadProject(projectName);
//			rModel.getRootModel().setLoadGeneration(generationName);
			
				AccessionListView view1 = (AccessionListView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(AccessionListView.class.getName());
				GraphicalView gView = (GraphicalView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(GraphicalView.class.getName());
				TargetGenotype tView = (TargetGenotype) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(TargetGenotype.class.getName());
				UnScreenedMarkersView unScreenView = (UnScreenedMarkersView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(UnScreenedMarkersView.class.getName());
				
				Session.getInstance().setRootModel(rModel);
				SessionChromosome.getInstance().setChromosome(chromosome);
				List<Object> selaccSubset = new ArrayList<Object>();
					try{
						if(rModel.getLinkData().getLoadAcc().size() !=0){
//							if(rModel.getGenotype().get(0).getAccessions().get(0).getLoadAcc().size() !=0){
								selaccSubset = rModel.getLinkData().getLoadAcc();
							}
					}catch(Exception e){
					}
		       
		        try{
		        	view1.getSite().getSelectionProvider().setSelection(new StructuredSelection(selaccSubset));
		        	
		        		        	
		        	
		        	/**
		        	 ** For TargetGenotype..... 
		        	 **/
//		        	if(rModel.getLoadNextGen().isEmpty()){
			        	for(int tg = 0; tg < rModel.getGenotype().get(0).getAccessions().get(0).getSelAccforTargetCreation().size(); tg++){
				        	obj = rModel.getGenotype().get(0).getAccessions().get(0).getSelAccforTargetCreation().get(tg);
							Object obj1 = ((ArrayList)obj).get(0);
							targetGenoList.add(obj1);
						}
			        	
			        	if(rModel.getSaveType().equals("TargetDataAvailable")){
			        		try{
					        /*for (Iterator<Object> iterator = targetGenoList.iterator(); iterator.hasNext();) {
								GenotypeMarkers type = (GenotypeMarkers) iterator.next();
								type = (GenotypeMarkers)targetGenoList.get(0);
								EditPart editPart = (EditPart) gView.getGViewer().getEditPartRegistry().get((GenotypeMarkers)targetGenoList.get(0));
								editParts.add(editPart);
								gView.getGViewer().setSelection(new StructuredSelection(editParts));
							}*/
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
			        
//		        	}else{
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
			
			     
            //}
            ois.close();
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
		}
		    }
		});
	}  
}
