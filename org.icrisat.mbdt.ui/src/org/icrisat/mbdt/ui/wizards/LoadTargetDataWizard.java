package org.icrisat.mbdt.ui.wizards;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
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
import org.icrisat.mbdt.gef.views.TargetGenotype;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.WorkbenchState;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.NextGeneration.LoadNextGenData;
import org.icrisat.mbdt.model.NextGeneration.LoadNextGenDataFirstChild;
import org.icrisat.mbdt.model.NextGeneration.LoadNextGenDataSecondChild;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;
import org.icrisat.mbdt.model.loader.GenotypeDataLoader;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionChromosome;
import org.icrisat.mbdt.model.sessions.SessionTargetGenotype;
import org.icrisat.mbdt.ui.views.AccessionListView;
import org.icrisat.mbdt.ui.views.SelectedAccessionsView;

public class LoadTargetDataWizard extends Wizard implements IImportWizard {

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
	
	public LoadTargetDataWizard() {
		// TODO Auto-generated constructor stub
		this.setWindowTitle("Load TargetData");
	}

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		
		LoadTargetDataPage page = (LoadTargetDataPage)getPage("LoadTargetDataPage");
		String returnString = page.getLoadTargetSelected();
		String[] filePath = returnString.split("!@!");
		
		String projectName = filePath[0];
		String generationName = filePath[1];
		
		mainFolder = Platform.getLocation().getDevice()+"//MBDT_PROJECTSFiles";
		folder = mainFolder+"//"+projectName;
		subFolder = folder+"//"+generationName;
		
		
		try {
			
			//For hiding views...
			IViewPart iView = PlatformUI.getWorkbench().getActiveWorkbenchWindow ().getActivePage().findView(TargetGenotype.class.getName());
			IViewPart iView1 = PlatformUI.getWorkbench().getActiveWorkbenchWindow ().getActivePage().findView(SelectedAccessionsView.class.getName());
			
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(iView);
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(iView1);
			
			FileInputStream fis = new FileInputStream(subFolder);
			ObjectInputStream ois = new ObjectInputStream(fis);
	        WorkbenchState myDeserializedObject;
	        myDeserializedObject = (WorkbenchState)ois.readObject();
			//Genometype genome1 = myDeserializedObject.getGenometype();
           //  List<Genotype> geno = myDeserializedObject.getGenoModel();
			RootModel rModel = myDeserializedObject.getRootModelSer();
			LinkageData linkData =  myDeserializedObject.getLinkData();
			rModel.setLinkData(linkData);
			rModel.getRootModel().setLinkData(linkData);
			linkData.setSortval("");
			
			Qtl_MapData qtlMapData = myDeserializedObject.getQtlMapData();
			rModel.setQtlMapData(qtlMapData);
			rModel.getRootModel().setQtlMapData(qtlMapData);
			rModel.setLoadFlag(true);
			rModel.setGeneration("Second");
			rModel.getRootModel().setLoadFlag(true);
			
			rModel.setLoadProject(projectName);
			rModel.setLoadGeneration(generationName);
			
			rModel.getRootModel().setLoadProject(projectName);
			rModel.getRootModel().setLoadGeneration(generationName);
			
			markersFrmGeno = rModel.getGenotype().get(0).getAccessions().get(0).getGmark();
			rModel.getGenotype().clear();
			GenotypeDataLoader gLoader = new GenotypeDataLoader();
			// for Genotype File Upload....
			RootModel gType = gLoader.loadGenotype(filePath[2]);	
			gType.setGeneration("Second");
			
		
			AccessionListView view1 = (AccessionListView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(AccessionListView.class.getName());
			TargetGeno target = myDeserializedObject.getTargetGeno();
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
						for(int k = 0; k < alleleSplit.length; k++){
							allelevalues.add(alleleSplit[k]);
						}
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
		} catch (PartInitException e) {
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
		addPage(new LoadTargetDataPage("LoadTargetDataPage"));
	}

}
