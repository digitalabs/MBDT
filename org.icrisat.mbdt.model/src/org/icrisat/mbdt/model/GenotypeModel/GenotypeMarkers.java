package org.icrisat.mbdt.model.GenotypeModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GenotypeMarkers implements Serializable {
	
	String acc;
	String type;
	List<String> markerName= new ArrayList();
	List<String> alleles= new ArrayList();
	List list_of_widths;
	int count;
	List<String> sortedlabels;
	
	Object eP = null;
	
	public Object getEP() {
		return eP;
	}

	public void setEP(Object ep) {
		eP = ep;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List getList_of_widths() {
		return list_of_widths;
	}

	public void setList_of_widths(List list_of_widths) {
		this.list_of_widths = list_of_widths;
	}

	public List<String> getAlleles() {
		return alleles;
	}

	public void setAlleles(List<String> alleles) {
		this.alleles = alleles;
	}

	public List<String> getMarkerName() {
		return markerName;
	}

	public void setMarkerName(List<String> markerName) {
		getMarkerName();
		this.markerName = markerName;
	}

	public String getAcc() {
		return acc;
	}

	public void setAcc(String acc) {
		this.acc = acc;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type= type;
	}

	public List<String> getSortedlabels() {
		return sortedlabels;
	}

	public void setSortedlabels(List<String> sortedlabels) {
		this.sortedlabels = sortedlabels;
	}

	
}
