package org.icrisat.mbdt.model.PhenotypeModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.icrisat.mbdt.model.GenotypeModel.Accessions;

public class Phenotype implements Serializable{
	
	
	List<Accessions> accessions;
	List<String> TraitList;
	public List<Accessions> getAccessions() {
		if(accessions== null) {
			accessions= new ArrayList<Accessions>();
		}
		return accessions;
	}
	
	public List<String> getTraitList() {
		return TraitList;
	}
	public void setTraitList(List<String> traitList) {
		TraitList = traitList;
	}
	
}
