package org.icrisat.mbdt.model.QTLModel;

import java.io.Serializable;

public class QTLPreviousData implements Serializable {
	
	String qtlPrevStart = "";
	String qtlPrevEnd = "";
	int qtlPrevgroupCount;
	int qtlMissingGrpCount;
	
	
	public int getQtlPrevgroupCount() {
		return qtlPrevgroupCount;
	}
	public void setQtlPrevgroupCount(int qtlPrevgroupCount) {
		this.qtlPrevgroupCount = qtlPrevgroupCount;
	}
	public String getQtlPrevStart() {
		return qtlPrevStart;
	}
	public void setQtlPrevStart(String qtlPrevStart) {
		this.qtlPrevStart = qtlPrevStart;
	}
	public String getQtlPrevEnd() {
		return qtlPrevEnd;
	}
	public void setQtlPrevEnd(String qtlPrevEnd) {
		this.qtlPrevEnd = qtlPrevEnd;
	}
	public int getQtlMissingGrpCount() {
		return qtlMissingGrpCount;
	}
	public void setQtlMissingGrpCount(int qtlMissingGrpCount) {
		this.qtlMissingGrpCount = qtlMissingGrpCount;
	}
}
