package org.icrisat.mbdt.ui.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.Chromosome.Chromosome;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.LinkageMapModel.LinkageMap;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionChromosome;

public class UnScreenedMarkersLabelProvider extends LabelProvider implements
		ITableLabelProvider {
	
	List unscreenedMarkers = new ArrayList();	
	static int count = 1;
		
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {		
		
		RootModel rootModel = Session.getInstance().getRootmodel();
		LinkageData linkageData;
		if(rootModel.getLoadFlag() == null){
			 linkageData = LinkageData.getLinkageData();
		}else{
			linkageData = rootModel.getLinkData();
		}
		Chromosome chrom = SessionChromosome.getInstance().getChromosome();
		LinkageMap map;
		if(linkageData.isCview()){
			map = (LinkageMap)chrom.getLinkagemap().get(0);			
		}else{
			map = (LinkageMap)rootModel.getLinkagemap().get(0);
		}
		for(int i=0;i<map.getChromosomes().size();i++){			
			if(map.getChromosomes().get(i).equals(element)){				
				if(map.getUnScreenedcount() != count){
					unscreenedMarkers.clear();
				}				
				unscreenedMarkers.add(map.getChromosomes().get(i).getMap_marker());
				
				//setting UnScreenedmarkers to Model for Lims input....
				map.getChromosomes().get(i).setUnScreenedmarkers(unscreenedMarkers);				
				count = map.getUnScreenedcount();
				return map.getChromosomes().get(i).getMap_marker();				
			}
		}		
		return "";
	}	

}
