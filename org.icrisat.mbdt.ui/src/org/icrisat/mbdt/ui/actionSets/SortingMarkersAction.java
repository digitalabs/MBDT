package org.icrisat.mbdt.ui.actionSets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Vector;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.LinkageMapModel.LinkageMap;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.ui.dialog.SortingMarkersDialog;

public class SortingMarkersAction implements IWorkbenchWindowActionDelegate {
	
	RootModel rModel;
	LinkageData linkage;
	LinkedHashSet  selectedMarkers1 = new LinkedHashSet();
	List<String> editParts = new ArrayList<String>();
	String selectedmarker = null;
	HashMap idLabelMap = new HashMap();
	static LinkedHashSet pp = new LinkedHashSet();
	public static String sortval = "";
	boolean issort = false;
	
	
	public void dispose() {
		// TODO Auto-generated method stub

	}

	
	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}

	
	public void run(IAction action) {
		// TODO Auto-generated method stub
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		SortingMarkersDialog sortDialog = new SortingMarkersDialog(shell);
		int accMarkersCount = 0;
		RootModel rootModel = RootModel.getRootModel();
		if(rootModel.getLoadFlag() == null){
			rModel = RootModel.getRootModel();
			linkage = LinkageData.getLinkageData();
		}else{
			rModel = Session.getInstance().getRootmodel();
			linkage = rModel.getLinkData();
		}
//		System.out.println(rootModel.getGenotype().size());
		//if(rootModel.getGenotype()){
		
		//sortDialog.open();
		//}
		Genotype geno = (Genotype) rModel.getGenotype().get(0);
		LinkageMap map = (LinkageMap)rModel.getLinkagemap().get(0);
		for(int ik = 0; ik < map.getChromosomes().size(); ik++){
			if(geno.getHeaderList().contains(map.getChromosomes().get(ik).getMap_marker())){
				accMarkersCount++;
			}
		}
		if(sortDialog.open()== Window.OK){
//			System.out.println("SORTED"+sortDialog.getMarkerTextData());
			try{
				selectedmarker = sortDialog.getMarkerTextData();
				selectedMarkers1.add(selectedmarker);
				pp.addAll(selectedMarkers1);
				linkage.setMarkers_of_intrest(selectedMarkers1);
				linkage.setListSelMarkers(pp);	
				
				
							
				if(linkage.getIsSortingDone() == true){
					selectedMarkers1.clear();
					List eles1 = new ArrayList();
					List<String> eles = new ArrayList();
					eles1.addAll(linkage.getAccFrmGV());
					eles.addAll(eles1);
					for(int i=0; i<rModel.getGenotype().get(0).getAccessions().size(); i++){
						for(int j=0; j<rModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().size(); j++){
							rModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).setKeyvalue(null);
							if(!(rModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).getSelAccession().equals(null))){
								String gaga=(eles.get(0)).toString();
								rModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).setSelAccession(gaga);	
								String ele=rModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).getSelAccession().toString();	
								for(int k=0; k<rModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).getSelacc1().size(); k++){
									rModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).getSelacc1().get(k).setSelAccession(ele);
								}
								eles.remove(0);
							}
						}
						
					}
					/*if(rModel instanceof RootModel) {
						Session.getInstance().setRootModel(rModel);
					}*/
					selectedMarkers1.add(selectedmarker);
					sortval="";
					linkage.setSortval("");
				}	
						
				if(!(selectedMarkers1.contains(null))){
					Session.getInstance().setRootModel(rModel);
				}
				
				List<String> gg = new ArrayList<String>();
				List<String> gg1 = new ArrayList<String>();
				Vector<String> acc_bef_sot = new Vector<String>();
				Vector<String> lab_bef_sot = new Vector<String>();
				try {
					acc_bef_sot.addAll(linkage.getAccallellic());
					lab_bef_sot.addAll(linkage.getLabelallelic());
					String id=new String();
					String label=new String();  
					ArrayList labelList=new ArrayList();
					labelList.clear();
					for(int u=0; u<acc_bef_sot.size(); u++){
						id=(String) acc_bef_sot.elementAt(u);
						label=(String) lab_bef_sot.elementAt(u);
						if(idLabelMap.containsKey(id))
						{
							labelList=(ArrayList)idLabelMap.get(id);
							if(labelList.size()<accMarkersCount){
								labelList.add(label);
								idLabelMap.put(id,labelList);
							}
						}
						// If the Map does not contains ID, create new ArrayList and add it to Map
						else
						{
							labelList=new ArrayList();
							labelList.add(label);
							idLabelMap.put(id,labelList);
						}
					}
					gg.addAll(linkage.getSortedAccessions());
					gg1=gg;
					//issort=true;
					linkage.setIsSortingDone(true);				
	//				sortval="sort";
					linkage.setSortval("sort");
				} catch(Exception e){
					}
	
					for(int i=0; i<rModel.getGenotype().get(0).getAccessions().size(); i++){
						for(int j=0; j<rModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().size(); j++){
							if(!(rModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).getSelAccession().equals(null))){
								rModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).setSelAccession(gg1.get(0));	
								String ele=rModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).getSelAccession().toString();	
								for(int k=0; k<rModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).getSelacc1().size(); k++){
									rModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).getSelacc1().get(k).setSelAccession(ele);
								}
								gg1.remove(0);
							}
							rModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).setKeyvalue(idLabelMap);
						}
					}
	
					if(rModel instanceof RootModel) {
						Session.getInstance().setRootModel(rModel);
					}				
			}catch(Exception e){
			}
		}
	}


	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
