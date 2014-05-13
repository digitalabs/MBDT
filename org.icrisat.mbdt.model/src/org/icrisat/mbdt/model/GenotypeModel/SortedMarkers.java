package org.icrisat.mbdt.model.GenotypeModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SortedMarkers implements Serializable {


	String acc;
	//String type;
	List<String> markerName= new ArrayList();
	List<String> alleles= new ArrayList();
	List list_of_widths;
	int count;
	//List selacc;
	String type;

	List<String> sortedlabels;
	HashMap<String, String> keyvalue=new HashMap<String, String>();

	/*public List getSelacc() {
		return selacc;
	}

	public void setSelacc(List selacc) {
		this.selacc = selacc;
	}
	 */
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
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type= type;
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

	/*public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type= type;
	}
	 */
	public List<String> getSortedlabels() {
		return sortedlabels;
	}

	public void setSortedlabels(List<String> sortedlabels) {
		this.sortedlabels = sortedlabels;
	}

	public HashMap<String, String> getKeyvalue() {
		return keyvalue;
	}

	public void setKeyvalue(HashMap<String, String> keyvalue) {
		this.keyvalue = keyvalue;
	}

	public List<String> getAlleles() {
		return alleles;
	}

	public void setAlleles(List<String> alleles) {
		this.alleles = alleles;
	}

}
