package org.icrisat.mbdt.model.QTLModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QTLGrayLines implements Serializable {
	
	List<QTLData> qtlData;
	

	public List<QTLData> getQtlData() {
		if(qtlData == null){
			qtlData = new ArrayList<QTLData>();
			
		}
		return qtlData;
	}

	public void setQtlData(List<QTLData> qtlData) {
		this.qtlData = qtlData;
	}
	
}
