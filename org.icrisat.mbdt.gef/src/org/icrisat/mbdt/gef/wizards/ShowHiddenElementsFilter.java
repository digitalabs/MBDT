package org.icrisat.mbdt.gef.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;

public class ShowHiddenElementsFilter extends ViewerFilter {

	String filterText;
	int count=0;
	int totalcount=0;
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		// TODO Auto-generated method stub
RootModel rootModel = RootModel.getRootModel();
		
		if(!rootModel.getGenotype().isEmpty()){
			totalcount=rootModel.getGenotype().get(0).getAccessions().size();
		}	
		if(filterText != null){
		if((((Accessions)element).getName().toLowerCase()).contains((filterText.replaceAll("\\*", "")).toLowerCase())) {
			if(filterText.length()>0)
				count++;
			return true;
		}
		}
		return true;
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
