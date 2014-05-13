package org.icrisat.mbdt.ui.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;
import org.icrisat.mbdt.gef.views.GraphicalView;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.GenotypeModel.SelectedAccessions1;
import org.icrisat.mbdt.model.sessions.Session;


public class SelectedAccessionTypeModifier implements ICellModifier {

	TableViewer tViewer;
	List selaccList = new ArrayList();
	List prevAcclist = new ArrayList();
	List currAcclist = new ArrayList();
	static int count = 0;
	static HashMap type= new HashMap();
	public SelectedAccessionTypeModifier(TableViewer tViewer) {
		this.tViewer= tViewer;
	}

	public boolean canModify(Object element, String property) {		
		if(property.equalsIgnoreCase("Name")) {
			return false;
		}
		return true;
	}

	public Object getValue(Object element, String property) {

		if(property.equalsIgnoreCase("Type")) {
			String type= ((Accessions)element).getType();
				if(type.equalsIgnoreCase("Recurrent")){
					return new Integer(0);
				} 
				else if(type.equalsIgnoreCase("Donor")){
					return new Integer(1);
				}
		}
		return null;
	}

	public void modify(Object element, String property, Object value) {

		String result= "";
		prevAcclist = currAcclist;
		currAcclist = ((Accessions)((TableItem)element).getData()).getSelectedAccessions1();
		if(property.equalsIgnoreCase("Type")) {
			switch(((Integer)value).intValue()) {
			case 0:
				result = "Recurrent";
				break;
			case 1:
				result = "Donor";
				break;
			default:
				break;
			}
			
			if((!currAcclist.equals(prevAcclist)) && (count != 0)){
				selaccList.clear();

			}
			String stracc = ((TableItem)element).getText();

			if(selaccList.contains(stracc)){

				int indexno = selaccList.indexOf(stracc) + 1;
				if(!selaccList.get(indexno).equals(result)){
					//for removing duplicate items from the arraylist.....
					selaccList.remove(selaccList.indexOf(stracc));
					selaccList.remove(indexno-1);

					selaccList.add(stracc);
					selaccList.add(result);
				}
			}else{
				selaccList.add(stracc);
				selaccList.add(result);
			}

			// Setting the result from table to model....
			((Accessions)((TableItem)element).getData()).setType(result);
			
			RootModel rootModel1 = Session.getInstance().getRootmodel();
			LinkageData linkageData;
			
			if(rootModel1.getLoadFlag() == null){
				 linkageData = LinkageData.getLinkageData();
			}else{
				linkageData = rootModel1.getLinkData();
			}
			linkageData.setModified(true);
			type = linkageData.getType();
			if(type == null){
				type = new HashMap();
			}
			type.put(((Accessions)((TableItem)element).getData()).getName(),((Accessions)((TableItem)element).getData()).getType());
			linkageData.setType(type);
			
//			System.out.println(linkageData.getType());
			((Accessions)((TableItem)element).getData()).setDbType(selaccList);
			count++;
			tViewer.refresh();
//			Session.getInstance().setRootModel(rootModel1);
		} 
	}
}
