package org.icrisat.mbdt.model.TargetGenotype;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MarkersSelectedParents implements Serializable {
	
	List<String> accession;
	List<String> chrNo;
	List<String> markerName;
	List<Integer> markerPosition;
	List<String> label;
	List<ColorAllele> colorAllele;
	List<Integer> markerPrevPos;
	List<Integer> markerNextPos;
	List<String> targetAlleleValue;
	
	List<String> donorAlleleValue;
	
	String selectedParents;
	int position;
	String type;
	
	List<String> targetAlleleFrmDataBase;
	List<String> targetMACCombi;
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getSelectedParents() {
		return selectedParents;
	}

	public void setSelectedParents(String selectedParents) {
		this.selectedParents = selectedParents;
	}

	public List<String> getAccession() {
		return accession;
	}
	
	public void setAccession(List<String> accession) {
		this.accession = accession;
	}
	
	public List<String> getChrNo() {
		return chrNo;
	}
	
	public void setChrNo(List<String> chrNo) {
		this.chrNo = chrNo;
	}
	
	public List<String> getMarkerName() {
		return markerName;
	}
	
	public void setMarkerName(List<String> markerName) {
		this.markerName = markerName;
	}
	
	public List<Integer> getMarkerPosition() {
		return markerPosition;
	}
	
	public void setMarkerPosition(List<Integer> markerPosition) {
		this.markerPosition = markerPosition;
	}
	
	public List<String> getLabel() {
		return label;
	}
	
	public void setLabel(List<String> label) {
		this.label = label;
	}

	public List<ColorAllele> getColorAllele() {
		if(colorAllele== null) {
			colorAllele= new ArrayList();
		}
		return colorAllele;
	}

	public List<Integer> getMarkerPrevPos() {
		return markerPrevPos;
	}

	public void setMarkerPrevPos(List<Integer> markerPrevPos) {
		this.markerPrevPos = markerPrevPos;
	}

	public List<Integer> getMarkerNextPos() {
		return markerNextPos;
	}

	public void setMarkerNextPos(List<Integer> markerNextPos) {
		this.markerNextPos = markerNextPos;
	}

	public List<String> getTargetAlleleValue() {
		return targetAlleleValue;
	}

	public void setTargetAlleleValue(List<String> targetAlleleValue) {
		this.targetAlleleValue = targetAlleleValue;
	}

	public List<String> getTargetAlleleFrmDataBase() {
		if(targetAlleleFrmDataBase == null){
			targetAlleleFrmDataBase = new ArrayList<String>();			
		}
		return targetAlleleFrmDataBase;
	}

	public void setTargetAlleleFrmDataBase(List<String> targetAlleleFrmDataBase) {
		this.targetAlleleFrmDataBase = targetAlleleFrmDataBase;
	}

	public List<String> getTargetMACCombi() {
		if(targetMACCombi == null){
			targetMACCombi = new ArrayList<String>();
			
		}
		return targetMACCombi;
	}

	public void setTargetMACCombi(List<String> targetMACCombi) {
		this.targetMACCombi = targetMACCombi;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getDonorAlleleValue() {
		return donorAlleleValue;
	}

	public void setDonorAlleleValue(List<String> donorAlleleValue) {
		this.donorAlleleValue = donorAlleleValue;
	}
	
	
}
