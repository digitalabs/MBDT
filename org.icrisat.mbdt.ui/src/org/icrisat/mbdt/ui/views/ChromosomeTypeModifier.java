package org.icrisat.mbdt.ui.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.LinkageMapModel.Intervals;
import org.icrisat.mbdt.model.TargetGenotype.ColorAllele;
import org.icrisat.mbdt.model.sessions.Session;

public class ChromosomeTypeModifier implements ICellModifier {

	TableViewer tViewer;
	public ChromosomeTypeModifier(TableViewer viewer) {
		tViewer = viewer;
	}

	
	public boolean canModify(Object element, String property) {
		
		if(property.equalsIgnoreCase("Chromosome")) {
			return false;
		}
		if(property.equalsIgnoreCase("Markers")) {
			return false;
		}
		if(property.equalsIgnoreCase("Numbering")) {
			return false;
		}
		if(property.equalsIgnoreCase("Distance")) {
			return false;
		}
		if(property.equalsIgnoreCase("Cum_Dist")) {
			return false;
		}
		if (property.equals("Marker Status")) {
			if(((Intervals)(element)).getForestatus().equals("Foreground FL")){
				return false;
			}
		}
		return true;
	}

	
	public Object getValue(Object element, String property) {
		if(property.equalsIgnoreCase("Marker Status")) {
			String type= ((Intervals)element).getForestatus();
			/*if(type.equalsIgnoreCase("")) {
				return new Integer(0); 
			} */
			 
				if(type.equalsIgnoreCase("Foreground")){
					return new Integer(0);
				} 
				else if(type.equalsIgnoreCase("Background")){
					return new Integer(1);
				}
		}
		
		if(property.equalsIgnoreCase("Markers")) {
			String marker= ((Intervals)element).getMap_marker();
				return marker;
				
		}
		
		
		return null;
	}

	
	public void modify(Object element, String property, Object value) {
		String result= "";

		if(property.equalsIgnoreCase("Marker Status")) {
			switch(((Integer)value).intValue()) {
	
			case 0:
				result = "Foreground";
				
				break;
			case 1:
				result = "Background";
				break;
			default:
				break;
			}
			
			//-----start-----
			RootModel rootModel = RootModel.getRootModel();
			LinkageData linkage;
			if(rootModel.getLoadFlag() == null){
				rootModel = RootModel.getRootModel();
				linkage = LinkageData.getLinkageData();
			}else{						
				rootModel = Session.getInstance().getRootmodel();
				linkage = rootModel.getLinkData();
			}
			String lflank = "", rflank = "";
			HashMap mstatus=new HashMap();								
			mstatus=rootModel.getLinkagemap().get(0).getChromosomes().get(0).getMstatus();
			List<String> foregroundMarkers = linkage.getForegroundMarker();
			HashMap flankmarkers = new HashMap();
			flankmarkers = linkage.getFlankmarkers();
			List<String> flankingMarkers = linkage.getFlankingMarker();
			if(flankingMarkers==null){
				flankingMarkers=new ArrayList<String>();
			}
			if(mstatus==null){
				mstatus=new HashMap();
			}
			if(foregroundMarkers==null){
				foregroundMarkers=new ArrayList<String>();
			}
			if(flankmarkers==null){
				flankmarkers=new HashMap();
			}
			mstatus.put(((Intervals)((TableItem)element).getData()).getMap_marker(), "Foreground");
			for(int l=0;l<rootModel.getLinkagemap().get(0).getChromosomes().size();l++){
				
				if(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker().equals(((Intervals)((TableItem)element).getData()).getMap_marker())){
					
					if(result == "Background"){
						rootModel.getLinkagemap().get(0).getChromosomes().get(l).setForestatus("Background");
						if((foregroundMarkers.contains(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker()))){
							foregroundMarkers.remove(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker());
							}
						
					}else{
						rootModel.getLinkagemap().get(0).getChromosomes().get(l).setForestatus("Foreground");
						if(!(foregroundMarkers.contains(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker()))){
							foregroundMarkers.add(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker());
							}
					}
				}
					 try {
						for(int f=l-1;;f--){
						if(rootModel.getGenotype().get(0).getHeaderList().contains(rootModel.getLinkagemap().get(0).getChromosomes().get(f).getMap_marker())){
						if(!(flankingMarkers.contains(rootModel.getLinkagemap().get(0).getChromosomes().get(f).getMap_marker()))){
							flankingMarkers.add(rootModel.getLinkagemap().get(0).getChromosomes().get(f).getMap_marker());	
							lflank = rootModel.getLinkagemap().get(0).getChromosomes().get(f).getMap_marker();
							break;											
						}
						}
						}

						 for(int f=l+1;;f++){
								if(rootModel.getGenotype().get(0).getHeaderList().contains(rootModel.getLinkagemap().get(0).getChromosomes().get(f).getMap_marker())){
								if(!(flankingMarkers.contains(rootModel.getLinkagemap().get(0).getChromosomes().get(f).getMap_marker()))){
									flankingMarkers.add(rootModel.getLinkagemap().get(0).getChromosomes().get(f).getMap_marker());
									rflank = rootModel.getLinkagemap().get(0).getChromosomes().get(f).getMap_marker();
									break;											
								}
								}
								}
						 flankmarkers.put(((Intervals)((TableItem)element).getData()).getMap_marker(), lflank+"!@!"+rflank);
					} catch (Exception e) {
						// TODO Auto-generated catch block
					}
				}
			linkage.setFlankmarkers(flankmarkers);
			linkage.setForegroundMarker(foregroundMarkers);
			linkage.setFlankingMarker(flankingMarkers);
			rootModel.getLinkagemap().get(0).getChromosomes().get(0).setMstatus(mstatus);
//			Session.getInstance().setRootModel(rootModel);
			
			//-----end------

	}
		if(property.equalsIgnoreCase("Markers")) {
			String marker= ((Intervals)element).getMap_marker();
		}		
		tViewer.refresh();
	}
}
