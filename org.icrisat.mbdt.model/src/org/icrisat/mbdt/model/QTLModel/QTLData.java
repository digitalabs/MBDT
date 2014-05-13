package org.icrisat.mbdt.model.QTLModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

public class QTLData implements Serializable {
	
	LinkedHashSet<String> groupName;
	ArrayList traitName;
	int groupCount;
	int prevGroupCount;
	int missingGrpCount;
	ArrayList qtlStart;
	ArrayList qtlEnd;
	String qtltraitName;
	String qtlEnviName;
	String qtlLodsqr;
	String qtlRsqr;
	String qtlAddEffects;
	String qtlStartPt;
	String qtlEndPt;
	String qtlPrevStartPt;
	String qtlPrevEndPt;
	String qtlPeakPoint;
	String qtlPeakPointFrmEditPart;
	int qtlStartPtForPeak;
	int qtlYValueForPeak;
	List<QTLPeakPoints> qtlPeakPoints;
	
	String qtlNames;
	String qtlChromNames;	
	List<String> qtlChromList = new ArrayList<String>();
	List<String> qtlModChromList = new ArrayList<String>();
	String selectedqtl;
	
	
	public String getSelectedqtl() {
		return selectedqtl;
	}
	public void setSelectedqtl(String selectedqtl) {
		this.selectedqtl = selectedqtl;
	}
	public LinkedHashSet<String> getGroupName() {
		if(groupName == null){
			groupName = new LinkedHashSet<String>();
			
		}
		return groupName;
	}
	public void setGroupName(LinkedHashSet<String> groupName) {
		this.groupName = groupName;
	}	
	public int getGroupCount() {		
		return groupCount;
	}
	public void setGroupCount(int groupCount) {
		//System.out.println(groupCount);
		this.groupCount = groupCount;
	}
	
	
	public ArrayList getTraitName() {
		if(traitName == null){
			traitName = new ArrayList();
			
		}
		return traitName;
	}
	public void setTraitName(ArrayList traitName) {
		this.traitName = traitName;
	}
	public ArrayList getQtlStart() {
		if(qtlStart == null){
			qtlStart = new ArrayList();			
		}
		return qtlStart;
	}
	public void setQtlStart(ArrayList qtlStart) {
		this.qtlStart = qtlStart;
	}
	public ArrayList getQtlEnd() {
		if(qtlEnd == null){
			qtlEnd = new ArrayList();			
		}
		return qtlEnd;
	}
	public void setQtlEnd(ArrayList qtlEnd) {		
		this.qtlEnd = qtlEnd;
	}
	public String getQtltraitName() {
		return qtltraitName;
	}
	public void setQtltraitName(String qtltraitName) {
		this.qtltraitName = qtltraitName;
	}
	public String getQtlStartPt() {
		return qtlStartPt;
	}
	public void setQtlStartPt(String qtlStartPt) {		
		this.qtlStartPt = qtlStartPt;
	}
	public String getQtlEndPt() {
		return qtlEndPt;
	}
	public void setQtlEndPt(String qtlEndPt) {
		this.qtlEndPt = qtlEndPt;
	}
	public String getQtlPrevStartPt() {
		return qtlPrevStartPt;
	}
	public void setQtlPrevStartPt(String qtlPrevStartPt) {
		this.qtlPrevStartPt = qtlPrevStartPt;
	}
	public String getQtlPrevEndPt() {
		return qtlPrevEndPt;
	}
	public void setQtlPrevEndPt(String qtlPrevEndPt) {
		this.qtlPrevEndPt = qtlPrevEndPt;
	}
	public int getPrevGroupCount() {
		return prevGroupCount;
	}
	public void setPrevGroupCount(int prevGroupCount) {
		this.prevGroupCount = prevGroupCount;
	}
	public int getMissingGrpCount() {
		return missingGrpCount;
	}
	public void setMissingGrpCount(int missingGrpCount) {
		this.missingGrpCount = missingGrpCount;
	}
	public String getQtlLodsqr() {
		return qtlLodsqr;
	}
	public void setQtlLodsqr(String qtlLodsqr) {
		this.qtlLodsqr = qtlLodsqr;
	}
	public String getQtlRsqr() {
		return qtlRsqr;
	}
	public void setQtlRsqr(String qtlRsqr) {
		this.qtlRsqr = qtlRsqr;
	}
	public String getQtlAddEffects() {
		return qtlAddEffects;
	}
	public void setQtlAddEffects(String qtlAddEffects) {
		this.qtlAddEffects = qtlAddEffects;
	}
	public String getQtlEnviName() {
		return qtlEnviName;
	}
	public void setQtlEnviName(String qtlEnviName) {
		this.qtlEnviName = qtlEnviName;
	}public String getQtlPeakPoint() {
		return qtlPeakPoint;
	}
	public void setQtlPeakPoint(String qtlPeakPoint) {
		this.qtlPeakPoint = qtlPeakPoint;
	}
	public String getQtlPeakPointFrmEditPart() {
		return qtlPeakPointFrmEditPart;
	}
	public void setQtlPeakPointFrmEditPart(String qtlPeakPointFrmEditPart) {
		this.qtlPeakPointFrmEditPart = qtlPeakPointFrmEditPart;
	}
	public int getQtlStartPtForPeak() {
		return qtlStartPtForPeak;
	}
	public void setQtlStartPtForPeak(int qtlStartPtForPeak) {
		this.qtlStartPtForPeak = qtlStartPtForPeak;
	}
	public int getQtlYValueForPeak() {
		return qtlYValueForPeak;
	}
	public void setQtlYValueForPeak(int qtlYValueForPeak) {
		this.qtlYValueForPeak = qtlYValueForPeak;
	}
	public List<QTLPeakPoints> getQtlPeakPoints() {
		if(qtlPeakPoints == null){
			qtlPeakPoints = new ArrayList<QTLPeakPoints>();
			
		}
		return qtlPeakPoints;
	}
	public void setQtlPeakPoints(List<QTLPeakPoints> qtlPeakPoints) {
		this.qtlPeakPoints = qtlPeakPoints;
	}
	
	
	public String getQtlNames() {
		return qtlNames;
	}
	public void setQtlNames(String qtlNames) {
		this.qtlNames = qtlNames;
	}
	public String getQtlChromNames() {
		return qtlChromNames;
	}
	public void setQtlChromNames(String qtlChromNames) {
		this.qtlChromNames = qtlChromNames;
	}
	public List<String> getQtlChromList() {
		return qtlChromList;
	}
	public void setQtlChromList(List<String> qtlChromList) {
		this.qtlChromList = qtlChromList;
	}
	public List<String> getQtlModChromList() {
		return qtlModChromList;
	}
	public void setQtlModChromList(List<String> qtlModChromList) {
		this.qtlModChromList = qtlModChromList;
	}
}
