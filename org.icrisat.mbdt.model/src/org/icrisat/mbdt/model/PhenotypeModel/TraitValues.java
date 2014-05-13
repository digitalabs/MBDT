package org.icrisat.mbdt.model.PhenotypeModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TraitValues implements Serializable{
	List traitValues= new ArrayList();

	public List getTraitValues() {
		return traitValues;
	}

	public void setTraitValues(List traitValues) {
		this.traitValues = traitValues;
	}
}
