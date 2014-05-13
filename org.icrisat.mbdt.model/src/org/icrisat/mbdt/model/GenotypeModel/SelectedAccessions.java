package org.icrisat.mbdt.model.GenotypeModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectedAccessions implements Serializable {

	String selAccession;
	Accessions parent;
	List<SelectedAccessions1> selacc1;
	List selallelicValues= new ArrayList();
	List selaccmarkers= new ArrayList();
	List selacc;
	HashMap<String, String> keyvalue;
	String count;
	String type;

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public List getSelallelicValues() {
		if(selallelicValues == null) {
			selallelicValues = new ArrayList();			
		}
		return selallelicValues;
	}

	public void setSelallelicValues(List selallelicValues) {
		this.selallelicValues = selallelicValues;
	}

	public List getSelaccmarkers() {
		if(selaccmarkers == null){
			selaccmarkers = new ArrayList();			
		}
		return selaccmarkers;
	}

	public void setSelaccmarkers(List selaccmarkers) {
		this.selaccmarkers = selaccmarkers;
	}	
	
	public String getCount() {
		return count;
	}
	
	public void setCount(String count) {
		this.count = count;
	}

	public List<SelectedAccessions1> getSelacc1() {
		if(selacc1== null) {
			selacc1= new ArrayList();
		}
		return selacc1;
	}
	
	public void setSelacc1(List<SelectedAccessions1> selacc1) {
		this.selacc1 = selacc1;
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
	
	public HashMap<String, String> getKeyvalue() {
		return keyvalue;
	}
	
	public void setKeyvalue(HashMap<String, String> keyvalue) {
		this.keyvalue = keyvalue;
	}

	public Accessions getParent() {
		return parent;
	}

	public void setParent(Accessions parent) {
		this.parent = parent;
	}
}
