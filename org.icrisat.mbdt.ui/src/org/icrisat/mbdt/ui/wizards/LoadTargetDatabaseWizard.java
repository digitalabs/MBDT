package org.icrisat.mbdt.ui.wizards;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
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
import org.generationcp.middleware.pojos.mbdt.MBDTGeneration;
import org.generationcp.middleware.pojos.mbdt.MBDTProjectData;
import org.generationcp.middleware.pojos.mbdt.SelectedGenotype;
import org.icrisat.mbdt.gef.views.GraphicalView;
import org.icrisat.mbdt.gef.views.TargetGenotype;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.WorkbenchState;
import org.icrisat.mbdt.model.Chromosome.Chromosome;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.NextGeneration.LoadNextGenData;
import org.icrisat.mbdt.model.NextGeneration.LoadNextGenDataFirstChild;
import org.icrisat.mbdt.model.NextGeneration.LoadNextGenDataSecondChild;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;
import org.icrisat.mbdt.model.loader.GenotypeDataLoader;
import org.icrisat.mbdt.model.loader.GenotypeDatasetLoader;
import org.icrisat.mbdt.model.loader.LinkageMapDatasetLoader;
import org.icrisat.mbdt.model.loader.PhenotypeDatasetLoader;
import org.icrisat.mbdt.model.loader.QTLDatasetLoader;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionChromosome;
import org.icrisat.mbdt.model.sessions.SessionTargetGenotype;
import org.icrisat.mbdt.ui.views.AccessionListView;
import org.icrisat.mbdt.ui.views.LinkageMapTableView;
import org.icrisat.mbdt.ui.views.SelectedAccessionsView;

public class LoadTargetDatabaseWizard extends Wizard implements IImportWizard {

	String mainFolder = "";
	String folder = "";
	String subFolder = "";
	List<String> markersFrmGeno = new ArrayList<String>();
	List<String> TargetMarkers = new ArrayList<String>();
	List<String> targetAlleleValues = new ArrayList<String>();
	List<String> targetAlleleValuesInCHAR = new ArrayList<String>();
	List allelevalues = new ArrayList();
	List<String> targetColorLabels = new ArrayList<String>();
	List<Integer> targetWidths = new ArrayList<Integer>();
	int targetHeight = 0;
	private Shell shell;
	DatabaseConnectionParameters local, central;
	ManagerFactory factory;
	MBDTDataManager mbdtmanager;
	GenotypicDataManager manager;
	GermplasmDataManager gmanager;
	StudyDataManager phenomanager;
	MBDTProjectData project;
	MBDTGeneration generation;
	int pid=0, gid=0;
	public LoadTargetDatabaseWizard() {
		// TODO Auto-generated constructor stub
		this.setWindowTitle("Load TargetData");
	}

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		
		LoadTargetDatabasePage page = (LoadTargetDatabasePage)getPage("LoadTargetDatabasePage");
		String returnString = page.getLoadTargetSelected();
		String[] filePath = returnString.split("!@!");
		
		String projectName = filePath[0];
		String generationName = filePath[1];
		
		try {

			try {
				String url = Platform.getLocation().toString().substring(0, Platform.getLocation().toString().lastIndexOf("/")+1);
				local = new DatabaseConnectionParameters(url+"DatabaseConfig.properties","local");
				central = new DatabaseConnectionParameters(url+"DatabaseConfig.properties","central");
				factory = new ManagerFactory(local, central);
				mbdtmanager = factory.getMbdtDataManager();
				manager = factory.getGenotypicDataManager();
				gmanager = factory.getGermplasmDataManager();
			} catch (IllegalStateException e1) {
			} catch (ConfigException e1) {
			} catch (URISyntaxException e1) {
			}
			Chromosome chromosome = Chromosome.getChromosome();
			
			try {
				project = new MBDTProjectData();
				pid = mbdtmanager.getProjectIDByProjectName(projectName);
				project = mbdtmanager.getProjectData(pid);
			} catch (Exception e2) {
			}
			Connection connection;
			Statement statement;
			ResultSet resultSet;
			String name ="";
			try {
				gid = mbdtmanager.getGenerationIDByGenerationName(generationName, pid);
				name = (manager.getDatasetById((mbdtmanager.getGeneration(gid)).getGenotypeDatasetID())).getDatasetName();
			} catch (Exception e1) {
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
			try {
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
			} catch (MiddlewareQueryException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			//for Phenotype File Upload....
			try {
				if(project.getPhenoDatasetID()!= 0){
					PhenotypeDatasetLoader pLoader = new PhenotypeDatasetLoader();
					phenomanager = factory.getNewStudyDataManager();
					name = (phenomanager.getDataSet(project.getPhenoDatasetID())).getName();
					rModel = pLoader.loadPhenotype(name);
					Session.getInstance().setRootModel(rModel);
				}
			} catch (ConfigException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (MiddlewareQueryException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
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
			try {
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
			} catch (MiddlewareQueryException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			//---end setparent data
			//For hiding views...
			IViewPart iView = PlatformUI.getWorkbench().getActiveWorkbenchWindow ().getActivePage().findView(TargetGenotype.class.getName());
			IViewPart iView1 = PlatformUI.getWorkbench().getActiveWorkbenchWindow ().getActivePage().findView(SelectedAccessionsView.class.getName());
			
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(iView);
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(iView1);
		//-----end of hiding views
			
			
			
			rModel.getGenotype().clear();
			GenotypeDatasetLoader gLoader1 = new GenotypeDatasetLoader();
			// for Genotype File Upload....
			RootModel gType = gLoader1.loadGenotype(filePath[2]);	
			gType.setGeneration("Second");
			view1 = (AccessionListView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(AccessionListView.class.getName());
			TargetGeno target = SessionTargetGenotype.getInstance().getTargetGeno();
			LoadNextGenData loadData = new LoadNextGenData();
			LoadNextGenDataFirstChild loadDataFirstChild = new LoadNextGenDataFirstChild();
			LoadNextGenDataSecondChild loadDataSecondChild = new LoadNextGenDataSecondChild();
//			System.out.println("Markers :"+markersFrmGeno);
			if(!(target==null)){
				if(target.getParents().size()==0){
					MessageDialog.openError(shell, "Error", "Target Data is not Available ");
					
					return false;
				}
			for(int i = 0; i < target.getParents().size();i++){
				if(target.getParents().get(i).getParent().contains("Target")){
					loadDataFirstChild.setTargetAcc(target.getParents().get(i).getParent());
//					loadDataSecondChild.setNGTargetMarkerName(targetMarkerName)
										
					loadData.setTargetAcc(target.getParents().get(i).getParent());
					loadData.setTargetAccObject(target.getParents().get(i));
					loadData.setTargetType("Target");
				try{
					for(int j=0;j<target.getParents().get(i).getSelParents().get(0).getMParents().get(0).getColorAllele().size();j++){
						String[] alleleSplit = target.getParents().get(i).getSelParents().get(0).getMParents().get(0).getColorAllele().get(j).getTargetAlleleValue().split(" ");
						
						targetAlleleValues.add(target.getParents().get(i).getSelParents().get(0).getMParents().get(0).getColorAllele().get(j).getTargetAlleleValue());
						targetAlleleValuesInCHAR.add(target.getParents().get(i).getSelParents().get(0).getMParents().get(0).getColorAllele().get(j).getTargetCharAlleleValues().get(j));
						TargetMarkers.add(target.getParents().get(i).getSelParents().get(0).getMParents().get(0).getColorAllele().get(j).getMarkerName());
//						targetColorLabels.add(target.getParents().get(i).getSelParents().get(0).getMParents().get(0).getColorAllele().get(j).getAlleleName());
						targetWidths.add(target.getParents().get(i).getSelParents().get(0).getMParents().get(0).getColorAllele().get(j).getWidth());
						targetHeight = target.getParents().get(i).getSelParents().get(0).getMParents().get(0).getColorAllele().get(j).getHeight();
						
						if(target.getParents().get(i).getSelParents().get(0).getMParents().get(0).getColorAllele().get(j).getAlleleName().contains("a50")){
							targetColorLabels.add("-");
						}else if(target.getParents().get(i).getSelParents().get(0).getMParents().get(0).getColorAllele().get(j).getAlleleName().contains("amono")){
							targetColorLabels.add("Monomorphic");
						}else{
							targetColorLabels.add(target.getParents().get(i).getSelParents().get(0).getMParents().get(0).getColorAllele().get(j).getTargetCharAlleleValues().get(j));
						}
						
						
						
					}
				}catch(Exception e){
					e.printStackTrace();
				}
					loadDataSecondChild.setNGTargetMarkerName(TargetMarkers);
					loadDataSecondChild.setNGTargetalleles(targetAlleleValues);
					loadDataSecondChild.setNGTargetallelesChar(targetAlleleValuesInCHAR);
					loadDataSecondChild.setNGTcolorValue(targetColorLabels);
					loadDataSecondChild.setNGTWidth(targetWidths);
					loadDataSecondChild.setNGTHeight(targetHeight);
					
				}
			}
			}else{
				
				MessageDialog.openError(shell, "Error", "Target Data is not Available ");
				
				return false;
			}
			loadDataFirstChild.getLoadNGSecondChild().add(loadDataSecondChild);
			loadData.getListtargetAcc().add(loadDataFirstChild);
			rModel.getLoadNextGen().add(loadData);
			Session.getInstance().setRootModel(gType);
			//
			
			rModel.getGenotype().add(gType.getGenotype().get(0));
			
			view1.getSite().getSelectionProvider().setSelection(new StructuredSelection(rModel.getGenotype().get(0).getAccessions()));
			
			for(int acc = 0; acc < rModel.getGenotype().get(0).getAccessions().size(); acc++){
				rModel.getGenotype().get(0).getAccessions().get(acc).setType("Recurrent");
			}

	//---start of flanking markers
			
			HashMap flankmarkers = new HashMap();
			HashMap monomarkers = new HashMap();
			List<String> flankingMarkers = linkData.getFlankingMarker();
			List<String> foregroundMarkers = linkData.getForegroundMarker();
			if(flankingMarkers==null){
				flankingMarkers=new ArrayList<String>();
			}
			if(foregroundMarkers==null){
				foregroundMarkers=new ArrayList<String>();
			}
			try {
				flankmarkers = linkData.getFlankmarkers();
				List markers = new ArrayList();				
				List<String> lMarkers= new ArrayList<String>();
				lMarkers = linkData.getMarker();
				monomarkers = rModel.getLinkagemap().get(0).getLimsMarkers().get(0).getMonomorphicMarkers();
				if(monomarkers == null){
					monomarkers = new HashMap();
				}
				if(flankmarkers==null){
					flankmarkers = new HashMap();
				}
				
				//------Start fg fm----
				if(!(foregroundMarkers==null)){
					
					for(int i=0;i<lMarkers.size();i++){
					if(foregroundMarkers.contains(lMarkers.get(i))){
						String lmark="",rmark="";
						try {
							for(int f=i-1;;f--){
								String str = rModel.getLinkagemap().get(0).getChromosomes().get(f).getMap_marker();
								if(rModel.getLinkagemap().get(0).getChromosomes().get(f).getChromosome().equals(rModel.getLinkagemap().get(0).getChromosomes().get(f-1).getChromosome())){
									if((rModel.getGenotype().get(0).getHeaderList().contains(str))&&(!(monomarkers.get(str).equals("Monomorphic")))&&(!foregroundMarkers.contains(str))){
										lmark = str;
										if(!flankingMarkers.contains(lmark)){
											flankingMarkers.add(lmark);										
										}
										break;											
									}
								}else{
									break; 
								}
							}							 
						} catch (Exception e1) {
						}
						try {
							for(int f=i+1;;f++){
								String str = rModel.getLinkagemap().get(0).getChromosomes().get(f).getMap_marker();
								if(rModel.getLinkagemap().get(0).getChromosomes().get(f).getChromosome().equals(rModel.getLinkagemap().get(0).getChromosomes().get(f+1).getChromosome())){
									if((rModel.getGenotype().get(0).getHeaderList().contains(str))&&(!(monomarkers.get(str).equals("Monomorphic")))&&(!foregroundMarkers.contains(str))){
										rmark = str;
										if(!flankingMarkers.contains(rmark)){
											flankingMarkers.add(rmark);										
										}
										break;											
									}
								}else{
									break; 
								}
							}	
						} catch (Exception e) {
						}
						flankmarkers.put(lMarkers.get(i), lmark+"!@!"+rmark);
						}	
					}
					}
				rModel.getLinkData().setFlankmarkers(flankmarkers);
				rModel.getLinkData().setFlankingMarker(flankingMarkers);
				//----end fg fm------
			
			}catch(Exception e){
			}
			///---end---
			SessionTargetGenotype.getInstance().setTargetGeno(target);
			Session.getInstance().setRootModel(rModel);
			SessionChromosome.getInstance().getChromosome().setLoadNextGen(rModel.getLoadNextGen());
			
//			System.out.println("IN WIZARD :"+Session.getInstance().getRootmodel().getGenotype().get(0).getAccessions().size());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}  catch (PartInitException e) {
			// TODO Auto-generated catch block
		} 
		
		return true;
	}

	
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void addPages() {
		// TODO Auto-generated method stub
		addPage(new LoadTargetDatabasePage("LoadTargetDatabasePage"));
	}

}
