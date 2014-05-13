package org.icrisat.mbdt.model.TargetGenotype;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Parents implements Serializable{

	String parent;
	List<SelectedParents> selParents;
	String type;
	
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public List<SelectedParents> getSelParents() {
		if(selParents== null) {
			selParents= new ArrayList();
		}
		return selParents;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}	
}
