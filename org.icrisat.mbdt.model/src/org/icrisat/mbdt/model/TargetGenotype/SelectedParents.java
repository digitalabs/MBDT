package org.icrisat.mbdt.model.TargetGenotype;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectedParents implements Serializable {

	String selectedParents;
	List<MarkersSelectedParents> mParents;
	List<String> chrNo;
	
	public List<String> getChrNo() {
		return chrNo;
	}

	public void setChrNo(List<String> chrNo) {
		this.chrNo = chrNo;
	}

	public String getSelectedParents() {
		return selectedParents;
	}

	public void setSelectedParents(String selectedParents) {
		this.selectedParents = selectedParents;
	}

	public List<MarkersSelectedParents> getMParents() {
		if(mParents== null) {
			mParents= new ArrayList();
		}
		return mParents;
	}
}
