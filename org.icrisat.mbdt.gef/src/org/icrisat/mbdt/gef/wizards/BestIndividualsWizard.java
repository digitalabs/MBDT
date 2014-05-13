package org.icrisat.mbdt.gef.wizards;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.icrisat.mbdt.gef.views.GraphicalView;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionTargetGenotype;

public class BestIndividualsWizard extends Wizard implements IImportWizard {
	List<Object> noselaccSubset = new ArrayList<Object>();
	List<String> selacc = new ArrayList<String>();
	List<Object> selaccSubset = new ArrayList<Object>();
	List<String> selmarker_wizard = new ArrayList<String>();
	List<String> lMarkers= new ArrayList<String>();
	List<String> markerPositions= new ArrayList<String>();
	List<String> markersList = new ArrayList<String>();
	static int count= 0;
	HashMap<String, List<String>> bestindividuals;
	HashMap<String, List<String>> missingindividuals;
	HashMap<String, List<String>> flankingindividuals;
	HashMap flankmarkers;
	//----start % Recovery----
	static HashMap<String, List> allelval1 = new HashMap<String, List>();
	List<String> list;
String[][] matrix_accD= new String[100][100];
static int count1 =0,count2 =0;
int t1=0, j1=0, temp=0;
public static List<String> TargetCharAllele= new ArrayList<String>();
Vector<String> acc_with_alllabels= new Vector<String>();
HashMap<String,Float> recp = new HashMap<String,Float>();
	
	//----End---
	
	public BestIndividualsWizard() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		
		BestIndividualPage page = (BestIndividualPage)getPage("BestIndividualPage");
		String returnString = page.getSelectedMarkers();
		RootModel rootModel = RootModel.getRootModel();
		RootModel rModel = null;
		LinkageData linkage;
		
		if(rootModel.getLoadFlag() == null){
			rModel = RootModel.getRootModel();
			linkage = LinkageData.getLinkageData();
		}else{
			rModel = Session.getInstance().getRootmodel();
			linkage = rModel.getLinkData();
		}
		
		String[] splitReturnString = returnString.split("@!@");
		for(int i = 0; i < splitReturnString.length; i++){
			selmarker_wizard.add(splitReturnString[i]);
		}
		
		try{
			
			bestindividuals = rModel.getLinkData().getBestindividual();
			if(bestindividuals==null){
				bestindividuals=new HashMap<String, List<String>>();
			}
			missingindividuals = rModel.getLinkData().getMissingindividual();
			if(missingindividuals==null){
				missingindividuals=new HashMap<String, List<String>>();
			}
			flankingindividuals = rModel.getLinkData().getFlankingindividual();
			if(flankingindividuals==null){
				flankingindividuals=new HashMap<String, List<String>>();
			}
			flankmarkers = rModel.getLinkData().getFlankmarkers();
			if(flankmarkers == null){
				flankmarkers = new HashMap();
			}
			for(int i=0;i<selmarker_wizard.size();i++){
				String marker = selmarker_wizard.get(i).substring(0, selmarker_wizard.get(i).indexOf(" "));
				try{
					List ind = bestindividuals.get(marker);
					List mind = missingindividuals.get(marker);
					if(ind == null){
						ind = new ArrayList();
					}
					if(mind == null){
						mind = new ArrayList();
					}
				for(int a=0; a < ind.size(); a++){
					if(!(selacc.contains(ind.get(a))))
					selacc.add(bestindividuals.get(marker).get(a));
				}
				if(linkage.isMissing()==true){
					for(int a=0; a < mind.size(); a++){
						if(!(selacc.contains(missingindividuals.get(marker).get(a))))
						selacc.add(missingindividuals.get(marker).get(a));
					}
				}
			
				}catch(Exception e){
				}
			}
			List<String> commonind = new ArrayList<String>();
			List<String> diffind = new ArrayList<String>();
			List common [] = new List [ selmarker_wizard.size()];
			List miss[] = new List [ selmarker_wizard.size()];
			List flank[] = new List [ selmarker_wizard.size()];
			int missCount = 0;
			int flankCount = 0;
			common[0]=bestindividuals.get(selmarker_wizard.get(0).substring(0, selmarker_wizard.get(0).indexOf(" ")));
			if(linkage.isMissing()==true){
				miss[0] = new ArrayList();
				try {
					for(int a=0; a < missingindividuals.get(selmarker_wizard.get(0).substring(0, selmarker_wizard.get(0).indexOf(" "))).size(); a++){
						if(!(common[0].contains(missingindividuals.get(selmarker_wizard.get(0).substring(0, selmarker_wizard.get(0).indexOf(" "))).get(a))))
							miss[0].add(missingindividuals.get(selmarker_wizard.get(0).substring(0, selmarker_wizard.get(0).indexOf(" "))).get(a));
					}
				} catch (Exception e) {
				}
			}
			if(linkage.isFlanking()==true){
				flank[0] = new ArrayList();
				String marker = selmarker_wizard.get(0).substring(0, selmarker_wizard.get(0).indexOf(" "));
				
				try {
					if(flankmarkers.containsKey(marker)){
						String lflank = flankmarkers.get(marker).toString().substring(0, flankmarkers.get(marker).toString().indexOf("!@!"));
						String rflank = flankmarkers.get(marker).toString().substring(flankmarkers.get(marker).toString().indexOf("!@!")+3,flankmarkers.get(marker).toString().length());
						
					try {
						for(int a=0; a < flankingindividuals.get(lflank).size(); a++){
							if(!(flank[0].contains(flankingindividuals.get(lflank).get(a))))
								flank[0].add(flankingindividuals.get(lflank).get(a));

						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
					}
					for(int a=0; a < flankingindividuals.get(rflank).size(); a++){
						if(!(flank[0].contains(flankingindividuals.get(rflank).get(a))))
							flank[0].add(flankingindividuals.get(rflank).get(a));
						}
					
					}
					
				} catch (Exception e) {
				}
			}
			for(int c=1;c < selmarker_wizard.size(); c++){
				String marker = selmarker_wizard.get(c).substring(0, selmarker_wizard.get(c).indexOf(" "));
				
				 common[c]= bestindividuals.get(marker) ;
				 if(common[c]==null){
					 common[c]= new ArrayList();
				 }
				 if(linkage.isMissing()==true){
					 miss[c] = new ArrayList();
						try {
							for(int a=0; a < missingindividuals.get(marker).size(); a++){
								if(!(common[c].contains(missingindividuals.get(marker).get(a))))
									miss[c].add(missingindividuals.get(marker).get(a));
							}
						} catch (Exception e) {
						}
					}
				 if(linkage.isFlanking()==true){
						flank[c] = new ArrayList();
						try {
							if(flankmarkers.containsKey(marker)){
								String lflank = flankmarkers.get(marker).toString().substring(0, flankmarkers.get(marker).toString().indexOf("!@!"));
								String rflank = flankmarkers.get(marker).toString().substring(flankmarkers.get(marker).toString().indexOf("!@!")+3,flankmarkers.get(marker).toString().length());
							try {
								for(int a=0; a < flankingindividuals.get(lflank).size(); a++){
									if(!(flank[c].contains(flankingindividuals.get(lflank).get(a))))
										flank[c].add(flankingindividuals.get(lflank).get(a));
								}
							} catch (Exception e) {
							}
							for(int a=0; a < flankingindividuals.get(rflank).size(); a++){
								if(!(flank[c].contains(flankingindividuals.get(rflank).get(a))))
									flank[c].add(flankingindividuals.get(rflank).get(a));
								}
							}
						} catch (Exception e) {
						}
					}
				if(!(common[c]==null)){
				int com= 0; 
				if(c==1){
					for (int d=0; d<common[c-1].size();d++) { 
						if (common[c].contains(common[c-1].get(d))) {
							com++;
							if(!(commonind.contains((String)common[c-1].get(d)))){
								commonind.add((String)common[c-1].get(d));
							}
						}
						if(linkage.isMissing()==true){
						if(miss[c].contains(common[c-1].get(d))){
							if(!(commonind.contains((String)common[c-1].get(d)))){
								commonind.add((String)common[c-1].get(d));
							}
						}
						}
					}
					if(linkage.isMissing()==true){
					for (int d=0; d<miss[c-1].size();d++) { 
						if (common[c].contains(miss[c-1].get(d))) {
							com++;
							if(!(commonind.contains((String)miss[c-1].get(d)))){
								commonind.add((String)miss[c-1].get(d));
							}
						}
						if(miss[c].contains(miss[c-1].get(d))){
							if(!(commonind.contains((String)miss[c-1].get(d)))){
								commonind.add((String)miss[c-1].get(d));
							}
						}
					}
					}
					/*if(linkage.isFlanking()==true){
						commonind = (ArrayList)Subtract(flank[0],commonind);
						}*/
				}else{
					for (int d=0; d<commonind.size();d++) { 
						   if (!(common[c].contains(commonind.get(d)))) {
							   diffind.add(commonind.get(d));
						   }
						   if(linkage.isMissing()==true){
								if(miss[c].contains(commonind.get(d))){
									diffind.remove(commonind.get(d));
								}
						   }
					}
					/*if(linkage.isFlanking()==true){
						commonind = (ArrayList)Subtract(flank[c],commonind);
				   }*/
					for(int diff=0; diff<diffind.size();diff++){
						commonind.remove(diffind.get(diff));
					}
				}
				}
			}
			if(selmarker_wizard.size()==1)
			{
				commonind = common[0];
			}

			//--------Start % recovery----------
			List<String> flankind = new ArrayList<String>();
			List<String> templist = new ArrayList<String>();
			if(commonind.size()!=linkage.getBcount()){				
				if(linkage.isFlanking()==true){
					flankind = (ArrayList)Subtract(flank[0],commonind);
					for(int c=2;c < selmarker_wizard.size(); c++){
						templist = (ArrayList)Subtract(flank[c],commonind);
					}
					for(int t=0; t<templist.size(); t++){
						if(!flankind.contains(templist.get(t))){
							flankind.add(templist.get(t));
						}
							
					}
				}
			}
			if(flankind.size()!=0){
			if(flankind.size()>=linkage.getBcount()){
				commonind = flankind;
			}
			}
			try {
				HashMap accWithLabels = linkage.getAccWithLabels();
				List<String> gh = new ArrayList<String>();
				gh.addAll(accWithLabels.keySet());
				TargetGeno target;
				target = SessionTargetGenotype.getInstance().getTargetGeno();	 
				 
				 
				for(int i = 0; i < target.getParents().size();i++){
					if(target.getParents().get(i).getParent().contains("Target")){
							TargetCharAllele=target.getParents().get(i).getSelParents().get(0).getMParents().get(0).getColorAllele().get(0).getTargetCharAlleleValues();
					}
				}
				
				for (int i1 = 0; i1 < TargetCharAllele.size(); i1++) {
					if(TargetCharAllele.get(i1).equals("A") || TargetCharAllele.get(i1).equals("-"))
					{
						count1++;
					}
				}

				for(int n = 0; n < linkage.getAccallellic().size(); n++){
					
					acc_with_alllabels.add(linkage.getAccallellic().get(n));
					acc_with_alllabels.add(rModel.getAccAllele().get(n));
				}

				for( int i1 = 0; i1 < commonind.size(); i1++) {
						
					for(int j=0; j<acc_with_alllabels.size(); j++) {
							
					if(commonind.get(i1).equals(acc_with_alllabels.elementAt(j))) {
					String pp1= (String) acc_with_alllabels.elementAt(j+1);
					matrix_accD[t1][j1] = pp1;
					j1++;
						}
					}t1++;	j1=0;
					
				}


				for(int i=0; i<commonind.size(); i++) {
					
					
					int noofmarkers =0;			
											
							String sel_string= commonind.get(i);	
						for(int l=0;l<TargetCharAllele.size();l++){
						String alelval1 =matrix_accD[i][l];
						
						try {
							if(((alelval1.equals(TargetCharAllele.get(l))) && ( !TargetCharAllele.get(l).equals("B")))  || ( alelval1.equals("A") && TargetCharAllele.get(l).equals("-"))   ){
									if( !((alelval1.equals("-")) && ( TargetCharAllele.get(l).equals("-")) ))
									{
											noofmarkers++;
									}
								}
						} catch (Exception e) {
						}
							}
						
						try {
							float percentage=(noofmarkers * 100) /count1;
								String per= Float.toString(percentage);								
								recp.put(sel_string, percentage);								
						} catch (RuntimeException e) {
							// TODO Auto-generated catch block
						}				
							
				}
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
			}
			
			
			//sorting hashmap
			List map1 = new ArrayList();
			Map<String, Float> map = new HashMap<String, Float>();
            map = recp;
            
            List<Map.Entry<String, Float>> list = new Vector<Map.Entry<String, Float>>(map.entrySet());
            java.util.Collections.sort(list, new Comparator<Map.Entry<String, Float>>(){

               public int compare(Map.Entry<String, Float> entry, Map.Entry<String, Float> entry1){
                  return (entry.getValue().equals(entry1.getValue()) ? 0 : (entry.getValue() < entry1.getValue() ? 1 : -1));
               }
            });
            if(flankind.size()!=0){
            map1.clear();
//            if(flankind.size()<linkage.getBcount()){
            	map1.addAll(flankind);
//			}
            }
            try {
				for (Map.Entry<String, Float> entry: list){
				   map.put(entry.getKey(), entry.getValue());
				   if(!map1.contains(entry.getKey()))
				   map1.add(entry.getKey());
				}
			} catch (Exception e1) {
			}
           //--------End %Recovery-------------	
			selaccSubset.clear();
			noselaccSubset.clear();
			for(int si = 0; si < rModel.getGenotype().get(0).getAccessions().size(); si++){
				if(common.length==1){
					if(linkage.isFlanking()==true){
						if(flankind.contains(rModel.getGenotype().get(0).getAccessions().get(si).getName())){
							selaccSubset.add(rModel.getGenotype().get(0).getAccessions().get(si));
						}
						else{							
							noselaccSubset.add(rModel.getGenotype().get(0).getAccessions().get(si));
						}
					}else{
					if(common[0].contains(rModel.getGenotype().get(0).getAccessions().get(si).getName())){
						selaccSubset.add(rModel.getGenotype().get(0).getAccessions().get(si));
					}
					else{						
						noselaccSubset.add(rModel.getGenotype().get(0).getAccessions().get(si));
					}
					}
				}else{
					if(commonind.contains(rModel.getGenotype().get(0).getAccessions().get(si).getName())){
						selaccSubset.add(rModel.getGenotype().get(0).getAccessions().get(si));
					}
					else{
						noselaccSubset.add(rModel.getGenotype().get(0).getAccessions().get(si));
					}
				}
			}
			List map2 = new ArrayList();
			List<Object> selaccSubsetremove = new ArrayList<Object>();
			List<Object> selaccSubsetadd= new ArrayList<Object>();
			
			try {
				for(int m1=0; m1<linkage.getBcount(); m1++){
					map2.add(map1.get(m1));	
				}
				if(map2.size()!=selaccSubset.size()){
				for(int m = 0; m<selaccSubset.size(); m++){
					if(!(map2.contains(((Accessions)selaccSubset.get(m)).getName()))){
						selaccSubsetremove.add(selaccSubset.get(m));
					}else{
						selaccSubsetadd.add(selaccSubset.get(m));
					}
					
				}}
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
			for(int diff=0; diff<selaccSubsetremove.size();diff++){
				selaccSubset.remove(selaccSubsetremove.get(diff));
				
				noselaccSubset.add(selaccSubsetremove.get(diff));
			}
			
			/*for(int diff=0; diff<selaccSubsetremove.size();diff++){
				noselaccSubset.add(selaccSubsetremove.get(diff));
			}*/
			rModel.getLinkData().setLoadAcc(selaccSubset);
			rModel.getGenotype().get(0).getAccessions().get(0).setLoadAcc(selaccSubset);
			rModel.getGenotype().get(0).setSelAccForUnHide(noselaccSubset);
			rModel.getGenotype().get(0).setNGHidingElement(selaccSubset);
			rModel.getGenotype().get(0).setNGHidingStatus(true);
			GraphicalView gv = new GraphicalView();
			gv.unsort();
			
		    
//			rModel.getLinkData().setBestcount(null);		
			}catch(Exception e){
			}
			
	linkage.setMissing(false);
	linkage.setFlanking(false);
		if(rModel instanceof RootModel) {
			Session.getInstance().setRootModel(rModel);
			
		}
		
		return true;
	}

	

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub

	}
	@Override
	public void addPages() {
		// TODO Auto-generated method stub
		addPage(new BestIndividualPage("BestIndividualPage"));
	}
	public static Collection Subtract(Collection coll1, Collection coll2)
	{
	    Collection result = new ArrayList(coll2);
	    result.retainAll(coll1);
	    return result;
	}
}
