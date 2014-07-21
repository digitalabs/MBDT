package org.icrisat.mbdt.ui.actions;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

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
import org.generationcp.middleware.exceptions.ConfigException;
import org.generationcp.middleware.exceptions.MiddlewareQueryException;
import org.generationcp.middleware.manager.DatabaseConnectionParameters;
import org.generationcp.middleware.manager.ManagerFactory;
import org.generationcp.middleware.manager.api.GenotypicDataManager;
import org.generationcp.middleware.manager.api.GermplasmDataManager;
import org.generationcp.middleware.manager.api.MBDTDataManager;
import org.generationcp.middleware.manager.api.StudyDataManager;
import org.generationcp.middleware.pojos.gdms.MarkerIdMarkerNameElement;
import org.generationcp.middleware.pojos.gdms.MarkerInfo;
import org.generationcp.middleware.pojos.mbdt.MBDTGeneration;
import org.generationcp.middleware.pojos.mbdt.MBDTProjectData;
import org.generationcp.middleware.pojos.mbdt.SelectedGenotype;
import org.generationcp.middleware.pojos.mbdt.SelectedMarker;
import org.icrisat.mbdt.gef.views.GraphicalView;
import org.icrisat.mbdt.gef.views.TargetGenotype;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.WorkbenchState;
import org.icrisat.mbdt.model.WorkbenchStateMultipleLoading;
import org.icrisat.mbdt.model.Chromosome.Chromosome;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.GenotypeModel.GenotypeMarkers;
import org.icrisat.mbdt.model.LinkageMapModel.LimsMarkers;
import org.icrisat.mbdt.model.LinkageMapModel.MapMarkers;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;
import org.icrisat.mbdt.model.loader.GenotypeDatasetLoader;
import org.icrisat.mbdt.model.loader.LinkageMapDataLoader1;
import org.icrisat.mbdt.model.loader.LinkageMapDatasetLoader;
import org.icrisat.mbdt.model.loader.PhenotypeDatasetLoader;
import org.icrisat.mbdt.model.loader.QTLDatasetLoader;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionChromosome;
import org.icrisat.mbdt.model.sessions.SessionTargetGenotype;
import org.icrisat.mbdt.ui.dialog.MbdtLoadProjectDialog;
import org.icrisat.mbdt.ui.views.AccessionListView;
import org.icrisat.mbdt.ui.views.LinkageMapTableView;
import org.icrisat.mbdt.ui.views.SelectedAccessionsView;
import org.icrisat.mbdt.ui.views.UnScreenedMarkersView;


public class LoadProjectAction implements IWorkbenchWindowActionDelegate{

	public static final String ID = "org.icrisat.mbdt.ui.actions.LoadProjectAction";
	private IWorkbenchWindow window;
	HashMap<Object, Object> targetHashMap = new HashMap<Object, Object>();
	List<Object> targetGenoList = new ArrayList<Object>();
	List<EditPart> editParts = new ArrayList<EditPart>();
	List<EditPart> UnscreenEditParts = new ArrayList<EditPart>();
	Object obj = "";
	String mainFolder = "";
	String folder = "";
	String subFolder = "";
	DatabaseConnectionParameters local, central;
	ManagerFactory factory;
	MBDTDataManager mbdtmanager;
	GenotypicDataManager manager;
	GermplasmDataManager gmanager;
	StudyDataManager phenomanager;
	MBDTProjectData project;
	MBDTGeneration generation;
	int pid=0, gid=0;
	Object testObj = "";



	public void run(IAction action) {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

		//Creating our own Dialog Box for Loading.....
		MbdtLoadProjectDialog loadDialog = new MbdtLoadProjectDialog(shell);

		try {
			if(loadDialog.open()== Window.OK){

				String projectName = loadDialog.getTextData();
				String generationName = loadDialog.getText1Data();


				try {
					String url = Platform.getLocation().toString().substring(0, Platform.getLocation().toString().lastIndexOf("/")+1);
					local = new DatabaseConnectionParameters(url+"DatabaseConfig.properties","local");
					central = new DatabaseConnectionParameters(url+"DatabaseConfig.properties","central");
					factory = new ManagerFactory(local, central);
					mbdtmanager = factory.getMbdtDataManager();
					manager = factory.getGenotypicDataManager();
					gmanager = factory.getGermplasmDataManager();
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ConfigException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Chromosome chromosome = Chromosome.getChromosome();

				try {
					project = new MBDTProjectData();
					pid = mbdtmanager.getProjectIDByProjectName(projectName);
					project = mbdtmanager.getProjectData(pid);
					try {
						System.out.println(project.getProjectID());
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					Connection connection;
					Statement statement;
					ResultSet resultSet;
					String name ="";
					try {
						gid = mbdtmanager.getGenerationIDByGenerationName(generationName, pid);
						name = (manager.getDatasetById((mbdtmanager.getGeneration(gid)).getGenotypeDatasetID())).getDatasetName();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					GenotypeDatasetLoader gLoader = new GenotypeDatasetLoader();
					RootModel rModel = gLoader.loadGenotype(name);				
					rModel.setGeneration("First");
					Session.getInstance().setRootModel(rModel);				
					//for Linkage Map
					try {   
						Class.forName(central.getDriverName());   
						connection = DriverManager.getConnection(central.getUrl(), central.getUsername(), central.getPassword());   
						statement = connection.createStatement();   
						resultSet = statement.executeQuery("SELECT map_name from gdms_map where map_id = "+project.getMapID());   
						name = resultSet.getString(1);	
						connection.close();
					} catch (Exception e) {  
					}
					LinkageMapDatasetLoader lLoader= new LinkageMapDatasetLoader();
					name = "RIL-3 (ICGS 44 x ICGS 76) ()";
					rModel = lLoader.load(name);

					try{
						LinkageMapTableView view = (LinkageMapTableView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(LinkageMapTableView.class.getName());

					}catch(Exception e){
					}
					Session.getInstance().setRootModel(rModel);


					//for QTL File Upload....
					if(project.getQtlID()!= 0){
						name = (manager.getDatasetById(project.getQtlID())).getDatasetName();
						QTLDatasetLoader qtlLoader = new QTLDatasetLoader();
						rModel = null;
						try {
							rModel = qtlLoader.load(name);
						} catch (MiddlewareQueryException e) {
						}
						Session.getInstance().setRootModel(rModel);
					}
					//for Phenotype File Upload....
					if(project.getPhenoDatasetID()!= 0){
						PhenotypeDatasetLoader pLoader = new PhenotypeDatasetLoader();
						phenomanager = factory.getNewStudyDataManager();
						name = (phenomanager.getDataSet(project.getPhenoDatasetID())).getName();
						rModel = pLoader.loadPhenotype(name);
						Session.getInstance().setRootModel(rModel);
					}

					rModel.setLoadFlag(true);
					rModel.setLoadProject(projectName);
					rModel.setLoadGeneration(generationName);
					LinkageData linkData =  LinkageData.getLinkageData();;
					rModel.setLinkData(linkData);
					Qtl_MapData qtlMapData = Qtl_MapData.getQtl_MapData();
					rModel.setQtlMapData(qtlMapData);
					SessionChromosome.getInstance().setChromosome(chromosome);

					AccessionListView view1 = (AccessionListView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(AccessionListView.class.getName());
					GraphicalView gView = (GraphicalView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(GraphicalView.class.getName());
					TargetGenotype tView = (TargetGenotype) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(TargetGenotype.class.getName());

					Session.getInstance().setRootModel(rModel);
					SessionChromosome.getInstance().setChromosome(chromosome);
					/**
					 ** For SelectedAccessions..... 
					 **/

					try {
						List<SelectedGenotype> selacc = mbdtmanager.getSelectedAccession(pid);
						SelectedAccessionsView sview = new SelectedAccessionsView();
						sview.setLoadAcc(selacc);

						List<Object> selaccSubset = new ArrayList<Object>();
						try{
							if(rModel.getLinkData().getLoadAcc().size() !=0){
								selaccSubset = rModel.getLinkData().getLoadAcc();
							}
						}catch(Exception e1){
						}
						view1.getSite().getSelectionProvider().setSelection(new StructuredSelection(selaccSubset));
					} catch (Exception e2) {
					}

					//----end of SelectedAccessions

					//---start Foreground Markers
					try {
						List<Integer> selmarkers = mbdtmanager.getMarkerStatus(gid);
						List<MarkerIdMarkerNameElement> marinfo =manager.getMarkerNamesByMarkerIds(selmarkers);
						List<String> mname = new ArrayList<String>();
						for(int s =0; s<marinfo.size(); s++)
						mname.add(marinfo.get(s).getMarkerName());

						List<String> foregroundMarkers = linkData.getForegroundMarker();
						HashMap mstatus=new HashMap();
						HashMap flankmarkers = new HashMap();
						flankmarkers = linkData.getFlankmarkers();

						int i=0;
						if(foregroundMarkers==null){
							foregroundMarkers=new ArrayList<String>();
						}
						if(mstatus==null){
							mstatus=new HashMap();
						}

						for(int l=0;l<rModel.getLinkagemap().get(0).getChromosomes().size();l++){
							if(mname.contains(rModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker())){
								mstatus.put(rModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker(), "Foreground");
								rModel.getLinkagemap().get(0).getChromosomes().get(l).setForestatus("Foreground");
								if(!(foregroundMarkers.contains(rModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker()))){
									foregroundMarkers.add(rModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker());
								}
							}
						}
						rModel.getLinkagemap().get(0).getChromosomes().get(0).setMstatus(mstatus);								
						linkData.setForegroundMarker(foregroundMarkers);
						Session.getInstance().setRootModel(rModel);
					} catch (Exception e1) {
					}


					//---end Foreground markers
					
					//---start set parent data
					List<SelectedGenotype> parents = mbdtmanager.getParentData(gid);
					TargetGenotype tview1 = new TargetGenotype();
					
					List<String> tgen = new ArrayList();
					for(int i1 =0; i1< parents.size(); i1++){
						if(parents.get(i1).getType().toString()=="SD" ){
							tgen.add(gmanager.getNamesByGID(parents.get(i1).getGid(), null, null).get(0).getNval());
							tgen.add("Donor");
						}
						else if(parents.get(i1).getType().toString()=="SR"){
							tgen.add(gmanager.getNamesByGID(parents.get(i1).getGid(), null, null).get(0).getNval());
							tgen.add("Recurrent");
						}	
					}
					tview1.setParentData(tgen);
					//---end setparent data
					
					if(!rModel.getLoadNextGen().isEmpty()){
						//For hiding views...
						IViewPart iView = PlatformUI.getWorkbench().getActiveWorkbenchWindow ().getActivePage().findView(TargetGenotype.class.getName());
						IViewPart iView1 = PlatformUI.getWorkbench().getActiveWorkbenchWindow ().getActivePage().findView(SelectedAccessionsView.class.getName());

						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(iView);
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(iView1);

						Session.getInstance().setRootModel(rModel);
					}
				}catch(Exception e){
					e.printStackTrace();
				}


			} 



		} catch(Exception e){

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


