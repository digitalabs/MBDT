package org.icrisat.mbdt.model.GenotypeModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class Genotype implements Serializable {
 
	List<Accessions> accessions;
	List<String> headerList;
	int count;
	String dataCheck;
	LinkedHashSet<String> NGTColorPrefCount = new LinkedHashSet<String>();
	List<Object> NGHidingElement;
	Boolean NGHidingStatus = false;
	List<Object> selAccForUnHide;
	
	public List<Accessions> getAccessions() {
		if(accessions== null) {
			accessions= new ArrayList<Accessions>();
		}
		return accessions;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}	

	public List<String> getHeaderList() {
		return headerList;
	}

	public void setHeaderList(List<String> headerList) {
		this.headerList = headerList;
	}

	public String getDataCheck() {
		return dataCheck;
	}

	public void setDataCheck(String dataCheck) {
		this.dataCheck = dataCheck;
	}

	public LinkedHashSet<String> getNGTColorPrefCount() {
		return NGTColorPrefCount;
	}

	public void setNGTColorPrefCount(LinkedHashSet<String> colorPrefCount) {
		NGTColorPrefCount = colorPrefCount;
	}

	public List<Object> getNGHidingElement() {
		if(NGHidingElement == null){
			NGHidingElement = new ArrayList<Object>();
		}
		return NGHidingElement;
	}

	public void setNGHidingElement(List<Object> hidingElement) {
		NGHidingElement = hidingElement;
	}

	public Boolean getNGHidingStatus() {
		return NGHidingStatus;
	}

	public void setNGHidingStatus(Boolean hidingStatus) {
		NGHidingStatus = hidingStatus;
	}

	public List<Object> getSelAccForUnHide() {
		if(selAccForUnHide == null){
			selAccForUnHide = new ArrayList<Object>();
		}
		return selAccForUnHide;
	}

	public void setSelAccForUnHide(List<Object> selAccForUnHide) {
		this.selAccForUnHide = selAccForUnHide;
	}
	
}
