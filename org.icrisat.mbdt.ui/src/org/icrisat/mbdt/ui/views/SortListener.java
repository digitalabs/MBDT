package org.icrisat.mbdt.ui.views;

	import org.eclipse.jface.viewers.Viewer;
	import org.eclipse.jface.viewers.ViewerSorter;
	import org.eclipse.swt.SWT;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.LinkageMapModel.LimsMarkers;

	public class SortListener extends ViewerSorter {
		public int propertyIndex;
		private static int DESCENDING = 1;
		private int direction ;
		private String column="";
		int i=0;
		
		public SortListener() {
			this.propertyIndex = propertyIndex;
			}
		public SortListener(String column, int dir) {
	        super();
	        this.column = column;
	        this.direction = dir;
	    }
		
		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			
			Accessions p1 = (Accessions) e1;
			Accessions p2 = (Accessions) e2;
			int rc = 0;
			String result= "";
			i=propertyIndex;
			if(propertyIndex==-1){
				rc = p1.getName().compareTo(p2.getName());				
			}else{
				if(i==0){
					rc = p1.getTraitValues().get(i).getTraitValues().get(i).toString().compareTo(p2.getTraitValues().get(i).getTraitValues().get(i).toString());	
				}else{
				rc = p1.getTraitValues().get(i-1).getTraitValues().get(i).toString().compareTo(p2.getTraitValues().get(i-1).getTraitValues().get(i).toString());
				}
				
			}
			
			if (direction == SWT.DOWN) {
				rc = -rc;
			}
			return rc;
		}

	}


