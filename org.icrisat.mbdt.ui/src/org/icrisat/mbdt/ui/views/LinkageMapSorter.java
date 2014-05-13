package org.icrisat.mbdt.ui.views;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.icrisat.mbdt.model.LinkageMapModel.Intervals;

public class LinkageMapSorter extends ViewerSorter {
	
	int columnNo;
	private int sortDirection;
	private String column="";
	@Override
	public int category(Object element) {
		// TODO Auto-generated method stub
		return super.category(element);
	}
	public LinkageMapSorter(String column, int dir) {
        super();
        this.column = column;
        if(column == "Markers"){
        	columnNo = 1;
        }else if(column == "Chromosome"){
        	columnNo = 0;
        }
        this.sortDirection = dir;
    }
	
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		Intervals object1 = (Intervals)e1;
		Intervals object2 = (Intervals)e2;
		int rc = 0;
		switch (columnNo) {
		case 0:
			rc = object1.getChromosome().compareTo(object2.getChromosome());
			break;	
		
		case 1:
			rc = object1.getMap_marker().compareTo(object2.getMap_marker());
			break;
			
		default:
			break;			
			}
		if (sortDirection == SWT.DOWN) {
			rc = -rc;
		}
		return rc;
	}

	

}
