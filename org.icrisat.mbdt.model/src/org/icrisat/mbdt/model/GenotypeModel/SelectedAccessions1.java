package org.icrisat.mbdt.model.GenotypeModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectedAccessions1 implements Serializable{

	String selAccession;
	List selacc;
	List<GenotypeMarkers> allellic;
	List<GenotypeMarkers> gMarkers;
	List<SortedMarkers> sMarkers;
	int width;
	List list_of_widths;
	String type;



	HashMap<String, String> keyvalue;
	public HashMap<String, String> getKeyvalue() {
		return keyvalue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setKeyvalue(HashMap<String, String> keyvalue) {
		this.keyvalue = keyvalue;
	}


	public List getList_of_widths() {
		return list_of_widths;
	}


	public void setList_of_widths(List list_of_widths) {
		this.list_of_widths = list_of_widths;
	}


	public List<GenotypeMarkers> getGenotypeMarkers() {
		if(gMarkers== null) {
			gMarkers= new ArrayList();
		}
		return gMarkers;
	}

	public List<SortedMarkers> getSortedMarkers() {
		if(sMarkers== null) {
			sMarkers= new ArrayList();
		}
		return sMarkers;
	}


	public List getSelacc() {
		return selacc;
	}

	public void setSelacc(List selacc) {
		this.selacc = selacc;
	}

	public String getSelAccession() {

		return selAccession;
	}

	public void setSelAccession(String selAccession) {
		this.selAccession = selAccession;
	}

	public  List<GenotypeMarkers> getAllellic() {
		if(allellic== null) {
			allellic= new ArrayList();
		}
		return allellic;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}

}