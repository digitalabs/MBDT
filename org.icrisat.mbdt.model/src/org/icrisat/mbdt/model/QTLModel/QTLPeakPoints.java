package org.icrisat.mbdt.model.QTLModel;

import java.io.Serializable;

public class QTLPeakPoints implements Serializable {

	String qtlPeakPoints;
	int qtlStartPtForPeak;
	int qtlYValueForPeak;
	int qtlStartPtFrmEditPart;
	
	public int getQtlStartPtFrmEditPart() {
		return qtlStartPtFrmEditPart;
	}

	public void setQtlStartPtFrmEditPart(int qtlStartPtFrmEditPart) {
		this.qtlStartPtFrmEditPart = qtlStartPtFrmEditPart;
	}

	public int getQtlYValueForPeak() {
		return qtlYValueForPeak;
	}

	public void setQtlYValueForPeak(int qtlYValueForPeak) {
		this.qtlYValueForPeak = qtlYValueForPeak;
	}

	public int getQtlStartPtForPeak() {
		return qtlStartPtForPeak;
	}

	public void setQtlStartPtForPeak(int qtlStartPtForPeak) {
		this.qtlStartPtForPeak = qtlStartPtForPeak;
	}

	public String getQtlPeakPoints() {
		return qtlPeakPoints;
	}

	public void setQtlPeakPoints(String qtlPeakPoints) {
		this.qtlPeakPoints = qtlPeakPoints;
	}	
}
