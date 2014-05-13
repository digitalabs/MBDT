package org.icrisat.mbdt.gef.wizards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.sessions.Session;

public class BestIndividualsLabelProvider extends LabelProvider implements ITableLabelProvider {
	List<String> lMarkers= new ArrayList<String>();
	List<String> markerPositions= new ArrayList<String>();
	List<String> markersList = new ArrayList<String>();
	static int count= 0;

	
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getColumnText(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		String result= "";
		try{
		switch(columnIndex) {
		case 0:
			RootModel rootModel = RootModel.getRootModel();
			
			LinkageData linkage;
			if(rootModel.getLoadFlag() == null){
				linkage = LinkageData.getLinkageData();
			}else{
				rootModel = Session.getInstance().getRootmodel();
				linkage = rootModel.getLinkData();
			}
			try{
			String count = null;
			
				if(linkage.isMissing()==true){
					int c =0;
				try {
					if(!(linkage.getBestcount().get(element.toString())==null)){
//						 c = (Integer)linkage.getBestcount().get(element.toString());
						 c = (Integer)linkage.getBestindividual().get(element.toString()).size();
						}else{
							c = 0;
						}
					c = c+(Integer)linkage.getMissingindividual().get(element.toString()).size();
				} catch (Exception e) {
				}
				result = element.toString()+"      "+c;	
				count = Integer.toString(c)  ;
				}else{
					
					if(!(linkage.getBestcount().get(element.toString())==null)){
						count = Integer.toString((Integer)linkage.getBestindividual().get(element.toString()).size());
//					 count = Integer.toString((Integer)linkage.getBestcount().get(element.toString()));
					}else{
						count ="0";
					}
					result = element.toString()+"      "+count;
					}
			
			}catch(Exception e){
			}
			break;
		
		default:
			break;
		}
		}catch(Exception e){
		}
		
		return result;
	}

}
