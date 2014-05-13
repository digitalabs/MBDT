package org.icrisat.mbdt.ui.views;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;


public class ExampleViewerFilter extends ViewerFilter {

	String filterText="";
	int count=0;
	int totalcount=0;
	
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		RootModel rootModel = RootModel.getRootModel();
		
		try {
			if(!rootModel.getGenotype().isEmpty()){
				totalcount=rootModel.getGenotype().get(0).getAccessions().size();
			}	
			if((((Accessions)element).getName().toLowerCase()).contains((filterText.replaceAll("\\*", "")).toLowerCase())) {
				if(filterText.length()>0)
				count++;
				return true;
			}
			if(filterText.length() == 0) {
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		return false;
	}	
	public void setFilterText(String text) {
		this.filterText= text;
		count=0;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}
}