package org.icrisat.mbdt.lims.views;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.icrisat.mbdt.model.LinkageMapModel.LimsMarkers;

public class LimsMarkerStatusSorter extends ViewerSorter {
	private int propertyIndex;
	private static final int DESCENDING = 1;
	LimsMarkersInputView limsview=new LimsMarkersInputView();
	private int direction ;
	private String column="";
	
	
	public LimsMarkerStatusSorter() {
		this.propertyIndex = 0;
		}
	public LimsMarkerStatusSorter(String column, int dir) {
        super();
        this.column = column;
        this.direction = dir;
    }
	
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		
		LimsMarkers p1 = (LimsMarkers) e1;
		LimsMarkers p2 = (LimsMarkers) e2;
		int rc = 0;
		switch (propertyIndex) {
		case 0:
			rc = p1.getMarkerStatus().compareTo(p2.getMarkerStatus());
			break;		
		default:
			rc = 0;
		}
		if (direction == SWT.DOWN) {
			rc = -rc;
		}
		return rc;
	}

}
