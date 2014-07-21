	package org.icrisat.mbdt.ui.actions;

	import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.generationcp.middleware.domain.mbdt.SelectedGenotypeEnum;
import org.generationcp.middleware.exceptions.ConfigException;
import org.generationcp.middleware.exceptions.MiddlewareQueryException;
import org.generationcp.middleware.manager.Database;
import org.generationcp.middleware.manager.DatabaseConnectionParameters;
import org.generationcp.middleware.manager.ManagerFactory;
import org.generationcp.middleware.manager.Operation;
import org.generationcp.middleware.manager.WorkbenchDataManagerImpl;
import org.generationcp.middleware.manager.api.GenotypicDataManager;
import org.generationcp.middleware.manager.api.GermplasmDataManager;
import org.generationcp.middleware.manager.api.MBDTDataManager;
import org.generationcp.middleware.manager.api.WorkbenchDataManager;
import org.generationcp.middleware.pojos.Germplasm;
import org.generationcp.middleware.pojos.gdms.Marker;
import org.generationcp.middleware.pojos.gdms.MarkerInfo;
import org.generationcp.middleware.pojos.mbdt.MBDTGeneration;
import org.generationcp.middleware.pojos.mbdt.MBDTProjectData;
import org.generationcp.middleware.pojos.mbdt.SelectedGenotype;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.WorkbenchState;
import org.icrisat.mbdt.model.Chromosome.Chromosome;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.MBDTModel;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.TargetGenotype.Parents;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionChromosome;
import org.icrisat.mbdt.model.sessions.SessionTargetGenotype;
import org.icrisat.mbdt.ui.dialog.IsmabSaveAsDialog;

	public class SaveDatabaseAction implements IWorkbenchWindowActionDelegate {
		
		public static final String ID = "org.icrisat.mbdt.ui.SaveDatabaseAction";
		private IWorkbenchWindow window;
		DatabaseConnectionParameters local, central, workbench;
		ManagerFactory factory;
		MBDTDataManager mbdtmanager;
		GenotypicDataManager manager;
		GermplasmDataManager gmanager;
		MBDTProjectData project;
		MBDTGeneration generation;
		WorkbenchDataManager wdm;
		public void run(IAction action) {
			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			
			//Creating our own Dialog Box.....
			IsmabSaveAsDialog saveAsdialog = new IsmabSaveAsDialog(shell);
			 
			 
			if(saveAsdialog.open()== Window.OK){
				String projectName = saveAsdialog.getTextData();
				String generationName = saveAsdialog.getText1Data();
				
				if(Session.getInstance().getRootmodel().getLoadNextGen().isEmpty()){
					   
					RootModel rootmodel = RootModel.getRootModel();
					RootModel rootModel1;
					WorkbenchState myObject = new WorkbenchState ();
					
					LinkageData linkData;
					Qtl_MapData qtlMapData;
					Chromosome chromosome = SessionChromosome.getInstance().getChromosome();
					TargetGeno targetGeno = SessionTargetGenotype.getInstance().getTargetGeno();
					
					if(rootmodel.getLoadFlag() == null){
						 rootModel1 = Session.getInstance().getRootmodel();
						linkData = LinkageData.getLinkageData();
						qtlMapData = Qtl_MapData.getQtl_MapData();
						
						if(targetGeno == null){
							rootModel1.setSaveType("NoTargetData");
						}else{
							if(targetGeno.getParents().size()== 0) {
								rootModel1.setSaveType("NoTargetData");
							}else{
								rootModel1.setSaveType("TargetDataAvailable");
							}
						}
						
						myObject.setRootModelSer(rootModel1);
					}else{
						 rootModel1 = Session.getInstance().getRootmodel();
						linkData = rootModel1.getLinkData();
						qtlMapData = rootModel1.getQtlMapData();
						if(targetGeno == null){
							rootModel1.setSaveType("NoTargetData");
						}else{
							if(targetGeno.getParents().size()== 0) {
								rootModel1.setSaveType("NoTargetData");
							}else{
								rootModel1.setSaveType("TargetDataAvailable");
							}
						}
						
						myObject.setRootModelSer(rootModel1);
						
				        
					}
					//---setting project details. 
					 try {
						 String url = Platform.getLocation().toString().substring(0, Platform.getLocation().toString().lastIndexOf("/")+1);
						 local = new DatabaseConnectionParameters(url+"DatabaseConfig.properties","local");
						 central = new DatabaseConnectionParameters(url+"DatabaseConfig.properties","central");
						 factory = new ManagerFactory(local, central);
						 mbdtmanager = factory.getMbdtDataManager();
						 manager = factory.getGenotypicDataManager();
						 gmanager = factory.getGermplasmDataManager();
						 generationName = saveAsdialog.getText1Data();
						 List did = new ArrayList();
						 did.add(rootModel1.getGenotypeDataset());
						 project = new MBDTProjectData();
						 project.setEmail(manager.getDatasetDetailsByDatasetIds(did).get(0).getEmail());
						 project.setInstitute(manager.getDatasetDetailsByDatasetIds(did).get(0).getInstitute());
						 project.setMapID(rootModel1.getMapDataset());
						 project.setPhenoDatasetID(rootModel1.getPhenoDataset());
						 project.setPrincipalInvestigator(manager.getDatasetDetailsByDatasetIds(did).get(0).getPrincipalInvestigator());
						 try {
							 System.out.println(MBDTModel.getMBDTModel().getWorkbenchDataManager().getWorkbenchRuntimeData().getUserId());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 project.setUserID(1);
						 project.setProjectName(projectName);
						 project.setQtlID(rootModel1.getQtlDataset());
						 int proj_id = mbdtmanager.setProjectData(project);
						
					//---end of project details
						
					//----start generation data--	 
						generation = new MBDTGeneration();	
						MBDTGeneration gen = new MBDTGeneration();
						generation.setProject(project);
						generation.setGenerationName(generationName);
						generation.setGenotypeDatasetID(rootModel1.getGenotypeDataset());
						gen = mbdtmanager.setGeneration(proj_id, generation);
						int g_id = gen.getGenerationID();
					//-----end generation data--
						
					//-----start foreground markers
						try {
							List<String> foregroundMarkers = linkData.getForegroundMarker();
							List<Integer> mids =new ArrayList<Integer>();
							List<Marker> minfo = new ArrayList<Marker>();
							minfo = manager.getMarkersByMarkerNames(foregroundMarkers, 0, foregroundMarkers.size(), Database.LOCAL);
							for(int i=0;i<minfo.size(); i++){
								if(!mids.contains(minfo.get(i).getMarkerId()))
								mids.add(minfo.get(i).getMarkerId());
							}
							minfo = manager.getMarkersByMarkerNames(foregroundMarkers, 0, foregroundMarkers.size(), Database.CENTRAL);
							for(int i=0;i<minfo.size(); i++){
								if(!mids.contains(minfo.get(i).getMarkerId()))
								mids.add(minfo.get(i).getMarkerId());
							}
							
							mbdtmanager.setMarkerStatus(g_id, mids);
						} catch (Exception e) {
							// TODO Auto-generated catch block
//							e.printStackTrace();
						}
						
					//-----end foreground markers
						
					//---start selected accessions
//						System.out.println(rootModel1.getGenotype().get(0).getAccessions().get(0).getSelectedAccessions1().size()+".."+rootModel1.getGenotype().get(0).getAccessions().get(0).getSelectedAccessions().size());
						List<Accessions> selacc = linkData.getSelectedAccessions();
						List<Integer> glist = new ArrayList<Integer>();
						for( int i=0; i<selacc.size(); i++){
							glist.add(gmanager.getGermplasmByName(selacc.get(i).getName(), 0, (int) gmanager.countGermplasmByName(selacc.get(i).getName(),Operation.EQUAL),  Operation.EQUAL).get(0).getGid());
						}
						mbdtmanager.setSelectedAccessions(g_id, glist);
					//---end of Selected Accessions
						
					//---start of parent data
						SelectedGenotype sg = new SelectedGenotype();
						glist = new ArrayList<Integer>();
						List<Integer> dlist = new ArrayList<Integer>();
						List<Parents> p = TargetGeno.getTargetGeno().getParents();
						for(int i = 0; i < p.size(); i++){
						if(p.get(i).getType().equals("Donor")){
							dlist.add(gmanager.getGermplasmByName(p.get(i).getParent(), 0, (int) gmanager.countGermplasmByName(p.get(i).getParent(), Operation.EQUAL),  Operation.EQUAL).get(0).getGid());
							mbdtmanager.setParentData(g_id, SelectedGenotypeEnum.D, dlist);
						}else if(p.get(i).getType().equals("Recurrent")){
							glist.add(gmanager.getGermplasmByName(p.get(i).getParent(), 0, (int) gmanager.countGermplasmByName(p.get(i).getParent(), Operation.EQUAL),  Operation.EQUAL).get(0).getGid());
							mbdtmanager.setParentData(g_id, SelectedGenotypeEnum.R, glist);
						}
						}
					//---end of parent data	
					} catch (ConfigException e) {
						e.printStackTrace();
					} catch (MiddlewareQueryException e) {
						e.printStackTrace();
					}catch(Exception e){
						
					}
				}
				
			}  
		
		}

		public void dispose() {
		}

		public void init(IWorkbenchWindow window) {
			this.window = window;
		}

		public void selectionChanged(IAction action, ISelection selection) {
		}

	}
