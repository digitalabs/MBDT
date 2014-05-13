package org.icrisat.mbdt.model.QTLModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QTL implements Serializable {
	
	List<QTLData> qtlData;
	String qtlInfo;
	List qtlGrayLineYvalues;
	int qtlEnvironmentCount;
	int qtlTraitCount;
	List<String> qtlEnvironmentList;
	List<String> qtlTraitList;
	List<String> qtlEnviListFromLoader;
	List<String> qtlTraitListFromLoader;
//	List<QTLGrayLines> qtlGrayLines;	
	
	
	//3rd NOV 2009......
	String dialogQtlStartPt;
	String dialogQtlEndPt;
	String dialogQtlTraitName;
	String dialogQtlLodSqr;
	String dialogQtlRSqr;
	String dialogQtlAddEffects;
	String dialogQtlPeakPt;
	
	public int getQtlTraitCount() {
		return qtlTraitCount;
	}

	public void setQtlTraitCount(int qtlTraitCount) {
		this.qtlTraitCount = qtlTraitCount;
	}

	public List<QTLData> getQtlData() {
		if(qtlData == null){
			qtlData = new ArrayList<QTLData>();	
		}
		return qtlData;
	}

	public void setQtlData(List<QTLData> qtlData) {		
		this.qtlData = qtlData;
	}
	
	public String getQtlInfo() {
		return qtlInfo;
	}
	public void setQtlInfo(String qtlInfo) {
		this.qtlInfo = qtlInfo;
	}

	public List getQtlGrayLineYvalues() {
		if(qtlGrayLineYvalues == null){
			qtlGrayLineYvalues = new ArrayList();
			
		}
		return qtlGrayLineYvalues;
	}

	public void setQtlGrayLineYvalues(List qtlGrayLineYvalues) {
		this.qtlGrayLineYvalues = qtlGrayLineYvalues;
	}

	public int getQtlEnvironmentCount() {
		return qtlEnvironmentCount;
	}

	public void setQtlEnvironmentCount(int qtlEnvironmentCount) {
		this.qtlEnvironmentCount = qtlEnvironmentCount;
	}

	public List<String> getQtlEnvironmentList() {
		if(qtlEnvironmentList == null){
			qtlEnvironmentList = new ArrayList<String>();
			
		}
		return qtlEnvironmentList;
	}

	public void setQtlEnvironmentList(List<String> qtlEnvironmentList) {
		this.qtlEnvironmentList = qtlEnvironmentList;
	}

	public List<String> getQtlTraitList() {
		if(qtlTraitList == null){
			qtlTraitList = new ArrayList<String>();
		}
		return qtlTraitList;
	}

	public void setQtlTraitList(List<String> qtlTraitList) {
		this.qtlTraitList = qtlTraitList;
	}

	public List<String> getQtlEnviListFromLoader() {
		if(qtlEnviListFromLoader == null){
			qtlEnviListFromLoader = new ArrayList<String>();
			
		}
		return qtlEnviListFromLoader;
	}

	public void setQtlEnviListFromLoader(List<String> qtlEnviListFromLoader) {
		this.qtlEnviListFromLoader = qtlEnviListFromLoader;
	}

	public List<String> getQtlTraitListFromLoader() {
		if(qtlTraitListFromLoader == null){
			qtlTraitListFromLoader = new ArrayList<String>();
			
		}
		return qtlTraitListFromLoader;
	}

	public void setQtlTraitListFromLoader(List<String> qtlTraitListFromLoader) {
		this.qtlTraitListFromLoader = qtlTraitListFromLoader;
	}

	public String getDialogQtlStartPt() {
		return dialogQtlStartPt;
	}

	public void setDialogQtlStartPt(String dialogQtlStartPt) {
		this.dialogQtlStartPt = dialogQtlStartPt;
	}

	public String getDialogQtlEndPt() {
		return dialogQtlEndPt;
	}

	public void setDialogQtlEndPt(String dialogQtlEndPt) {
		this.dialogQtlEndPt = dialogQtlEndPt;
	}

	public String getDialogQtlTraitName() {
		return dialogQtlTraitName;
	}

	public void setDialogQtlTraitName(String dialogQtlTraitName) {
		this.dialogQtlTraitName = dialogQtlTraitName;
	}

	public String getDialogQtlLodSqr() {
		return dialogQtlLodSqr;
	}

	public void setDialogQtlLodSqr(String dialogQtlLodSqr) {
		this.dialogQtlLodSqr = dialogQtlLodSqr;
	}

	public String getDialogQtlRSqr() {
		return dialogQtlRSqr;
	}

	public void setDialogQtlRSqr(String dialogQtlRSqr) {
		this.dialogQtlRSqr = dialogQtlRSqr;
	}

	public String getDialogQtlAddEffects() {
		return dialogQtlAddEffects;
	}

	public void setDialogQtlAddEffects(String dialogQtlAddEffects) {
		this.dialogQtlAddEffects = dialogQtlAddEffects;
	}

	public String getDialogQtlPeakPt() {
		return dialogQtlPeakPt;
	}

	public void setDialogQtlPeakPt(String dialogQtlPeakPt) {
		this.dialogQtlPeakPt = dialogQtlPeakPt;
	}
	
	/*public List<QTLGrayLines> getQtlGrayLines() {
		if(qtlGrayLines == null){
			qtlGrayLines = new ArrayList<QTLGrayLines>();	
		}
		return qtlGrayLines;
	}
	public void setQtlGrayLines(List<QTLGrayLines> qtlGrayLines) {
		this.qtlGrayLines = qtlGrayLines;
	}*/
}
